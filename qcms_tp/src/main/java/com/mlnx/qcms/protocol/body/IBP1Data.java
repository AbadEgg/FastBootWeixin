package com.mlnx.qcms.protocol.body;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * IBP1参数
 *
 * @author fzh
 * @create 2018/1/23 17:06
 */
public class IBP1Data extends DataHeader{

    PackageType packageType;		//包类型
    byte checksum;			//校验核
    int packageLength;	//包长
    String ibpLabelName;	//ibp标名
    boolean bArtPressure;
    int sysPress;
    int sysPressHighLimt;
    int sysPressLowLimt;
    int meanPress;
    int meanPressHighLimt;
    int meanPressLowLimt;
    int diaPress;
    int diaPressHighLimt;
    int diaPressLowLimt;

    int waveSampleNum;		//采样数据个数
    int[] waveData = new int[256];		//1秒的波形

    public String getIbpLabelName() {
        return ibpLabelName;
    }

    public void setIbpLabelName(String ibpLabelName) {
        this.ibpLabelName = ibpLabelName;
    }

    public boolean isbArtPressure() {
        return bArtPressure;
    }

    public void setbArtPressure(boolean bArtPressure) {
        this.bArtPressure = bArtPressure;
    }

    public int getSysPress() {
        return sysPress;
    }

    public void setSysPress(int sysPress) {
        this.sysPress = sysPress;
    }

    public int getSysPressHighLimt() {
        return sysPressHighLimt;
    }

    public void setSysPressHighLimt(int sysPressHighLimt) {
        this.sysPressHighLimt = sysPressHighLimt;
    }

    public int getSysPressLowLimt() {
        return sysPressLowLimt;
    }

    public void setSysPressLowLimt(int sysPressLowLimt) {
        this.sysPressLowLimt = sysPressLowLimt;
    }

    public int getMeanPress() {
        return meanPress;
    }

    public void setMeanPress(int meanPress) {
        this.meanPress = meanPress;
    }

    public int getMeanPressHighLimt() {
        return meanPressHighLimt;
    }

    public void setMeanPressHighLimt(int meanPressHighLimt) {
        this.meanPressHighLimt = meanPressHighLimt;
    }

    public int getMeanPressLowLimt() {
        return meanPressLowLimt;
    }

    public void setMeanPressLowLimt(int meanPressLowLimt) {
        this.meanPressLowLimt = meanPressLowLimt;
    }

    public int getDiaPress() {
        return diaPress;
    }

    public void setDiaPress(int diaPress) {
        this.diaPress = diaPress;
    }

    public int getDiaPressHighLimt() {
        return diaPressHighLimt;
    }

    public void setDiaPressHighLimt(int diaPressHighLimt) {
        this.diaPressHighLimt = diaPressHighLimt;
    }

    public int getDiaPressLowLimt() {
        return diaPressLowLimt;
    }

    public void setDiaPressLowLimt(int diaPressLowLimt) {
        this.diaPressLowLimt = diaPressLowLimt;
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

    @Override
    public String toString() {
        return "IBP1Data{" +
                "packageType=" + packageType +
                ", checksum=" + checksum +
                ", packageLength=" + packageLength +
                ", ibpLabelName='" + ibpLabelName + '\'' +
                ", bArtPressure=" + bArtPressure +
                ", sysPress=" + sysPress +
                ", sysPressHighLimt=" + sysPressHighLimt +
                ", sysPressLowLimt=" + sysPressLowLimt +
                ", meanPress=" + meanPress +
                ", meanPressHighLimt=" + meanPressHighLimt +
                ", meanPressLowLimt=" + meanPressLowLimt +
                ", diaPress=" + diaPress +
                ", diaPressHighLimt=" + diaPressHighLimt +
                ", diaPressLowLimt=" + diaPressLowLimt +
                ", waveSampleNum=" + waveSampleNum +
                ", waveData=" + Arrays.toString(waveData) +
                '}';
    }

    public IBP1Data() {
        packageType = PackageType.PACKAGE_IBP1;
    }

    @Override
    public void decodeData(ByteBuffer buf) {

    }
}
