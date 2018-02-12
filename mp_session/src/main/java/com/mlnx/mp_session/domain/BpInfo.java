package com.mlnx.mp_session.domain;

/**
 * Created by amanda.shan on 2018/2/12.
 */
public class BpInfo extends DeviceInfo{

    private Integer sbp;
    private Integer dbp;
    private Integer heart;

    public Integer getSbp() {
        return sbp;
    }

    public void setSbp(Integer sbp) {
        this.sbp = sbp;
    }

    public Integer getDbp() {
        return dbp;
    }

    public void setDbp(Integer dbp) {
        this.dbp = dbp;
    }

    public Integer getHeart() {
        return heart;
    }

    public void setHeart(Integer heart) {
        this.heart = heart;
    }
}
