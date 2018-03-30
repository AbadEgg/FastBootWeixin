package com.mlnx.handle;

import com.mlnx.client.MpClientLis;
import com.mlnx.mptp.mptp.MpPacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author fzh
 * @create 2018/3/26 16:40
 */
public class MpClientHandle extends SimpleChannelInboundHandler<MpPacket> {

    private MpClientLis mpClientLis;

    public MpClientHandle(MpClientLis mpClientLis) {
        this.mpClientLis = mpClientLis;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MpPacket mpPacket) throws Exception {
        mpClientLis.receive(mpPacket);
    }
}
