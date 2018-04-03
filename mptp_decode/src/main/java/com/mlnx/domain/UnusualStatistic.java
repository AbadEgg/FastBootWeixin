package com.mlnx.domain;

/**
 * 异常统计
 *
 * @author fzh
 * @create 2018/4/3 11:32
 */
public class UnusualStatistic {
    //纤维性颤动VF(心室)
    private int vfCount;
    //室性心动过速
    private int sxFastCount;
    //多联发室性早搏
    private int mostPbCount;
    //二连发室性早搏
    private int twoPbCount;
    //偶发室性早搏
    private int accidentalPbCount;
    //RONT
    private int rontCount;
    //多形PVC
    private int pvcCount;
    //二联律
    private int twollCount;
    //三联律
    private int threellCount;

    public int getVfCount() {
        return vfCount;
    }

    public void setVfCount(int vfCount) {
        this.vfCount = vfCount;
    }

    public int getSxFastCount() {
        return sxFastCount;
    }

    public void setSxFastCount(int sxFastCount) {
        this.sxFastCount = sxFastCount;
    }

    public int getMostPbCount() {
        return mostPbCount;
    }

    public void setMostPbCount(int mostPbCount) {
        this.mostPbCount = mostPbCount;
    }

    public int getTwoPbCount() {
        return twoPbCount;
    }

    public void setTwoPbCount(int twoPbCount) {
        this.twoPbCount = twoPbCount;
    }

    public int getAccidentalPbCount() {
        return accidentalPbCount;
    }

    public void setAccidentalPbCount(int accidentalPbCount) {
        this.accidentalPbCount = accidentalPbCount;
    }

    public int getRontCount() {
        return rontCount;
    }

    public void setRontCount(int rontCount) {
        this.rontCount = rontCount;
    }

    public int getPvcCount() {
        return pvcCount;
    }

    public void setPvcCount(int pvcCount) {
        this.pvcCount = pvcCount;
    }

    public int getTwollCount() {
        return twollCount;
    }

    public void setTwollCount(int twollCount) {
        this.twollCount = twollCount;
    }

    public int getThreellCount() {
        return threellCount;
    }

    public void setThreellCount(int threellCount) {
        this.threellCount = threellCount;
    }
}
