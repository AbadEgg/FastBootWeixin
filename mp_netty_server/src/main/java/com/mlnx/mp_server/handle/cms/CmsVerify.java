package com.mlnx.mp_server.handle.cms;

import com.mlnx.mp_server.protocol.RegisterMessage;
import com.mlnx.mp_session.core.Session;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.qcms.protocol.DataPacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 设备是否注册验证
 *
 * @author Administrator
 */
public class CmsVerify extends SimpleChannelInboundHandler<DataPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataPacket dataPacket) throws Exception {

        if (dataPacket.getBody().getCommand() != null) {
            switch (dataPacket.getBody().getCommand().getCmdType()) {
                // 注册包
                case CMD_BEGIN_COMMUNICATION:

                    RegisterMessage registerMessage = new RegisterMessage();

                    String s = dataPacket.getBody().getUserInfoV1().getFirstName();
                    StringBuilder builder = new StringBuilder();
                    for (char c : s.toCharArray()) {
                        if (c != (char) 0) {
                            builder.append(c);
                        }
                    }
                    registerMessage.setDeviceId(builder.toString());
                    registerMessage.setDeviceType(com.mlnx.mptp.DeviceType.MP_DEVICE);


                    ctx.fireChannelRead(registerMessage);

                    break;
                default:
                    break;
            }
        } else {

            Session session = SessionManager.get(ctx.channel());
            if (session != null) {
                SessionManager.refreshLastTime(ctx.channel());
                session.setTimeOut(false);
                ctx.fireChannelRead(dataPacket);
            } else {
                MptpLogUtils.e("cms 设备未注册 ：" + ctx.channel());
            }

        }
    }

}
