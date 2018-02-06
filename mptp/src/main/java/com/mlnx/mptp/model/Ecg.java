package com.mlnx.mptp.model;

import java.util.Arrays;

public class Ecg {

    private Integer patientId;

    private String deivceId;

    private Long startTime;

    private Integer numChannels;

    private Integer samplingRate;

    private Integer amplification;

    private Integer heartRate;

    private Integer pose;

    private Integer batteryLevel;

    private Integer signalStrength;

    private Integer probeChannelBias;

    private Integer pei;    // 探头电极阻抗

    private byte[] data;    // 原始数据

    private byte[] encryData;    // 加密数据

    public Integer getPatientId() {

        return patientId;
    }

    public void setPatientId(Integer patientId) {

        this.patientId = patientId;
    }

    public String getDeivceId() {
        return deivceId;
    }

    public void setDeivceId(String deivceId) {
        this.deivceId = deivceId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Integer getNumChannels() {

        return numChannels;
    }

    public void setNumChannels(Integer numChannels) {

        this.numChannels = numChannels;
    }

    public Integer getSamplingRate() {

        return samplingRate;
    }

    public void setSamplingRate(Integer samplingRate) {

        this.samplingRate = samplingRate;
    }

    public Integer getAmplification() {

        return amplification;
    }

    public void setAmplification(Integer amplification) {

        this.amplification = amplification;
    }

    public Integer getHeartRate() {

        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {

        this.heartRate = heartRate;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Integer getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(Integer signalStrength) {
        this.signalStrength = signalStrength;
    }

    public Integer getProbeChannelBias() {
        return probeChannelBias;
    }

    public void setProbeChannelBias(Integer probeChannelBias) {
        this.probeChannelBias = probeChannelBias;
    }

    public Integer getPei() {
        return pei;
    }

    public void setPei(Integer pei) {
        this.pei = pei;
    }

    public Integer getPose() {

        return pose;
    }

    public void setPose(Integer pose) {

        this.pose = pose;
    }

    public byte[] getData() {

        return data;
    }

    public void setData(byte[] data) {

        this.data = data;
    }

    public byte[] getEncryData() {
        return encryData;
    }

    public void setEncryData(byte[] encryData) {
        this.encryData = encryData;
    }

    @Override
    public String toString() {
        return "Ecg{" +
                "patientId=" + patientId +
                ", deivceId='" + deivceId + '\'' +
                ", startTime=" + startTime +
                ", numChannels=" + numChannels +
                ", samplingRate=" + samplingRate +
                ", amplification=" + amplification +
                ", heartRate=" + heartRate +
                ", pose=" + pose +
                ", batteryLevel=" + batteryLevel +
                ", signalStrength=" + signalStrength +
                ", probeChannelBias=" + probeChannelBias +
                ", pei=" + pei +
                ", data=" + Arrays.toString(data) +
                ", encryData=" + Arrays.toString(encryData) +
                '}';
    }
}
