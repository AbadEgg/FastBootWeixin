package com.mlnx.mp_session.domain;

import com.mlnx.mptp.model.ECGData;
import com.mlnx.mptp.model.ECGDeviceInfo;
import com.mlnx.mptp.model.analysis.RealEcgAnalysResult;

/**
 * Created by amanda.shan on 2018/2/11.
 */
public class EcgInfo extends DeviceInfo{

    private ECGDeviceInfo ecgDeviceInfo;

    private ECGData ecgData;

    private RealEcgAnalysResult realEcgAnalysResult;

    public ECGDeviceInfo getEcgDeviceInfo() {
        return ecgDeviceInfo;
    }

    public void setEcgDeviceInfo(ECGDeviceInfo ecgDeviceInfo) {
        this.ecgDeviceInfo = ecgDeviceInfo;
    }

    public ECGData getEcgData() {
        return ecgData;
    }

    public void setEcgData(ECGData ecgData) {
        this.ecgData = ecgData;
    }

    public RealEcgAnalysResult getRealEcgAnalysResult() {
        return realEcgAnalysResult;
    }

    public void setRealEcgAnalysResult(RealEcgAnalysResult realEcgAnalysResult) {
        this.realEcgAnalysResult = realEcgAnalysResult;
    }

    @Override
    public String toString() {
        return "EcgInfo{" +
                "ecgDeviceInfo=" + ecgDeviceInfo +
                ", ecgData=" + ecgData +
                ", realEcgAnalysResult=" + realEcgAnalysResult +
                '}';
    }
}
