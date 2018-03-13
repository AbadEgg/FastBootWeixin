package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * nibp参数
 *
 * @author fzh
 * @create 2018/1/23 16:02
 */
public class NibpData extends DataHeader{

    boolean newMeasureValue;	//是否是一次新测量的值
    int sysPress;
    int sysPressHighLimt;
    int sysPressLowLimt;
    int meanPress;
    int meanPressHighLimt;
    int meanPressLowLimt;
    int diaPress;
    int diaPressHighLimt;
    int diaPressLowLimt;
    int intervel;
    NibpMeasureMode nibpMeasureMode;
    int remainSec;
    int pressure;
    int[] promptInfo = new int[64];
    //NIBP 参数视图除了单位全放这里；

    public boolean isNewMeasureValue() {
        return newMeasureValue;
    }

    public void setNewMeasureValue(boolean newMeasureValue) {
        this.newMeasureValue = newMeasureValue;
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

    public int getintervel() {
        return intervel;
    }

    public void setintervel(int intervel) {
        intervel = intervel;
    }

    public NibpMeasureMode getNibpMeasureMode() {
        return nibpMeasureMode;
    }

    public void setNibpMeasureMode(NibpMeasureMode nibpMeasureMode) {
        this.nibpMeasureMode = nibpMeasureMode;
    }

    public int getRemainSec() {
        return remainSec;
    }

    public void setRemainSec(int remainSec) {
        this.remainSec = remainSec;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int[] getPromptInfo() {
        return promptInfo;
    }

    public void setPromptInfo(int[] promptInfo) {
        this.promptInfo = promptInfo;
    }

    public NibpData() {
        packageType = PackageType.PACKAGE_NIBP;
    }

    @Override
    public String toString() {
        return "NibpData{" +
                "newMeasureValue=" + newMeasureValue +
                ", sysPress=" + sysPress +
                ", sysPressHighLimt=" + sysPressHighLimt +
                ", sysPressLowLimt=" + sysPressLowLimt +
                ", meanPress=" + meanPress +
                ", meanPressHighLimt=" + meanPressHighLimt +
                ", meanPressLowLimt=" + meanPressLowLimt +
                ", diaPress=" + diaPress +
                ", diaPressHighLimt=" + diaPressHighLimt +
                ", diaPressLowLimt=" + diaPressLowLimt +
                ", intervel=" + intervel +
                ", nibpMeasureMode=" + nibpMeasureMode +
                ", remainSec=" + remainSec +
                ", pressure=" + pressure +
                ", promptInfo=" + Arrays.toString(promptInfo) +
                '}';
    }

    @Override
    public void decodeData(ByteBuffer buf) {
        newMeasureValue = buf.get()==0x01?true:false;
        byte[] b2 = new byte[2];
        buf.get(b2);
        sysPress = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        sysPressHighLimt = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        sysPressLowLimt = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        meanPress = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        meanPressHighLimt = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        meanPressLowLimt = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        diaPress = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        diaPressHighLimt = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        diaPressLowLimt = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        intervel = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        nibpMeasureMode = NibpMeasureMode.decode(ByteUtils.bytesToSignInt(b2,2));
        buf.get(b2);
        remainSec = ByteUtils.bytesToSignInt(b2,2);
        buf.get(b2);
        pressure = ByteUtils.bytesToSignInt(b2,2);
        for (int i = 0; i < 64 ; i++) {
            buf.get(b2);
            promptInfo[i] = ByteUtils.bytesToSignInt(b2,2);
        }
//        System.out.println(this.toString());
    }
}
