package com.mlnx.qcms.protocol.body;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Ag参数
 *
 * @author fzh
 * @create 2018/1/23 16:47
 */
public class AgData extends DataHeader{

    float etCO2Value;
    float etCO2LimitHi;
    float etCO2LimitLow;
    float fiCO2Value;
    float fiCO2LimitHi;    //高限，无低限
    int awrrValue;
    int awrrLimitHi;
    int awrrLimitLow;
    //
    float etN2OValue;
    float etN2OLimitHi;
    float etN2OLimitLow;
    float fiN2OValue;
    float fiN2OLimitHi;
    float fiN2OLimitLow;
    //
    float etAAValue;
    float etAALimitHi;
    float etAALimitLow;
    float fiAAValue;
    float fiAALimitHi;
    float fiAALimitLow;
    float AAmacValue;
    String AAname;
    //
    float etO2Value;
    float etO2LimitHi;
    float etO2LimitLow;
    float fiO2Value;
    float fiO2LimitHi;
    float fiO2LimitLow;
    //
    int waveSampleNum;			//采样数据个数
    int[] waveCo2Data = new int[32];		//1秒的波形
    int[] waveN2OData = new int[32];		//1秒的波形
    int[] waveAAData = new int[32];
    int[] waveO2Data = new int[32];

    public float getEtCO2Value() {
        return etCO2Value;
    }

    public void setEtCO2Value(float etCO2Value) {
        this.etCO2Value = etCO2Value;
    }

    public float getEtCO2LimitHi() {
        return etCO2LimitHi;
    }

    public void setEtCO2LimitHi(float etCO2LimitHi) {
        this.etCO2LimitHi = etCO2LimitHi;
    }

    public float getEtCO2LimitLow() {
        return etCO2LimitLow;
    }

    public void setEtCO2LimitLow(float etCO2LimitLow) {
        this.etCO2LimitLow = etCO2LimitLow;
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

    public float getEtN2OValue() {
        return etN2OValue;
    }

    public void setEtN2OValue(float etN2OValue) {
        this.etN2OValue = etN2OValue;
    }

    public float getEtN2OLimitHi() {
        return etN2OLimitHi;
    }

    public void setEtN2OLimitHi(float etN2OLimitHi) {
        this.etN2OLimitHi = etN2OLimitHi;
    }

    public float getEtN2OLimitLow() {
        return etN2OLimitLow;
    }

    public void setEtN2OLimitLow(float etN2OLimitLow) {
        this.etN2OLimitLow = etN2OLimitLow;
    }

    public float getFiN2OValue() {
        return fiN2OValue;
    }

    public void setFiN2OValue(float fiN2OValue) {
        this.fiN2OValue = fiN2OValue;
    }

    public float getFiN2OLimitHi() {
        return fiN2OLimitHi;
    }

    public void setFiN2OLimitHi(float fiN2OLimitHi) {
        this.fiN2OLimitHi = fiN2OLimitHi;
    }

    public float getFiN2OLimitLow() {
        return fiN2OLimitLow;
    }

    public void setFiN2OLimitLow(float fiN2OLimitLow) {
        this.fiN2OLimitLow = fiN2OLimitLow;
    }

    public float getEtAAValue() {
        return etAAValue;
    }

    public void setEtAAValue(float etAAValue) {
        this.etAAValue = etAAValue;
    }

    public float getEtAALimitHi() {
        return etAALimitHi;
    }

    public void setEtAALimitHi(float etAALimitHi) {
        this.etAALimitHi = etAALimitHi;
    }

    public float getEtAALimitLow() {
        return etAALimitLow;
    }

    public void setEtAALimitLow(float etAALimitLow) {
        this.etAALimitLow = etAALimitLow;
    }

    public float getFiAAValue() {
        return fiAAValue;
    }

    public void setFiAAValue(float fiAAValue) {
        this.fiAAValue = fiAAValue;
    }

    public float getFiAALimitHi() {
        return fiAALimitHi;
    }

    public void setFiAALimitHi(float fiAALimitHi) {
        this.fiAALimitHi = fiAALimitHi;
    }

    public float getFiAALimitLow() {
        return fiAALimitLow;
    }

    public void setFiAALimitLow(float fiAALimitLow) {
        this.fiAALimitLow = fiAALimitLow;
    }

    public float getAAmacValue() {
        return AAmacValue;
    }

    public void setAAmacValue(float AAmacValue) {
        this.AAmacValue = AAmacValue;
    }

    public String getAAname() {
        return AAname;
    }

    public void setAAname(String AAname) {
        this.AAname = AAname;
    }

    public float getEtO2Value() {
        return etO2Value;
    }

    public void setEtO2Value(float etO2Value) {
        this.etO2Value = etO2Value;
    }

    public float getEtO2LimitHi() {
        return etO2LimitHi;
    }

    public void setEtO2LimitHi(float etO2LimitHi) {
        this.etO2LimitHi = etO2LimitHi;
    }

    public float getEtO2LimitLow() {
        return etO2LimitLow;
    }

    public void setEtO2LimitLow(float etO2LimitLow) {
        this.etO2LimitLow = etO2LimitLow;
    }

    public float getFiO2Value() {
        return fiO2Value;
    }

    public void setFiO2Value(float fiO2Value) {
        this.fiO2Value = fiO2Value;
    }

    public float getFiO2LimitHi() {
        return fiO2LimitHi;
    }

    public void setFiO2LimitHi(float fiO2LimitHi) {
        this.fiO2LimitHi = fiO2LimitHi;
    }

    public float getFiO2LimitLow() {
        return fiO2LimitLow;
    }

    public void setFiO2LimitLow(float fiO2LimitLow) {
        this.fiO2LimitLow = fiO2LimitLow;
    }

    public int getWaveSampleNum() {
        return waveSampleNum;
    }

    public void setWaveSampleNum(int waveSampleNum) {
        this.waveSampleNum = waveSampleNum;
    }

    public int[] getWaveCo2Data() {
        return waveCo2Data;
    }

    public void setWaveCo2Data(int[] waveCo2Data) {
        this.waveCo2Data = waveCo2Data;
    }

    public int[] getWaveN2OData() {
        return waveN2OData;
    }

    public void setWaveN2OData(int[] waveN2OData) {
        this.waveN2OData = waveN2OData;
    }

    public int[] getWaveAAData() {
        return waveAAData;
    }

    public void setWaveAAData(int[] waveAAData) {
        this.waveAAData = waveAAData;
    }

    public int[] getWaveO2Data() {
        return waveO2Data;
    }

    public void setWaveO2Data(int[] waveO2Data) {
        this.waveO2Data = waveO2Data;
    }

    public AgData() {
        packageType = PackageType.PACKAGE_AG;
    }

    @Override
    public void decodeData(ByteBuffer buf) {

    }
}
