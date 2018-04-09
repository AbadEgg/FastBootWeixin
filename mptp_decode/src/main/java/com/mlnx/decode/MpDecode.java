package com.mlnx.decode;

import com.mlnx.utils.EcgAnalysis;
import com.mlnx.domain.DataTime;
import com.mlnx.mptp.model.analysis.RealEcgAnalysResult;
import com.mlnx.mptp.mptp.InvalidPacketException;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.VersionManager;
import com.mlnx.mptp.mptp.head.Header;
import com.mlnx.mptp.utils.ByteUtils;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.utils.ByteBuffUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fzh
 * @create 2018/4/2 9:26
 */
public class MpDecode {

    private EcgAnalysis ecgAnalysis;

    enum State {
        HEAD, VERSION, LEN, CONTANT
    }

    private State state = State.HEAD;
    private int matchHeadIndex;
    private int length;

    public List<RealEcgAnalysResult> decode(String fileName) throws Exception {

        List<RealEcgAnalysResult> results = new ArrayList<>();

        FileInputStream fis = new FileInputStream(fileName);

        FileChannel channel = fis.getChannel();

        MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());

        byte[] bytes = null;
        MpPacket mpPacket = new MpPacket();

        while (byteBuffer.hasRemaining()) {
            switch (state) {
                case HEAD:
                    if (matchHead(byteBuffer)) {
                        state = State.VERSION;
                    } else {
                        break;
                    }
                case VERSION:

                    if (byteBuffer.hasRemaining()) {
                        byte code = byteBuffer.get();
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
                    length = getPacketLength(byteBuffer);

                    if (length > Header.MaxLength) {
                        MptpLogUtils.w(String.format("数据包长度过长  实际包长度为%d", length));
                        state = State.HEAD;
                        break;
                    } else if (length >= 0) {
                        state = State.CONTANT;
                        MptpLogUtils.mpFrame("frame len:" + length);
                    } else {
                        break;
                    }
                case CONTANT:
                    if (byteBuffer.remaining() >= length) {
                        bytes = new byte[length];
                        byteBuffer.get(bytes);
                        ByteBuffer bf = ByteBuffer.allocate(length);
                        ByteBuffUtils.addBytes(bf, bytes);
                        try {
                            mpPacket.decode(bf);
                            RealEcgAnalysResult realEcgAnalysResult = ecgAnalysis.realAnalysis(mpPacket.getBody().getEcgBody().getEcgData().getEncrySuccessionData(),mpPacket.getBody().getPacketTime());
                            results.add(realEcgAnalysResult);
                        } catch (InvalidPacketException e) {
                            e.printStackTrace();
                        } finally {
                            channel.close();
                            fis.close();
                        }

                        state = State.HEAD;
                    }
                    break;
                default:
            }
        }
        return results;
    }

    public DataTime getDataTime(String fileName) throws Exception {
        DataTime dataTime = new DataTime();

        FileInputStream fis = new FileInputStream(fileName);

        FileChannel channel = fis.getChannel();

        MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());

        byte[] bytes = null;
        MpPacket mpPacket = new MpPacket();

        while (byteBuffer.hasRemaining()) {
            switch (state) {
                case HEAD:
                    if (matchHead(byteBuffer)) {
                        state = State.VERSION;
                    } else {
                        break;
                    }
                case VERSION:

                    if (byteBuffer.hasRemaining()) {
                        byte code = byteBuffer.get();
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
                    length = getPacketLength(byteBuffer);

                    if (length > Header.MaxLength) {
                        MptpLogUtils.w(String.format("数据包长度过长  实际包长度为%d", length));
                        state = State.HEAD;
                        break;
                    } else if (length >= 0) {
                        state = State.CONTANT;
                        MptpLogUtils.mpFrame("frame len:" + length);
                    } else {
                        break;
                    }
                case CONTANT:
                    if (byteBuffer.remaining() >= length) {
                        bytes = new byte[length];
                        byteBuffer.get(bytes);
                        ByteBuffer bf = ByteBuffer.allocate(length);
                        ByteBuffUtils.addBytes(bf, bytes);
                        try {
                            mpPacket.decode(bf);
                            if(dataTime.getStartTime()==0){
                                dataTime.setStartTime(mpPacket.getBody().getPacketTime());
                            }
                            if(byteBuffer.hasRemaining()){
                                dataTime.setEndTime(mpPacket.getBody().getPacketTime());
                            }
                        } catch (InvalidPacketException e) {
                            e.printStackTrace();
                        } finally {
                            channel.close();
                            fis.close();
                        }

                        state = State.HEAD;
                    }
                    break;
                default:
            }
        }
        return dataTime;
    }

    private boolean matchHead(ByteBuffer buf) {

        while (buf.hasRemaining()) {
            if (matchHeadIndex == Header.Heads.length) {
                matchHeadIndex = 0;
                return true;
            }
            byte b = buf.get();
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

    private int getPacketLength(ByteBuffer buf) {
        if (buf.remaining() > Header.LengthByteSize) {
            byte[] dst = new byte[Header.LengthByteSize];
            buf.get(dst);
            int length = ByteUtils.bytesToInt(dst, Header.LengthByteSize);

            return length;
        } else {
            return -1;
        }
    }

    public MpDecode() {
        ecgAnalysis = new EcgAnalysis();
        try {
            ecgAnalysis.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
