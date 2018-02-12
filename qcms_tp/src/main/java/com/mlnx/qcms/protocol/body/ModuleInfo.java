package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fzh
 * @create 2018/1/23 13:23
 */
public class ModuleInfo extends DataHeader{

    private byte[] moduleSwitch;			//模块开关
    private List<WaveDataInfo> moduleWaveInfo;	//模块波形描述

    public byte[] getModuleSwitch() {
        return moduleSwitch;
    }

    public void setModuleSwitch(byte[] moduleSwitch) {
        this.moduleSwitch = moduleSwitch;
    }

    public List<WaveDataInfo> getModuleWaveInfo() {
        return moduleWaveInfo;
    }

    public void setModuleWaveInfo(List<WaveDataInfo> moduleWaveInfo) {
        this.moduleWaveInfo = moduleWaveInfo;
    }

    @Override
    public String toString() {
        return "ModuleInfo{" +
                "packageType=" + packageType +
                ", checksum=" + checksum +
                ", packageLength=" + packageLength +
                ", moduleSwitch=" + Arrays.toString(moduleSwitch) +
                ", moduleWaveInfo=" + moduleWaveInfo +
                '}';
    }

    public ModuleInfo() {
        moduleSwitch = new byte[PackageType.PACKAGE_MODULE_END.getCode()];
        moduleWaveInfo = new ArrayList<>(WaveEnum.WAVE_MAX.getCode());
        packageType = PackageType.PACKAGE_MODULE_INFO;
    }

    @Override
    public void decodeData(ByteBuffer buf) {
        buf.get(moduleSwitch);
        for (int i = 0; i < WaveEnum.WAVE_MAX.getCode(); i++) {
            WaveDataInfo waveDataInfo = new WaveDataInfo();
            byte[] b2 = new byte[2];
            buf.get(b2);
            waveDataInfo.setWaveDataMinValue(ByteUtils.bytesToInt(b2,2));
            buf.get(b2);
            waveDataInfo.setWaveDataMaxValue(ByteUtils.bytesToInt(b2,2));
            buf.get(b2);
            waveDataInfo.setWaveDataBaseLine(ByteUtils.bytesToInt(b2,2));
            byte[] b4 = new byte[4];
            buf.get(b4);
            waveDataInfo.setWaveSamplingHz(ByteUtils.bytesToInt(b4));
            moduleWaveInfo.add(waveDataInfo);
        }

    }
}
