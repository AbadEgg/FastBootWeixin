package com.mlnx.mp_server.handle.common;

import com.mlnx.mptp.mptp.InvalidPacketException;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.VersionManager;
import com.mlnx.mptp.mptp.head.Header;
import com.mlnx.mptp.utils.ByteUtils;
import com.mlnx.mptp.utils.MpLogLevelInfo;
import com.mlnx.mptp.utils.MptpLogUtils;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;

public class MpDecode extends ByteToMessageDecoder {

    enum State {
        HEAD, VERSION, LEN, CONTANT
    }

    private State state = State.HEAD;
    private int matchHeadIndex;
    private int length;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in,
                          List<Object> out) throws Exception {

//        if (MpLogLevelInfo.getInstance().isOpenFrameLog())
//            MptpLogUtils.mpFrame("recive  frame:" + ByteBufUtil.hexDump(in));

        switch (state) {
            case HEAD:
                if (matchHead(in)) {
                    state = State.VERSION;
                } else {
                    break;
                }
            case VERSION:

                if (in.isReadable()) {
                    byte code = in.readByte();
                    if (VersionManager.isSupport(code)) {
                        state = State.LEN;
                    } else {
                        int mainCode = (code >> 4) & 0x0000000f;
                        int subCode = code & 0x0f;

                        MptpLogUtils.e(String
                                .format("不支持的协议版本%d.%d", mainCode, subCode));
                        state = State.HEAD;
                        break;
                    }
                } else {
                    break;
                }
            case LEN:
                length = getPacketLength(in);

                if (length > Header.MaxLength) {
                    MptpLogUtils.w(String.format("数据包长度过长  实际包长度为%d", length));
                    state = State.HEAD;
                    break;
                } else if (length >= 0) {
                    state = State.CONTANT;
                    MptpLogUtils.mpFrame("frame len:" + length);
                }  else {
                    break;
                }
            case CONTANT:
                if (in.isReadable(length)) {

                    ByteBuf frame = in.readBytes(length);
                    MpPacket mpPacket = new MpPacket();
                    try {
                        if (MpLogLevelInfo.getInstance().isOpenFrameLog()) {
                            MptpLogUtils.mpFrame("recive  contant:" + ByteBufUtil.hexDump(frame));
                        }
                        mpPacket.decode(frame.nioBuffer());

                        out.add(mpPacket);
                    } catch (InvalidPacketException e) {
                        e.printStackTrace();
                    } finally {
                        ReferenceCountUtil.release(frame);
                    }

                    state = State.HEAD;
                }
                break;
                default:
        }
    }

    private boolean matchHead(ByteBuf buf) {

        while (buf.isReadable()) {
            if (matchHeadIndex == Header.Heads.length) {
                matchHeadIndex = 0;
                return true;
            }
            byte b = buf.readByte();
            if (b == Header.Heads[matchHeadIndex]) {
                matchHeadIndex++;
            } else if (b == Header.Heads[0]) {
                matchHeadIndex = 1;
            } else {
                matchHeadIndex = 0;
            }
        }
        return false;
    }

    private int getPacketLength(ByteBuf buf) {
        if (buf.isReadable(Header.LengthByteSize)) {
            byte[] dst = new byte[Header.LengthByteSize];
            buf.readBytes(dst);
            int length = ByteUtils.bytesToInt(dst, Header.LengthByteSize);

            return length;
        } else {
            return -1;
        }
    }

}
