package com.mlnx.mp_server.handle.cms;

import com.mlnx.mp_server.protocol.AbstractMessage;
import com.mlnx.mp_server.protocol.BpMessage;
import com.mlnx.mp_server.protocol.EcgMessage;
import com.mlnx.mp_server.protocol.RegisterMessage;
import com.mlnx.mp_server.protocol.SpoMessage;
import com.mlnx.mp_session.core.DeviceSession;
import com.mlnx.mp_session.core.Session;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.mptp.model.ECGData;
import com.mlnx.qcms.protocol.DataPacket;
import com.mlnx.qcms.protocol.body.CmdType;
import com.mlnx.qcms.protocol.body.Command;
import com.mlnx.qcms.protocol.head.DeviceType;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by amanda.shan on 2018/2/11.
 */
public class CmsServerHandle extends SimpleChannelInboundHandler<DataPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataPacket dataPacket) throws Exception {

        AbstractMessage message = null;

        Session session = SessionManager.get(ctx.channel());

        SessionManager.refreshLastTime(ctx.channel());
        session.setTimeOut(false);

        if (dataPacket.getBody().getCommand() != null) {
            switch (dataPacket.getBody().getCommand().getCmdType()) {
                case CMD_BEGIN_COMMUNICATION:

                    RegisterMessage registerMessage = new RegisterMessage();
                    message = registerMessage;

                    String s = dataPacket.getBody().getUserInfoV1().getFirstName();
                    StringBuilder builder = new StringBuilder();
                    for (char c : s.toCharArray()) {
                        if (c != (char) 0) {
                            builder.append(c);
                        }
                    }
                    registerMessage.setDeviceId(builder.toString());
                    registerMessage.setDeviceType(com.mlnx.mptp.DeviceType.MP_DEVICE);

                    break;
                default:
                    break;
            }
        } else {

            String deviceId = null;
            if (session instanceof DeviceSession) {
                DeviceSession deviceSession = (DeviceSession) session;
                deviceId = deviceSession.getDeviceId();
            }

            if (dataPacket.getBody().getEcgData() != null) {

                EcgMessage ecgMessage = new EcgMessage();
                message = ecgMessage;
                ecgMessage.setDeviceId(deviceId);

                ECGData ecgData = new ECGData();
                ecgData.setSuccessionData(dataPacket.getBody().getEcgData().getWaveData());
                ecgData.setEcgHeart(dataPacket.getBody().getEcgData().getHr());

                ecgMessage.setEcgData(ecgData);
                ecgMessage.setPacketTime(System.currentTimeMillis());

                message.setDeviceType(com.mlnx.mptp.DeviceType.MP_DEVICE);
                ctx.fireChannelRead(message);

            }
            if (dataPacket.getBody().getSpo2Data() != null) {

                SpoMessage spoMessage = new SpoMessage();
                message = spoMessage;
                spoMessage.setDeviceId(deviceId);

                spoMessage.setPatientId(session.getPatientId());
                spoMessage.setPacketTime(System.currentTimeMillis());
                spoMessage.setSpo(dataPacket.getBody().getSpo2Data().getSpo2());
                spoMessage.setHeart(dataPacket.getBody().getSpo2Data().getPr());

                message.setDeviceType(com.mlnx.mptp.DeviceType.MP_DEVICE);
                ctx.fireChannelRead(message);

            }
            if (dataPacket.getBody().getNibpData() != null) {

                BpMessage bpMessage = new BpMessage();
                message = bpMessage;
                bpMessage.setDeviceId(deviceId);

                bpMessage.setPatientId(session.getPatientId());
                bpMessage.setPacketTime(System.currentTimeMillis());
                bpMessage.setSbp(dataPacket.getBody().getNibpData().getSysPress());
                bpMessage.setDbp(dataPacket.getBody().getNibpData().getDiaPress());

                message.setDeviceType(com.mlnx.mptp.DeviceType.MP_DEVICE);
                ctx.fireChannelRead(message);

            }

            DataPacket resp = new DataPacket();
            Command command = new Command();
            command.setCmdType(CmdType.CMD_CONNECT_HEART);
            resp.getBody().setCommand(command);
            resp.getHeader().setPackageNum(resp.getBody().calPackageNum());
            resp.getHeader().setPackageBytes(resp.getBody().calPackageBytes());
            resp.getHeader().setDeviceType(DeviceType.CENTER_MONITOR_SERVER);
            ctx.channel().writeAndFlush(resp);
        }

    }
}
