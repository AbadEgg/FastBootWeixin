package com.mlnx.ecg.store.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.cybermkd.mongo.kit.MongoBean;
import com.mlnx.ecg.store.utils.Base64Utils;
import com.mlnx.mptp.model.Ecg;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sun.misc.BASE64Encoder;

public class EcgMong extends MongoBean {

    private String id;

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

    private Integer probeElectrodeImpedance;

    private String data;

    public EcgMong() {
    }

    public EcgMong(Ecg ecg) {
        patientId = ecg.getPatientId();
        deivceId = ecg.getDeivceId();
        startTime = ecg.getStartTime();
        numChannels = ecg.getNumChannels();
        amplification = ecg.getAmplification();
        heartRate = ecg.getHeartRate();
        pose = ecg.getPose();
        batteryLevel = ecg.getBatteryLevel();
        signalStrength = ecg.getSignalStrength();
        probeChannelBias = ecg.getProbeChannelBias();
        probeElectrodeImpedance = ecg.getProbeElectrodeImpedance();
        samplingRate = ecg.getSamplingRate();

        data = Base64Utils.enc(ecg.getData()); //使用BASE64编码
    }

    public Ecg getEcg() throws IOException {

        Ecg ecg = new Ecg();

        ecg.setPatientId(patientId);
        ecg.setDeivceId(deivceId);
        ecg.setStartTime(startTime);
        ecg.setNumChannels(numChannels);
        ecg.setAmplification(amplification);
        ecg.setHeartRate(heartRate);
        ecg.setPose(pose);
        ecg.setBatteryLevel(batteryLevel);
        ecg.setSignalStrength(signalStrength);
        ecg.setProbeChannelBias(probeChannelBias);
        ecg.setProbeElectrodeImpedance(probeElectrodeImpedance);
        ecg.setSamplingRate(samplingRate);
        ecg.setData(Base64Utils.dec(data));

        return ecg;
    }

    public String getId() {
        return id;
    }

    @JSONField(name = "_id")
    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getPose() {
        return pose;
    }

    public void setPose(Integer pose) {
        this.pose = pose;
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

    public Integer getProbeElectrodeImpedance() {
        return probeElectrodeImpedance;
    }

    public void setProbeElectrodeImpedance(Integer probeElectrodeImpedance) {
        this.probeElectrodeImpedance = probeElectrodeImpedance;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        return format.format(new Date(startTime));
    }

    public static void main(String[] args) throws IOException {
        BASE64Encoder enc = new BASE64Encoder();

        byte[] bytes = new byte[4*1024];

        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Base64Utils.enc(bytes);
        }

        System.out.println((System.currentTimeMillis() - time) / 10000);


        String s = Base64Utils.enc(bytes);
        time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Base64Utils.dec(s);
        }

        System.out.println((System.currentTimeMillis() - time) / 10000);
    }
}
