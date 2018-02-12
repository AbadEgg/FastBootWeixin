package com.mlnx.qcms.protocol.body;

/**
 * 波形数据
 *
 * @author fzh
 * @create 2018/1/23 13:54
 */
public class WaveDataInfo {
    int waveDataMinValue;
    int waveDataMaxValue;
    int waveDataBaseLine;
    int waveSamplingHz;

    public int getWaveDataMinValue() {
        return waveDataMinValue;
    }

    public void setWaveDataMinValue(int waveDataMinValue) {
        this.waveDataMinValue = waveDataMinValue;
    }

    public int getWaveDataMaxValue() {
        return waveDataMaxValue;
    }

    public void setWaveDataMaxValue(int waveDataMaxValue) {
        this.waveDataMaxValue = waveDataMaxValue;
    }

    public int getWaveDataBaseLine() {
        return waveDataBaseLine;
    }

    public void setWaveDataBaseLine(int waveDataBaseLine) {
        this.waveDataBaseLine = waveDataBaseLine;
    }

    public int getWaveSamplingHz() {
        return waveSamplingHz;
    }

    public void setWaveSamplingHz(int waveSamplingHz) {
        this.waveSamplingHz = waveSamplingHz;
    }

    @Override
    public String toString() {
        return "WaveDataInfo{" +
                "waveDataMinValue=" + waveDataMinValue +
                ", waveDataMaxValue=" + waveDataMaxValue +
                ", waveDataBaseLine=" + waveDataBaseLine +
                ", waveSamplingHz='" + waveSamplingHz + '\'' +
                '}';
    }

    public WaveDataInfo() {
        this.waveDataBaseLine = 0;
        this.waveDataMaxValue = 0;
        this.waveDataMinValue = 0;
        this.waveSamplingHz = 0;
    }


}
