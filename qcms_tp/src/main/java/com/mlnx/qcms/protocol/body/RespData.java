package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.ByteUtils;

import java.nio.ByteBuffer;

/**
 * 响应参数
 *
 * @author fzh
 * @create 2018/1/23 15:33
 */
public class RespData extends DataHeader{

    int resp;				//resp值
    int respHighLimt;		//高限
    int respLowLimt;		//低限
    int waveSampleNum;		//resp采样数据个数
    int[] waveData = new int[512];		//1秒的波形

    public int getResp() {
        return resp;
    }

    public void setResp(int resp) {
        this.resp = resp;
    }

    public int getRespHighLimt() {
        return respHighLimt;
    }

    public void setRespHighLimt(int respHighLimt) {
        this.respHighLimt = respHighLimt;
    }

    public int getRespLowLimt() {
        return respLowLimt;
    }

    public void setRespLowLimt(int respLowLimt) {
        this.respLowLimt = respLowLimt;
    }

    public int getWaveSampleNum() {
        return waveSampleNum;
    }

    public void setWaveSampleNum(int waveSampleNum) {
        this.waveSampleNum = waveSampleNum;
    }

    public int[] getWaveData() {
        return waveData;
    }

    public void setWaveData(int[] waveData) {
        this.waveData = waveData;
    }

    public RespData() {
        packageType = PackageType.PACKAGE_RESP;
    }

    @Override
    public void decodeData(ByteBuffer buf) {
        byte[] b2 = new byte[2];
        buf.get(b2);
        resp = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        respHighLimt = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        respLowLimt = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        waveSampleNum = ByteUtils.bytesToSignInt(b2,2);
        for (int i = 0; i < 512; i++) {
            buf.get(b2);
            waveData[i] = ByteUtils.bytesToSignInt(b2,2);
        }

    }
}
