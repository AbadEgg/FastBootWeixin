package com.mlnx.mptp.mptp.body.spo;

import com.mlnx.mptp.model.SPOResult;
import com.mlnx.mptp.mptp.InvalidPacketException;
import com.mlnx.mptp.mptp.body.GroupType;
import com.mlnx.mptp.mptp.config.MptpConfig;
import com.mlnx.mptp.utils.ByteUtils;
import com.mlnx.mptp.utils.MpLogLevelInfo;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.mptp.utils.ReadUtils;

import java.nio.ByteBuffer;

public class SPOBody {

    private static final ByteBuffer buffer = ByteBuffer
            .allocate(MptpConfig.SPO_BODY_CAPABILITY);
    private SPOResult spoResult;

    public void init(){
        spoResult = new SPOResult();
    }

    public SPOResult getBsResult() {
        return spoResult;
    }

    public void setBsResult(SPOResult bsResult) {
        this.spoResult = bsResult;
    }

    public void decodeData(GroupType groupType, ByteBuffer frame,
                           String dataType, int dataLen) throws InvalidPacketException {
        switch (groupType) {
            case SPO:
                Integer spo = ReadUtils.readData(frame, dataType, dataLen);
                spoResult.setResultSPO(spo);
                MptpLogUtils.mpDecode("收到血氧：" + spo);
                break;
            case SPO_HEART:
                Integer resultHeart = ReadUtils.readData(frame, dataType, dataLen);
                spoResult.setResultHeart(resultHeart);
                MptpLogUtils.mpDecode("收到血氧心率：" + resultHeart);
                break;
            case SPO_WAVE:
                byte[] spoWaveData = ReadUtils.readBytes(frame, dataType,
                        dataLen);
                spoResult.setSpoWave(spoWaveData);
                if (MpLogLevelInfo.getInstance().isOpenDecodeLog()) {
                    StringBuilder builder = new StringBuilder();
                    for (byte b : spoWaveData) {
                        builder.append(String.format("%x ", b));
                    }

                    MptpLogUtils.mpDecode("收到连续血氧脉搏波数据：" + builder.toString());
                }
                break;
        }
    }

    public byte[] encode() {

        buffer.clear();

        if (spoResult.getResultSPO() != null) {
            buffer.put(GroupType.SPO.getEncodes());
            buffer.put(new String("US").getBytes());
            byte[] bs = ByteUtils.intToBytes(spoResult.getResultSPO(), 2);
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }

        if (spoResult.getResultHeart() != null) {
            buffer.put(GroupType.SPO_HEART.getEncodes());
            buffer.put(new String("US").getBytes());
            byte[] bs = ByteUtils.intToBytes(spoResult.getResultHeart(), 2);
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }

        buffer.flip();
        byte[] bs = new byte[buffer.remaining()];
        buffer.get(bs);
        return bs;
    }

}
