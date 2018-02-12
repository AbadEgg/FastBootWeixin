package com.mlnx.qcms.protocol.body;

import java.nio.ByteBuffer;

/**
 * CO参数
 *
 * @author fzh
 * @create 2018/1/23 17:01
 */
public class COData extends DataHeader{

    float COValue;
    float BTValue;
    float BTLimitHi;
    float BTLimitLow;

    public float getCOValue() {
        return COValue;
    }

    public void setCOValue(float COValue) {
        this.COValue = COValue;
    }

    public float getBTValue() {
        return BTValue;
    }

    public void setBTValue(float BTValue) {
        this.BTValue = BTValue;
    }

    public float getBTLimitHi() {
        return BTLimitHi;
    }

    public void setBTLimitHi(float BTLimitHi) {
        this.BTLimitHi = BTLimitHi;
    }

    public float getBTLimitLow() {
        return BTLimitLow;
    }

    public void setBTLimitLow(float BTLimitLow) {
        this.BTLimitLow = BTLimitLow;
    }

    public COData() {
        packageType = PackageType.PACKAGE_CO;
    }

    @Override
    public void decodeData(ByteBuffer buf) {

    }
}
