package com.mlnx.mp_session.domain;

/**
 * @author fzh
 * @create 2018/3/20 13:47
 */
public class CO2Info extends DeviceInfo {

    private float co2Value;

    public float getCo2Value() {
        return co2Value;
    }

    public void setCo2Value(float co2Value) {
        this.co2Value = co2Value;
    }
}
