package com.mlnx.qcms.protocol.body;

/**
 * @author fzh
 * @create 2018/1/24 10:39
 */
public class AlarmSettingElement {

    String paramName;
    int alarmHi;
    int alarmLow;
    int alarmLevel;
    AlarmSwith  alarmSwitch;
    int alarmLimitZoomValue;//报警限放大倍数
    byte reserve;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public int getAlarmHi() {
        return alarmHi;
    }

    public void setAlarmHi(int alarmHi) {
        this.alarmHi = alarmHi;
    }

    public int getAlarmLow() {
        return alarmLow;
    }

    public void setAlarmLow(int alarmLow) {
        this.alarmLow = alarmLow;
    }

    public int getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(int alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public AlarmSwith getAlarmSwitch() {
        return alarmSwitch;
    }

    public void setAlarmSwitch(AlarmSwith alarmSwitch) {
        this.alarmSwitch = alarmSwitch;
    }

    public int getAlarmLimitZoomValue() {
        return alarmLimitZoomValue;
    }

    public void setAlarmLimitZoomValue(int alarmLimitZoomValue) {
        this.alarmLimitZoomValue = alarmLimitZoomValue;
    }

    public byte getReserve() {
        return reserve;
    }

    public void setReserve(byte reserve) {
        this.reserve = reserve;
    }

}
