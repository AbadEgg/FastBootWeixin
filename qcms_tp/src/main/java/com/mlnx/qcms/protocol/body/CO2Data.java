package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.ByteUtils;
import sun.security.util.Length;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * CO2参数
 *
 * @author fzh
 * @create 2018/1/23 16:20
 */
public class CO2Data extends DataHeader{

    float co2Value;
    float co2LimitHi;
    float co2LimitLow;
    float fiCO2Value;
    float fiCO2LimitHi;    //高限，无低限
    int awrrValue;
    int awrrLimitHi;
    int awrrLimitLow;

    int waveSampleNum;		//采样数据个数
    int[] waveData = new int[256];		//1秒的波形

    public float getCo2Value() {
        return co2Value;
    }

    public void setCo2Value(float co2Value) {
        this.co2Value = co2Value;
    }

    public float getCo2LimitHi() {
        return co2LimitHi;
    }

    public void setCo2LimitHi(float co2LimitHi) {
        this.co2LimitHi = co2LimitHi;
    }

    public float getCo2LimitLow() {
        return co2LimitLow;
    }

    public void setCo2LimitLow(float co2LimitLow) {
        this.co2LimitLow = co2LimitLow;
    }

    public float getFiCO2Value() {
        return fiCO2Value;
    }

    public void setFiCO2Value(float fiCO2Value) {
        this.fiCO2Value = fiCO2Value;
    }

    public float getFiCO2LimitHi() {
        return fiCO2LimitHi;
    }

    public void setFiCO2LimitHi(float fiCO2LimitHi) {
        this.fiCO2LimitHi = fiCO2LimitHi;
    }

    public int getAwrrValue() {
        return awrrValue;
    }

    public void setAwrrValue(int awrrValue) {
        this.awrrValue = awrrValue;
    }

    public int getAwrrLimitHi() {
        return awrrLimitHi;
    }

    public void setAwrrLimitHi(int awrrLimitHi) {
        this.awrrLimitHi = awrrLimitHi;
    }

    public int getAwrrLimitLow() {
        return awrrLimitLow;
    }

    public void setAwrrLimitLow(int awrrLimitLow) {
        this.awrrLimitLow = awrrLimitLow;
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

    public CO2Data() {
        packageType = PackageType.PACKAGE_CO2;
    }

    @Override
    public void decodeData(ByteBuffer buf) {
        co2Value = buf.getFloat();
        co2LimitHi = buf.getFloat();
        co2LimitLow = buf.getFloat();
        fiCO2Value = buf.getFloat();
        fiCO2LimitHi = buf.getFloat();
        byte[] b2 = new byte[2];
        buf.get(b2);
        awrrValue = ByteUtils.bytesToInt(b2,2);
        buf.get(b2);
        awrrLimitHi = ByteUtils.bytesToInt(b2,2);
        buf.get(b2);
        awrrLimitLow = ByteUtils.bytesToInt(b2,2);
        buf.get(b2);
        waveSampleNum = ByteUtils.bytesToInt(b2,2);
        for (int i = 0; i < 256 ; i++) {
            buf.get(b2);
            waveData[i] = ByteUtils.bytesToInt(b2,2);
        }
    }
}
