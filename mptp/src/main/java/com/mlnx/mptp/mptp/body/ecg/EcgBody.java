package com.mlnx.mptp.mptp.body.ecg;

import com.mlnx.device.ecg.ECGChannelType;
import com.mlnx.device.ecg.ECGDeviceRunMode;
import com.mlnx.mptp.model.ECGData;
import com.mlnx.mptp.model.ECGDeviceInfo;
import com.mlnx.mptp.mptp.InvalidPacketException;
import com.mlnx.mptp.mptp.body.GroupType;
import com.mlnx.mptp.mptp.config.MptpConfig;
import com.mlnx.mptp.utils.ByteUtils;
import com.mlnx.mptp.utils.MpLogLevelInfo;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.mptp.utils.ReadUtils;

import java.nio.ByteBuffer;

public class EcgBody {
    private static final ByteBuffer buffer = ByteBuffer
            .allocate(MptpConfig.ECG_BODY_CAPABILITY);

    private ECGDeviceInfo ecgDeviceInfo;

    private ECGData ecgData;

    public EcgBody() {
        ecgDeviceInfo = new ECGDeviceInfo();
        ecgData = new ECGData();
    }

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

    public void decodeData(GroupType groupType, ByteBuffer frame,
                           String dataType, int dataLen) throws InvalidPacketException {

        int code;

        switch (groupType) {
            case ECG_DEVICE_MODE:

                code = ReadUtils.readData(frame, dataType, dataLen);
                ECGDeviceRunMode ecgDeviceRunMode = ECGDeviceRunMode.decode(code);
                if (ecgDeviceRunMode == null) {
                    throw new InvalidPacketException("设备模式不符合要求，收到的模式编码是：" + code);
                }

                ecgDeviceInfo.setEcgDeviceRunMode(ecgDeviceRunMode);
                MptpLogUtils.mpDecode("收到心电设备模式：" + ecgDeviceRunMode);
                break;

            case ECG_CHANNEL_TYPE:
                code = ReadUtils.readData(frame, dataType, dataLen);
                ECGChannelType ecgChannelType = ECGChannelType.decode(code);

                if (ecgChannelType == null) {
                    throw new InvalidPacketException("设备通道不符合要求，收到的通道编码是：" + code);
                }

                ecgDeviceInfo.setEcgChannelType(ecgChannelType);

                MptpLogUtils.mpDecode("收到设备通道类型：" + ecgChannelType);
                break;

            case PACKET_INTERVAL:
                Integer packetInterval = ReadUtils.readData(frame, dataType,
                        dataLen);
                ecgDeviceInfo.setPacketInterval(packetInterval);
                MptpLogUtils.mpDecode("收到设备发包间隔：" + packetInterval);
                break;

            case BATTERY_LEVEL:
                Integer batteryLevel = ReadUtils.readData(frame, dataType, dataLen);
                ecgDeviceInfo.setBatteryLevel(batteryLevel);
                MptpLogUtils.mpDecode("收到设备电池电量：" + batteryLevel);
                break;

            case SIGNAL_STRENGTH:
                Integer signalStrength = ReadUtils.readData(frame, dataType,
                        dataLen);
                ecgDeviceInfo.setSignalStrength(signalStrength);
                MptpLogUtils.mpDecode("收到设备信号强度：" + signalStrength);
                break;

            case SD_REMAIN:
                Integer sdRemain = ReadUtils.readData(frame, dataType, dataLen);
                ecgDeviceInfo.setSdRemain(sdRemain);
                MptpLogUtils.mpDecode("收到sd卡剩余容量：" + sdRemain);
                break;

            case SD_CAPACITY:
                Integer sdCapacity = ReadUtils.readData(frame, dataType, dataLen);
                ecgDeviceInfo.setSdCapacity(sdCapacity);
                MptpLogUtils.mpDecode("收到sd卡总容量：" + sdCapacity);
                break;

            case MAGNIFICATION:
                Integer magnification = ReadUtils
                        .readData(frame, dataType, dataLen);
                ecgDeviceInfo.setMagnification(magnification);
                MptpLogUtils.mpDecode("收到放大倍数：" + magnification);
                break;

            case SAMPLING:
                Integer sampling = ReadUtils.readData(frame, dataType, dataLen);
                ecgDeviceInfo.setSampling(sampling);
                MptpLogUtils.mpDecode("收到采样率：" + sampling);
                break;

            case PROBE_CHANNEL_BIAS:
                Integer probeChannelBias = ReadUtils.readData(frame, dataType,
                        dataLen);
                ecgDeviceInfo.setProbeChannelBias(probeChannelBias);
                MptpLogUtils.mpDecode("收到探头通道偏压：" + probeChannelBias);
                break;

            case PROBE_ELECTRODE_IMPEDANCE:
                Integer probeElectrodeImpedance = ReadUtils.readData(frame,
                        dataType, dataLen);
                ecgDeviceInfo.setPei(probeElectrodeImpedance);
                MptpLogUtils.mpDecode("收到探头电极阻抗：" + probeElectrodeImpedance);
                break;
            // case PROBE_CHANNEL_BIAS:
            // ProbeChannelBiasMode probeChannelBiasMode = ProbeChannelBiasMode
            // .decode(ReadUtils.readData(frame, dataType, dataLen));
            // ecgDeviceInfo.setProbeChannelBiasMode(probeChannelBiasMode);
            // MptpLogUtils.mpDecode("收到探头通道偏压：" + probeChannelBiasMode);
            // break;
            //
            // case PROBE_ELECTRODE_IMPEDANCE:
            // short s = (short) ReadUtils.readData(frame, dataType, dataLen);
            // List<ProbeElectrodeImpedanceMode> probeElectrodeImpedanceModes = new
            // ArrayList<ProbeElectrodeImpedanceMode>();
            // for (int mpDecode = 0; mpDecode < 10; mpDecode++) {
            // probeElectrodeImpedanceModes.add(null);
            // }
            //
            // for (int mpDecode = 9; mpDecode >= 0; mpDecode--) {
            // probeElectrodeImpedanceModes.set(mpDecode,
            // ProbeElectrodeImpedanceMode.decode(s &= 0x0001));
            // s >>= 1;
            // }
            // ecgDeviceInfo
            // .setProbeElectrodeImpedanceModes(probeElectrodeImpedanceModes);
            // MptpLogUtils.mpDecode("收到探头电极阻抗：" + probeElectrodeImpedanceModes);
            // break;

            case WEAR_MODE:
                WearMode wearMode = WearMode.decode(ReadUtils.readData(frame,
                        dataType, dataLen));
                ecgDeviceInfo.setWearMode(wearMode);
                MptpLogUtils.mpDecode("收到设备佩戴方式：" + wearMode);
                break;

            case ECG_HEART:
                Integer ecgHeart = ReadUtils.readData(frame, dataType, dataLen);
                ecgData.setEcgHeart(ecgHeart);
                MptpLogUtils.mpDecode("收到心电设备心率：" + ecgHeart);
                break;

            case ACCELERATION_SENSOR_DATA:
                Integer accelerationSensorData = ReadUtils.readData(frame,
                        dataType, dataLen);
                ecgData.setAsd(accelerationSensorData);
                MptpLogUtils.mpDecode("收到加速传感器数据：" + accelerationSensorData);
                break;

            case BREATH:
                Integer breath = ReadUtils.readData(frame, dataType, dataLen);
                ecgData.setBreath(breath);
                MptpLogUtils.mpDecode("收到呼吸：" + breath);
                break;

            case ENCRY_SUCCESSION_DATA:
                byte[] encrySuccessionData = ReadUtils.readBytes(frame, dataType,
                        dataLen);
                ecgData.setEncrySuccessionData(encrySuccessionData);

                if (MpLogLevelInfo.getInstance().isOpenDecodeLog()) {
                    StringBuilder builder = new StringBuilder();
                    for (byte b : encrySuccessionData) {
                        builder.append(String.format("%x ", b));
                    }

                    MptpLogUtils.mpDecode("收到加密连续数据：" + builder.toString());
                }
                break;
            case SUCCESSION_DATA:
                byte[] successionData = ReadUtils.readBytes(frame, dataType,
                        dataLen);
                ecgData.setSuccessionData(successionData);

                if (MpLogLevelInfo.getInstance().isOpenDecodeLog()) {
                    StringBuilder builder = new StringBuilder();
                    for (byte b : successionData) {
                        builder.append(String.format("%x ", b));
                    }

                    MptpLogUtils.mpDecode("收到未加密连续数据：" + builder.toString());
                }
                break;
        }

    }

    public byte[] encode() {

        buffer.clear();

        if (ecgDeviceInfo.getEcgDeviceRunMode() != null) {
            buffer.put(GroupType.ECG_DEVICE_MODE.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put((byte) ecgDeviceInfo.getEcgDeviceRunMode().getCode());
        }

        if (ecgDeviceInfo.getEcgChannelType() != null) {
            buffer.put(GroupType.ECG_CHANNEL_TYPE.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put((byte) ecgDeviceInfo.getEcgChannelType().getCode());
        }

        if (ecgDeviceInfo.getPacketInterval() != null) {
            buffer.put(GroupType.PACKET_INTERVAL.getEncodes());
            buffer.put(new String("US").getBytes());
            byte[] bs = ByteUtils.intToBytes(ecgDeviceInfo.getPacketInterval(),
                    2);
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }

        if (ecgDeviceInfo.getBatteryLevel() != null) {
            buffer.put(GroupType.BATTERY_LEVEL.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put(ecgDeviceInfo.getBatteryLevel().byteValue());
        }

        if (ecgDeviceInfo.getSignalStrength() != null) {
            buffer.put(GroupType.SIGNAL_STRENGTH.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put(ecgDeviceInfo.getSignalStrength().byteValue());
        }

        if (ecgDeviceInfo.getSdRemain() != null) {
            buffer.put(GroupType.SD_REMAIN.getEncodes());
            buffer.put(new String("US").getBytes());
            byte[] bs = ByteUtils.intToBytes(ecgDeviceInfo.getSdRemain(), 2);
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }

        if (ecgDeviceInfo.getSdCapacity() != null) {
            buffer.put(GroupType.SD_CAPACITY.getEncodes());
            buffer.put(new String("US").getBytes());
            byte[] bs = ByteUtils.intToBytes(ecgDeviceInfo.getSdCapacity(), 2);
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }

        if (ecgDeviceInfo.getMagnification() != null) {
            buffer.put(GroupType.MAGNIFICATION.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put(ecgDeviceInfo.getMagnification().byteValue());
        }

        if (ecgDeviceInfo.getSampling() != null) {
            buffer.put(GroupType.SAMPLING.getEncodes());
            buffer.put(new String("US").getBytes());
            byte[] bs = ByteUtils.intToBytes(ecgDeviceInfo.getSampling(), 2);
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }

        if (ecgDeviceInfo.getProbeChannelBias() != null) {
            buffer.put(GroupType.PROBE_CHANNEL_BIAS.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put((byte) ecgDeviceInfo.getProbeChannelBias().byteValue());
        }

        if (ecgDeviceInfo.getPei() != null) {
            buffer.put(GroupType.PROBE_ELECTRODE_IMPEDANCE.getEncodes());
            buffer.put(new String("US").getBytes());
            byte[] bs = ByteUtils.intToBytes(
                    ecgDeviceInfo.getPei(), 2);
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }

        // if (ecgDeviceInfo.getProbeChannelBiasMode() != null) {
        // buffer.put(GroupType.PROBE_CHANNEL_BIAS.getEncodes());
        // buffer.put(new String("BT").getBytes());
        // buffer.put((byte) 1);
        // buffer.put((byte) ecgDeviceInfo.getProbeChannelBiasMode().getCode());
        // }
        //
        // if (ecgDeviceInfo.getProbeElectrodeImpedanceModes() != null) {
        // buffer.put(GroupType.PROBE_ELECTRODE_IMPEDANCE.getEncodes());
        // buffer.put(new String("US").getBytes());
        // byte[] bs = ByteUtils.intToBytes(
        // ecgDeviceInfo.getProbeElectrodeImpedanceModesShort(), 2);
        // buffer.put((byte) bs.length);
        // buffer.put(bs);
        // }

        if (ecgDeviceInfo.getWearMode() != null) {
            buffer.put(GroupType.WEAR_MODE.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put((byte) ecgDeviceInfo.getWearMode().getCode());
        }

        if (ecgData.getEcgHeart() != null) {
            buffer.put(GroupType.ECG_HEART.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put(ecgData.getEcgHeart().byteValue());
        }

        if (ecgData.getAsd() != null) {
            buffer.put(GroupType.ACCELERATION_SENSOR_DATA.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put(ecgData.getAsd().byteValue());
        }

        if (ecgData.getBreath() != null) {
            buffer.put(GroupType.BREATH.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put(ecgData.getBreath().byteValue());
        }

        if (ecgData.getSuccessionData() != null) {
            buffer.put(GroupType.SUCCESSION_DATA.getEncodes());
            buffer.put(new String("OW").getBytes());
            byte[] bs = ecgData.getSuccessionData();
            buffer.put(ByteUtils.intToBytes(bs.length, 2));
            buffer.put(bs);
        }

        buffer.flip();
        byte[] bs = new byte[buffer.remaining()];
        buffer.get(bs);
        return bs;
    }
}
