package com.mlnx.client;

import com.mlnx.config.Config;
import com.mlnx.handle.MpClientHandle;
import com.mlnx.mp_server.handle.common.MpDecode;
import com.mlnx.mp_server.handle.common.MpEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author fzh
 * @create 2018/3/26 16:34
 */
public class TcpClientInitializer extends ChannelInitializer<SocketChannel> {

    private MpClientLis mpClientLis;

    public TcpClientInitializer(MpClientLis mpClientLis) {
        this.mpClientLis = mpClientLis;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("idleStateHandler",
                new IdleStateHandler(Config.DeviceReaderIdleMaxTimeSeconds, 0, 0));

        ch.pipeline().addLast(new MpDecode());
        ch.pipeline().addLast(new MpEncode());
        ch.pipeline().addLast(new MpClientHandle(mpClientLis));
    }
}
