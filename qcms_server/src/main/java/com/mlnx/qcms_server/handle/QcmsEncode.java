package com.mlnx.qcms_server.handle;

import com.mlnx.qcms.protocol.DataPacket;
import com.mlnx.qcms.utils.QcmsLogUtils;
import com.mlnx.qcms.utils.StringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author fzh
 * @create 2018/2/1 11:25
 */
public class QcmsEncode extends MessageToByteEncoder<DataPacket> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DataPacket dataPacket, ByteBuf byteBuf) throws Exception {
        byte[] bs = dataPacket.encode();
//        byte[] bs = {0x51,0x43,0x4d,0x53,0x20,0x00,0x02,0x01,0x1d,0x00,0x00,0x00,0x00,0x00,
//        0x00,0x00, (byte) 0x82,0x0e,0x0d,0x00,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        byteBuf.writeBytes(bs);
        QcmsLogUtils.mpFrame("send  frame:" + ByteBufUtil.hexDump(bs));
    }

    public static void main(String[] args) {
        byte[] bs = {
//                0x51,0x43,0x4d,0x53,
//                0x20,
//                0x00,0x02,
//                0x01,0x1d,0x00,0x00,0x00,0x00,0x00,
//                0x00,0x00,
//                (byte) 0x82,0x0e,
                0x0d,0x00,
                0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
        };
        int value = 0;
        for (int i = 0; i < bs.length ; i++) {
            value += bs[i] & 0x000000ff;
        }
        System.out.println(String.format("0x%x",value));
    }
}
