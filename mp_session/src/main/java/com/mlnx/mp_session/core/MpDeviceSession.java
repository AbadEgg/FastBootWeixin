package com.mlnx.mp_session.core;

import com.mlnx.mp_session.domain.BpInfo;
import com.mlnx.mp_session.domain.EcgInfo;
import com.mlnx.mp_session.domain.SpoInfo;
import com.mlnx.mp_session.domain.TempInfo;
import com.mlnx.mptp.model.ECGData;

/**
 * Created by amanda.shan on 2018/2/11.
 */
public class MpDeviceSession extends DeviceSession {

    private SpoInfo spoInfo;
    private BpInfo bpInfo;
    private EcgInfo ecgInfo;
    private TempInfo tempInfo;

    public MpDeviceSession(String deviceId) {
        super(deviceId);

        spoInfo = new SpoInfo();
        bpInfo = new BpInfo();
        ecgInfo = new EcgInfo();
        tempInfo = new TempInfo();

        spoInfo.setDeivceId(deviceId);
        bpInfo.setDeivceId(deviceId);
        ecgInfo.setDeivceId(deviceId);
        tempInfo.setDeivceId(deviceId);

        ecgInfo.setEcgData(new ECGData());
    }

    public void setBpInfo(SpoInfo info){
        if (info.getResultSPO() != null){
            spoInfo.setResultSPO(info.getResultSPO());
        }
        if (info.getResultHeart() != null){
            spoInfo.setResultHeart(info.getResultHeart());
        }
    }

    public void setBpInfo(BpInfo info){
        if (info.getSbp() != null){
            bpInfo.setSbp(info.getSbp());
        }
        if (info.getDbp() != null){
            bpInfo.setDbp(info.getDbp());
        }
        if (info.getHeart() != null){
            bpInfo.setHeart(info.getHeart());
        }
    }

    public void setECGData(ECGData ecgData){
        if (ecgData.getEcgHeart() != null){
            ecgInfo.getEcgData().setEcgHeart(ecgData.getEcgHeart());
        }
    }

    public void setTempInfo(TempInfo tempData) {
        tempInfo.setTemp(tempData.getTemp());
    }

    public SpoInfo getSpoInfo() {
        return spoInfo;
    }

    public BpInfo getBpInfo() {
        return bpInfo;
    }

    public EcgInfo getEcgInfo() {
        return ecgInfo;
    }

    public TempInfo getTempInfo() {
        return tempInfo;
    }
}
