package com.mlnx.mptp.model;

public class BpResult {

    private Boolean confidence; // 结果可信度
    private Boolean sucess; // 结果成功
    private Integer real; // 0 1 都代表成功， 一个是可信， 一个是不可信 2 代表检测失败

    private Integer wear;// 佩戴方式标志位
    private Boolean wearMode;// 佩戴方式是否正确

    private boolean realResult; // 实时 历史 结果标记

    private Integer patientID;
    private Integer resultSbp;
    private Integer resultDbp;
    private Integer resultHeart;
    private Long resultTime;

    public boolean isRealResult() {
        return realResult;
    }

    public void setRealResult(boolean realResult) {
        this.realResult = realResult;
    }

    /**
     * 是否所有数据接受OK
     *
     * @return
     */
    public boolean resultOK() {
        return patientID != null || resultSbp != null || resultDbp != null
                || resultHeart != null || resultTime != null;
    }

    public Integer getReal() {
        return real;
    }

    public void setReal(Integer real) {
        this.real = real;
    }

    public Boolean isConfidence() {
        if (confidence != null)
            return confidence;
        else
            return false;
    }

    public void setConfidence(Boolean confidence) {
        this.confidence = confidence;
    }

    public Boolean isSucess() {
        if (sucess != null)
            return sucess;
        else
            return false;
    }

    public Boolean paramOK() {
        return patientID != null && resultSbp != null && resultDbp != null && resultHeart != null && resultTime !=
                null;
    }

    public void setSucess(Boolean isSucess) {
        this.sucess = isSucess;
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public Integer getResultSbp() {
        return resultSbp;
    }

    public void setResultSbp(Integer resultSbp) {
        this.resultSbp = resultSbp;
    }

    public Integer getResultDbp() {
        return resultDbp;
    }

    public void setResultDbp(Integer resultDbp) {
        this.resultDbp = resultDbp;
    }

    public Integer getResultHeart() {
        return resultHeart;
    }

    public void setResultHeart(Integer resultHeart) {
        this.resultHeart = resultHeart;
    }

    public Long getResultTime() {
        return resultTime;
    }

    public void setResultTime(Long resultTime) {
        this.resultTime = resultTime;
    }

    public Integer getWear() {
        return wear;
    }

    public void setWear(Integer wear) {
        this.wear = wear;
    }

    public Boolean getWearMode() {
        return wearMode;
    }

    public void setWearMode(Boolean wearMode) {
        this.wearMode = wearMode;
    }

    @Override
    public String toString() {
        return "BpResult [patientID=" + patientID + ", resultSbp=" + resultSbp
                + ", resultDbp=" + resultDbp + ", resultHeart=" + resultHeart
                + ", resultTime=" + resultTime + ",wearMode=" + wearMode + "]";
    }
}
