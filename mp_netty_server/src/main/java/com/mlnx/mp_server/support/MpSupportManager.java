package com.mlnx.mp_server.support;

import com.mlnx.device.ecg.EcgDeviceInfo;
import com.mlnx.mp_server.protocol.RegisterMessage;
import com.mlnx.mp_session.core.DeviceSession;
import com.mlnx.mp_session.core.EcgDeviceSession;
import com.mlnx.mp_session.core.MpDeviceSession;
import com.mlnx.mp_session.core.Session;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.mp_session.core.UsrSession;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.body.Command;
import com.mlnx.mptp.mptp.head.QoS;
import com.mlnx.mptp.push.PushPacket;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.mptp.utils.RandomUtils;
import com.mlnx.qcms.protocol.DataPacket;
import com.mlnx.qcms.protocol.body.CmdType;

import java.io.IOException;
import java.util.Date;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by amanda.shan on 2017/4/7.
 */
public class MpSupportManager {

    private static MpSupportManager instance;

    public static MpSupportManager getInstance() {
        if (instance == null)
            synchronized (MpSupportManager.class) {
                if (instance == null)
                    instance = new MpSupportManager();
            }
        return instance;
    }

    private EcgSupport ecgSupport;
    private UsrSupport usrSupport;

    public EcgSupport getEcgSupport() {
        return ecgSupport;
    }

    public void setUsrSupport(UsrSupport usrSupport) {
        this.usrSupport = usrSupport;
    }

    public UsrSupport getUsrSupport() {
        return usrSupport;
    }

    public void setEcgSupport(EcgSupport ecgSupport) {
        this.ecgSupport = ecgSupport;
    }

    public String configDevice(String deviceId, String ssid, String password, Byte wifiChannel, byte[] serverIp,
                               Integer serverPort, Byte heartChannel) {
        Session session = SessionManager.getConfig(deviceId);
        if (session == null) {
            return "设备不存在";
        }

        Body body = new Body();
        body.setCommand(Command.CONFIG);
        body.setMessageId(RandomUtils.GetDataTimeID().intValue());

        body.getConfigBody().setServerIp(serverIp);
        body.getConfigBody().setServerPort(serverPort);
        body.getConfigBody().setWifiSSid(ssid);
        body.getConfigBody().setWifiPassword(password);
        body.getConfigBody().setWifiChannel(wifiChannel);
        body.getConfigBody().setHeartChannel(heartChannel);

        MpPacket packet = new MpPacket().push(DeviceType.SERVER, body);
        packet.getHeader().setQoS(QoS.LEAST_ONE);

        session.getChannel().writeAndFlush(packet);

        return null;
    }


    /**
     * 验证ecg设备是否通过
     *
     * @param action
     * @param ecgDeviceInfo
     */
    public void verifyEcg(Action action, EcgDeviceInfo ecgDeviceInfo) throws IOException {

        Session session = null;
        RegisterMessage registerMessage = action.getRegisterMessage();
        ChannelHandlerContext ctx = action.getCtx();

        String deviceId = registerMessage.getDeviceId();

        if (ecgDeviceInfo == null) {
            MptpLogUtils.e(String.format("%s 设备不存在", deviceId));
            MpPacket packet = new MpPacket().registerAck(DeviceType.SERVER,
                    ResponseCode.NOT_EXIST_DEVICE);
            ctx.channel().writeAndFlush(packet);
            return;
        }else if (ecgDeviceInfo.getPatientId() == null ){
            MptpLogUtils.e(String.format("%s 设备未绑定患者", deviceId));
            MpPacket packet = new MpPacket().registerAck(DeviceType.SERVER,
                    ResponseCode.UN_BIND_PATIENT);
            ctx.channel().writeAndFlush(packet);
        }else{
            int type = 0;

            session = new EcgDeviceSession(deviceId);
            session.setKey(deviceId);
            ((DeviceSession) session).setDeviceId(deviceId);
            session.setPatientId(ecgDeviceInfo.getPatientId());

            Channel channel = SessionManager.get(session.getKey());
            if (channel != null && !ctx.channel().equals(channel)) {
                // 同一个设备另外重新
                type = 0;
            } else if (channel != null && ctx.channel().equals(channel)) {
                // 收到重复注册包
                type = 1;
            } else {
                // 新设备注册
                type = 2;
            }

            // 同一个设备另外重新
            // 设备不允许同时上线  用户运行多个端同时上线
            if (type == 0) {
                SessionManager.remove(channel, true);
                MptpLogUtils.e(String.format("重复注册 移除设备：%s 过期的Channel: %s", session.getKey(), channel.toString()));

                if (registerMessage.getKeepAliveTimer() != null)
                    session.setReaderIdleTimeSeconds(registerMessage
                            .getKeepAliveTimer());

                session.setChannel(ctx.channel());
                session.setSocketAddress(ctx.channel().remoteAddress());
                session.setLastPacketTime(new Date());
                session.setDeviceType(registerMessage.getDeviceType());

                SessionManager.add(ctx.channel(), session);
            }
            // 收到重复注册包
            else if (type == 1) {
                MptpLogUtils.e("同一个channel重复收到注册包");
            }
            // 新设备或者用户注册
            else {
                if (registerMessage.getKeepAliveTimer() != null)
                    session.setReaderIdleTimeSeconds(registerMessage
                            .getKeepAliveTimer());

                session.setChannel(ctx.channel());
                session.setSocketAddress(ctx.channel().remoteAddress());
                session.setLastPacketTime(new Date());
                session.setDeviceType(registerMessage.getDeviceType());

                SessionManager.add(ctx.channel(), session);
            }

            MpPacket packet = new MpPacket().registerAck(ecgDeviceInfo.getEcgChannelType(), ecgDeviceInfo
                    .getEcgDeviceRunMode
                            (), DeviceType.SERVER, ecgDeviceInfo.getPatientId(), ResponseCode.SUCESS);

            ctx.channel().writeAndFlush(packet);

            MptpLogUtils.i("心电设备:" + deviceId + "注册成功:" + ctx.channel().toString());
        }
    }

    /**
     * 验证mpcms设备是否通过
     *
     * @param action
     * @param patientId
     */
    public void verifyCmsMp(Action action, Integer patientId) throws IOException {

        Session session = null;
        RegisterMessage registerMessage = action.getRegisterMessage();
        ChannelHandlerContext ctx = action.getCtx();

        String deviceId = registerMessage.getDeviceId();

        if (patientId == null ){
            MptpLogUtils.e(String.format("%s 设备未绑定患者", deviceId));
        }else{
            int type = 0;

            session = new MpDeviceSession(deviceId);
            session.setKey(deviceId);
            session.setPatientId(patientId);

            Channel channel = SessionManager.get(session.getKey());
            if (channel != null && !ctx.channel().equals(channel)) {
                // 同一个设备另外重新
                type = 0;
            } else if (channel != null && ctx.channel().equals(channel)) {
                // 收到重复注册包
                type = 1;
            } else {
                // 新设备注册
                type = 2;
            }

            // 同一个设备另外重新
            // 设备不允许同时上线  用户运行多个端同时上线
            if (type == 0) {
                SessionManager.remove(channel, true);
                MptpLogUtils.e(String.format("重复注册 移除设备：%s 过期的Channel: %s", session.getKey(), channel.toString()));

                if (registerMessage.getKeepAliveTimer() != null)
                    session.setReaderIdleTimeSeconds(registerMessage
                            .getKeepAliveTimer());

                session.setChannel(ctx.channel());
                session.setSocketAddress(ctx.channel().remoteAddress());
                session.setLastPacketTime(new Date());
                session.setDeviceType(registerMessage.getDeviceType());

                SessionManager.add(ctx.channel(), session);
            }
            // 收到重复注册包
            else if (type == 1) {
                MptpLogUtils.e("同一个channel重复收到注册包");
            }
            // 新设备或者用户注册
            else {
                if (registerMessage.getKeepAliveTimer() != null)
                    session.setReaderIdleTimeSeconds(registerMessage
                            .getKeepAliveTimer());

                session.setChannel(ctx.channel());
                session.setSocketAddress(ctx.channel().remoteAddress());
                session.setLastPacketTime(new Date());
                session.setDeviceType(registerMessage.getDeviceType());

                SessionManager.add(ctx.channel(), session);
            }

            DataPacket resp = new DataPacket();
            com.mlnx.qcms.protocol.body.Command command  = new com.mlnx.qcms.protocol.body.Command();
            command.setCmdType(CmdType.CMD_BEGIN_COMMUNICATION_OK);
            resp.getBody().setCommand(command);
            resp.getHeader().setPackageNum(resp.getBody().calPackageNum());
            resp.getHeader().setPackageBytes(resp.getBody().calPackageBytes());
            resp.getHeader().setDeviceType(com.mlnx.qcms.protocol.head.DeviceType.CENTER_MONITOR_SERVER);

            ctx.channel().writeAndFlush(resp);

            MptpLogUtils.i("CMS设备:" + deviceId + "注册成功:" + ctx.channel().toString());
        }
    }

    /**
     * 用户验证
     * @param sucess
     * @param action
     */
    public void verifyUsr(boolean sucess, Action action) {

        Session session = null;
        RegisterMessage registerMessage = action.getRegisterMessage();
        ChannelHandlerContext ctx = action.getCtx();

        if (sucess) {

            int type = 0;

            session = new UsrSession();
            ((UsrSession) session).setUserName(registerMessage.getUsrName());
            ((UsrSession) session).setPassword(registerMessage.getPassword());
            session.setKey(registerMessage.getUsrName() + ":"
                    + registerMessage.getPassword());

            MptpLogUtils.i("用户:" + registerMessage.getUsrName() + " 注册:" + ctx.channel().toString());

            Session usrSession1 = SessionManager.get(ctx.channel());
            if (usrSession1 != null) {
                // 收到重复注册包
                MptpLogUtils.e("同一个channel重复收到注册包");
            } else {
                // 新用户注册
                if (registerMessage.getKeepAliveTimer() != null)
                    session.setReaderIdleTimeSeconds(registerMessage
                            .getKeepAliveTimer());

                session.setChannel(ctx.channel());
                session.setSocketAddress(ctx.channel().remoteAddress());
                session.setLastPacketTime(new Date());
                session.setDeviceType(registerMessage.getDeviceType());

                SessionManager.add(ctx.channel(), session);
            }

            PushPacket packet = new PushPacket().registerAck(registerMessage.getDeviceType(), ResponseCode.SUCESS);
            ctx.channel().writeAndFlush(packet);
        } else {
            MptpLogUtils.e(String.format("%s %s 用户名或密码验证失败", registerMessage.getUsrName(), registerMessage
                    .getPassword()));
            PushPacket packet = new PushPacket().registerAck(registerMessage.getDeviceType(),
                    ResponseCode.VERIFY_USR_ERR);
            ctx.channel().writeAndFlush(packet);
            return;
        }
    }
}
