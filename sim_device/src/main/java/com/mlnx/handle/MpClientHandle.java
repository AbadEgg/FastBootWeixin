package com.mlnx.handle;

import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.support.ListenManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author fzh
 * @create 2018/3/26 16:40
 */
public class MpClientHandle extends SimpleChannelInboundHandler<MpPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MpPacket mpPacket) throws Exception {
        ListenManager.getInstance().getMpClientLis().receive(mpPacket);
    }
}
