package com.mlnx.mp_session.domain;

/**
 * Created by amanda.shan on 2018/2/12.
 */
public class SpoInfo extends DeviceInfo{

    private Integer resultSPO;
    private Integer resultHeart;

    public Integer getResultSPO() {
        return resultSPO;
    }

    public void setResultSPO(Integer resultSPO) {
        this.resultSPO = resultSPO;
    }

    public Integer getResultHeart() {
        return resultHeart;
    }

    public void setResultHeart(Integer resultHeart) {
        this.resultHeart = resultHeart;
    }
}
