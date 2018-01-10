package com.mlnx.mp_server.handle.common;

import com.mlnx.mp_server.protocol.RegisterMessage;
import com.mlnx.mp_server.support.Action;
import com.mlnx.mp_server.support.MpSupportManager;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.body.ResponseCode;
import com.mlnx.mptp.mptp.head.DeviceType;
import com.mlnx.mptp.utils.MptpLogUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RegisterHandle extends
        SimpleChannelInboundHandler<RegisterMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                RegisterMessage registerMessage) throws Exception {

        String deviceID = registerMessage.getDeviceId();
        DeviceType deviceType = registerMessage.getDeviceType();

        switch (registerMessage.getDeviceType()) {
            case ECG_DEVICE:
                if (deviceID == null) {
                    MptpLogUtils.e("缺少  deviceID ");
                    MpPacket packet = new MpPacket().registerAck(deviceType,
                            ResponseCode.LACK_DEVICEID);
                    ctx.channel().writeAndFlush(packet);
                    return;
                } else {
                    Action action = new Action();
                    action.setActionType(Action.ActionType.ECG_DEVICE_REGISTER);
                    action.setCtx(ctx);
                    action.setRegisterMessage(registerMessage);

                    // 设备验证
                    MpSupportManager.getInstance().getEcgSupport().verifyEcg(action);
                }
                break;
            case USR:

                Action action = new Action();
                action.setActionType(Action.ActionType.USR_REGISTER);
                action.setCtx(ctx);
                action.setRegisterMessage(registerMessage);

                // 用户名密码验证
                MpSupportManager.getInstance().getUsrSupport().verifyUsr(action);
                return;
            case BP_DEVICE:
            case SBP_DEVICE:
            case MP_DEVICE:
                MptpLogUtils.e("不支持的设备类型 ：" + deviceType);
            {
                MpPacket packet = new MpPacket().registerAck(deviceType,
                        ResponseCode.NOT_SUPPORT_DEVICE_TYPE);
                ctx.channel().writeAndFlush(packet);
            }
            break;
            default:
                MptpLogUtils.e("设备类型非法 ：" + deviceType);
            {
                MpPacket packet = new MpPacket().registerAck(deviceType,
                        ResponseCode.ILLEGAL_DEVICE_TYPE);
                ctx.channel().writeAndFlush(packet);
            }
            return;
        }
    }

}
