package com.mlnx.local.data.domain;

import java.util.Date;

/**
 * @author fzh
 * @create 2018/3/14 11:31
 */
public class BpAvg {

    //病人id
    private Integer patientId;

    //日平均舒张压
    private Integer diastolicAvg;

    //日平均收缩压
    private Integer systolicAvg;

    //日平均脉搏数
    private Integer heartAvg;

    //记录血压日期
    private Date dayTime;

    //白天平均舒张压
    private Integer diastolicDayAvg;

    //白天平均收缩压
    private Integer systolicDayAvg;

    //白天平均脉搏数
    private Integer heartDayAvg;

    //晚上平均舒张压
    private Integer diastolicNightAvg;

    //晚上平均收缩压
    private Integer systolicNightAvg;

    //晚上平均脉搏数
    private Integer heartNightAvg;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getDiastolicAvg() {
        return diastolicAvg;
    }

    public void setDiastolicAvg(Integer diastolicAvg) {
        this.diastolicAvg = diastolicAvg;
    }

    public Integer getSystolicAvg() {
        return systolicAvg;
    }

    public void setSystolicAvg(Integer systolicAvg) {
        this.systolicAvg = systolicAvg;
    }

    public Integer getHeartAvg() {
        return heartAvg;
    }

    public void setHeartAvg(Integer heartAvg) {
        this.heartAvg = heartAvg;
    }

    public Date getDayTime() {
        return dayTime;
    }

    public void setDayTime(Date dayTime) {
        this.dayTime = dayTime;
    }

    public Integer getDiastolicDayAvg() {
        return diastolicDayAvg;
    }

    public void setDiastolicDayAvg(Integer diastolicDayAvg) {
        this.diastolicDayAvg = diastolicDayAvg;
    }

    public Integer getSystolicDayAvg() {
        return systolicDayAvg;
    }

    public void setSystolicDayAvg(Integer systolicDayAvg) {
        this.systolicDayAvg = systolicDayAvg;
    }

    public Integer getHeartDayAvg() {
        return heartDayAvg;
    }

    public void setHeartDayAvg(Integer heartDayAvg) {
        this.heartDayAvg = heartDayAvg;
    }

    public Integer getDiastolicNightAvg() {
        return diastolicNightAvg;
    }

    public void setDiastolicNightAvg(Integer diastolicNightAvg) {
        this.diastolicNightAvg = diastolicNightAvg;
    }

    public Integer getSystolicNightAvg() {
        return systolicNightAvg;
    }

    public void setSystolicNightAvg(Integer systolicNightAvg) {
        this.systolicNightAvg = systolicNightAvg;
    }

    public Integer getHeartNightAvg() {
        return heartNightAvg;
    }

    public void setHeartNightAvg(Integer heartNightAvg) {
        this.heartNightAvg = heartNightAvg;
    }
}
