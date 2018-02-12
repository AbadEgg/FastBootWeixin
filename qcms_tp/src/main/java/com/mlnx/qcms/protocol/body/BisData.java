package com.mlnx.qcms.protocol.body;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Bis参数
 *
 * @author fzh
 * @create 2018/1/23 17:23
 */
public class BisData extends DataHeader{

    int bisValue;
    int bisHighLimt;
    int bisLowLimt;
    int emgValue;
    int srValue;
    int sqiValue;
    int tpValue;
    int sefValue;

    int waveSampleNum;		//采样数据个数
    int[] waveData = new int[256];		//1秒的波形

    public int getBisValue() {
        return bisValue;
    }

    public void setBisValue(int bisValue) {
        this.bisValue = bisValue;
    }

    public int getBisHighLimt() {
        return bisHighLimt;
    }

    public void setBisHighLimt(int bisHighLimt) {
        this.bisHighLimt = bisHighLimt;
    }

    public int getBisLowLimt() {
        return bisLowLimt;
    }

    public void setBisLowLimt(int bisLowLimt) {
        this.bisLowLimt = bisLowLimt;
    }

    public int getEmgValue() {
        return emgValue;
    }

    public void setEmgValue(int emgValue) {
        this.emgValue = emgValue;
    }

    public int getSrValue() {
        return srValue;
    }

    public void setSrValue(int srValue) {
        this.srValue = srValue;
    }

    public int getSqiValue() {
        return sqiValue;
    }

    public void setSqiValue(int sqiValue) {
        this.sqiValue = sqiValue;
    }

    public int getTpValue() {
        return tpValue;
    }

    public void setTpValue(int tpValue) {
        this.tpValue = tpValue;
    }

    public int getSefValue() {
        return sefValue;
    }

    public void setSefValue(int sefValue) {
        this.sefValue = sefValue;
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

    public BisData() {
        packageType = PackageType.PACKAGE_BIS;
    }

    @Override
    public void decodeData(ByteBuffer buf) {

    }
}
