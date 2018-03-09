package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.ByteUtils;
import com.mlnx.qcms.utils.WaveformUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Ecg参数
 *
 * @author fzh
 * @create 2018/1/23 15:12
 */
public class EcgData extends DataHeader{

    EcgLeadType ecgLeadType;
    int hr;				//hr值
    int hrHighLimt;		//高限
    int hrLowLimt;			//低限
    int pvcs;				//pvcs值
    int pvcsHighLimit;     //pvcs高限，无低限
    int[] st = new int[12];            //最多12通道ST值(12导)
    int[] stHighLimit = new int[12];   //st高限
    int[] stLowLimit = new int[12];    //st低限
    int waveSampleNum;		//ecg波形采样数据个数
    int[] waveData = new int[512];		//1秒的波形 2个字节拼成1个int

    public EcgLeadType getEcgLeadType() {
        return ecgLeadType;
    }

    public void setEcgLeadType(EcgLeadType ecgLeadType) {
        this.ecgLeadType = ecgLeadType;
    }

    public int getHr() {
        return hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }

    public int getHrHighLimt() {
        return hrHighLimt;
    }

    public void setHrHighLimt(int hrHighLimt) {
        this.hrHighLimt = hrHighLimt;
    }

    public int getHrLowLimt() {
        return hrLowLimt;
    }

    public void setHrLowLimt(int hrLowLimt) {
        this.hrLowLimt = hrLowLimt;
    }

    public int getPvcs() {
        return pvcs;
    }

    public void setPvcs(int pvcs) {
        this.pvcs = pvcs;
    }

    public int getPvcsHighLimit() {
        return pvcsHighLimit;
    }

    public void setPvcsHighLimit(int pvcsHighLimit) {
        this.pvcsHighLimit = pvcsHighLimit;
    }

    public int[] getSt() {
        return st;
    }

    public void setSt(int[] st) {
        this.st = st;
    }

    public int[] getStHighLimit() {
        return stHighLimit;
    }

    public void setStHighLimit(int[] stHighLimit) {
        this.stHighLimit = stHighLimit;
    }

    public int[] getStLowLimit() {
        return stLowLimit;
    }

    public void setStLowLimit(int[] stLowLimit) {
        this.stLowLimit = stLowLimit;
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

    public EcgData() {
        packageType = PackageType.PACKAGE_ECG;
    }

    @Override
    public String toString() {
        return "EcgData{" +
                "ecgLeadType=" + ecgLeadType +
                ", hr=" + hr +
                ", hrHighLimt=" + hrHighLimt +
                ", hrLowLimt=" + hrLowLimt +
                ", pvcs=" + pvcs +
                ", pvcsHighLimit=" + pvcsHighLimit +
                ", st=" + Arrays.toString(st) +
                ", stHighLimit=" + Arrays.toString(stHighLimit) +
                ", stLowLimit=" + Arrays.toString(stLowLimit) +
                ", waveSampleNum=" + waveSampleNum +
                ", waveData=" + Arrays.toString(waveData) +
                '}';
    }

    @Override
    public void decodeData(ByteBuffer byteBuffer){
        byte[] b2 = new byte[2];
        byteBuffer.get(b2);
        ecgLeadType = EcgLeadType.decode(ByteUtils.bytesToInt(b2,2));
        byteBuffer.get(b2);
        hr = ByteUtils.bytesToInt(b2,2);
        byteBuffer.get(b2);
        hrHighLimt = ByteUtils.bytesToInt(b2,2);
        byteBuffer.get(b2);
        hrLowLimt = ByteUtils.bytesToInt(b2,2);
        byteBuffer.get(b2);
        pvcs = ByteUtils.bytesToInt(b2,2);
        byteBuffer.get(b2);
        pvcsHighLimit = ByteUtils.bytesToInt(b2,2);
        for (int i = 0; i < 12; i++) {
            byteBuffer.get(b2);
            st[i] = ByteUtils.bytesToInt(b2,2);
        }
        for (int i = 0; i < 12; i++) {
            byteBuffer.get(b2);
            stHighLimit[i] = ByteUtils.bytesToInt(b2,2);
        }
        for (int i = 0; i < 12; i++) {
            byteBuffer.get(b2);
            stLowLimit[i] = ByteUtils.bytesToInt(b2,2);
        }
        byteBuffer.get(b2);
        waveSampleNum = ByteUtils.bytesToInt(b2,2);
        for (int i = 0; i < 512 ; i++) {
            byteBuffer.get(b2);
            waveData[i] = ByteUtils.bytesToInt(b2,2);
        }
        WaveformUtils.printConsole(WaveformUtils.getDots(waveData));
    }
}
