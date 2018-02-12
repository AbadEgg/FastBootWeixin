package com.mlnx.qcms.protocol.body;

import java.util.Date;

/**
 * @author fzh
 * @create 2018/1/24 8:45
 */
public class AlarmElement {

    AlarmSwith alarmSwith;
    AlarmLevel alarmLevel;
    int alarmOccurTime;
    boolean isNewOccure;

    public AlarmSwith getAlarmSwith() {
        return alarmSwith;
    }

    public void setAlarmSwith(AlarmSwith alarmSwith) {
        this.alarmSwith = alarmSwith;
    }

    public AlarmLevel getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(AlarmLevel alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public int getAlarmOccurTime() {
        return alarmOccurTime;
    }

    public void setAlarmOccurTime(int alarmOccurTime) {
        this.alarmOccurTime = alarmOccurTime;
    }

    public boolean isNewOccure() {
        return isNewOccure;
    }

    public void setNewOccure(boolean newOccure) {
        isNewOccure = newOccure;
    }

}
