package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * spo2参数
 *
 * @author fzh
 * @create 2018/1/23 15:25
 */
public class Spo2Data extends DataHeader{

    int spo2;				//spo2值
    int spo2HighLimt;		//高限
    int spo2LowLimt;		//低限
    int pr;				//pr
    int prHighLimt;		//pr Hight
    int prLowLimt;			//pr low
    int waveSampleNum;		//spo2采样数据个数
    int[] waveData = new int[128];		//1秒的波形

    public int getSpo2() {
        return spo2;
    }

    public void setSpo2(int spo2) {
        this.spo2 = spo2;
    }

    public int getSpo2HighLimt() {
        return spo2HighLimt;
    }

    public void setSpo2HighLimt(int spo2HighLimt) {
        this.spo2HighLimt = spo2HighLimt;
    }

    public int getSpo2LowLimt() {
        return spo2LowLimt;
    }

    public void setSpo2LowLimt(int spo2LowLimt) {
        this.spo2LowLimt = spo2LowLimt;
    }

    public int getPr() {
        return pr;
    }

    public void setPr(int pr) {
        this.pr = pr;
    }

    public int getPrHighLimt() {
        return prHighLimt;
    }

    public void setPrHighLimt(int prHighLimt) {
        this.prHighLimt = prHighLimt;
    }

    public int getPrLowLimt() {
        return prLowLimt;
    }

    public void setPrLowLimt(int prLowLimt) {
        this.prLowLimt = prLowLimt;
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

    public Spo2Data() {
        packageType = PackageType.PACKAGE_SPO2;
    }

    @Override
    public String toString() {
        return "Spo2Data{" +
                "spo2=" + spo2 +
                ", spo2HighLimt=" + spo2HighLimt +
                ", spo2LowLimt=" + spo2LowLimt +
                ", pr=" + pr +
                ", prHighLimt=" + prHighLimt +
                ", prLowLimt=" + prLowLimt +
                ", waveSampleNum=" + waveSampleNum +
                ", waveData=" + Arrays.toString(waveData) +
                '}';
    }

    @Override
    public void decodeData(ByteBuffer buf) {
        byte[] b2 = new byte[2];
        buf.get(b2);
        spo2 = ByteUtils.bytesToInt(b2,2);
        buf.get(b2);
        spo2HighLimt = ByteUtils.bytesToInt(b2,2);
        buf.get(b2);
        spo2LowLimt = ByteUtils.bytesToInt(b2,2);
        buf.get(b2);
        pr = ByteUtils.bytesToInt(b2,2);
        buf.get(b2);
        prHighLimt = ByteUtils.bytesToInt(b2,2);
        buf.get(b2);
        prLowLimt = ByteUtils.bytesToInt(b2,2);
        buf.get(b2);
        waveSampleNum = ByteUtils.bytesToInt(b2,2);
        for (int i = 0; i < 128 ; i++) {
            buf.get(b2);
            waveData[i] = ByteUtils.bytesToInt(b2,2);
        }
//        System.out.println(this.toString());
    }
}
