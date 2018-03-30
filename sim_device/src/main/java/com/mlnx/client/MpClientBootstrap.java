package com.mlnx.client;

import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.utils.MptpLogUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @author fzh
 * @create 2018/3/26 16:21
 */
public class MpClientBootstrap {

    private int port;
    private String host;
    public SocketChannel socketChannel;
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

    private MpClientLis mpClientLis;

    public MpClientBootstrap(int port, String host, MpClientLis mpClientLis) {
        this.port = port;
        this.host = host;
        this.mpClientLis = mpClientLis;
        start();
    }
    private void start(){
        ChannelFuture future = null;
        try {
            EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.group(eventLoopGroup);
            bootstrap.remoteAddress(host,port);
            bootstrap.handler(new TcpClientInitializer(mpClientLis));
            future =bootstrap.connect(host,port).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel)future.channel();
                MptpLogUtils.i("connect server  成功---------");
            }else{
                MptpLogUtils.e("连接失败！");
                MptpLogUtils.i("准备重连！");
                start();
            }
        } catch (Exception e) {

        }
    }

    public void send(MpPacket mpPacket){
        socketChannel.writeAndFlush(mpPacket);
    }
}
