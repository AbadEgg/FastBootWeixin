package com.mlnx.mptp.model;

import com.mlnx.device.ecg.ECGChannelType;
import com.mlnx.device.ecg.ECGDeviceRunMode;
import com.mlnx.mptp.mptp.body.ecg.WearMode;

public class ECGDeviceInfo {

	private ECGDeviceRunMode ecgDeviceRunMode;

	private ECGChannelType ecgChannelType;

	private Integer packetInterval;

	private Integer batteryLevel;	// 电量

	private Integer signalStrength;	// 信号强度

	private Integer sdRemain;

	private Integer sdCapacity;

	private Integer amplification;	// 放大倍数

	private Integer sampling;

	private Integer probeChannelBias;	// 探头偏压

	private Integer pei;	// 导联情况

	private WearMode wearMode;

	public ECGDeviceInfo() {
	}

	public void updateECGDeviceInfo(ECGDeviceInfo info) {
		if (info.getEcgDeviceRunMode() != null) {
			setEcgDeviceRunMode(info.getEcgDeviceRunMode());
		}
		if (info.getEcgChannelType() != null) {
			setEcgChannelType(info.getEcgChannelType());
		}
		if (info.getBatteryLevel() != null) {
			setBatteryLevel(info.getBatteryLevel());
		}
		if (info.getSignalStrength() != null) {
			setSignalStrength(info.getSignalStrength());
		}
		if (info.getSdCapacity() != null) {
			setSdCapacity(info.getSdCapacity());
		}
		if (info.getSdRemain() != null) {
			setSdRemain(info.getSdRemain());
		}
		if (info.getSampling() != null) {
			setSampling(info.getSampling());
		}
		if (info.getAmplification() != null) {
			setAmplification(info.getAmplification());
		}
		if (info.getWearMode() != null) {
			setWearMode(info.getWearMode());
		}
		if (info.getProbeChannelBias() != null) {
			setProbeChannelBias(info.getProbeChannelBias());
		}
		if (info.getPei() != null) {
			setPei(info.getPei());
		}
	}

	public ECGDeviceRunMode getEcgDeviceRunMode() {
		return ecgDeviceRunMode;
	}

	public ECGDeviceInfo setEcgDeviceRunMode(ECGDeviceRunMode ecgDeviceRunMode) {
		this.ecgDeviceRunMode = ecgDeviceRunMode;
		return this;
	}

	public ECGChannelType getEcgChannelType() {
		return ecgChannelType;
	}

	public void setEcgChannelType(ECGChannelType ecgChannelType) {
		this.ecgChannelType = ecgChannelType;
	}

	public Integer getPacketInterval() {
		return packetInterval;
	}

	public void setPacketInterval(Integer packetInterval) {
		this.packetInterval = packetInterval;
	}

	public Integer getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(Integer batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public Integer getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(Integer signalStrength) {
		this.signalStrength = signalStrength;
	}

	public Integer getSdRemain() {
		return sdRemain;
	}

	public void setSdRemain(Integer sdRemain) {
		this.sdRemain = sdRemain;
	}

	public Integer getSdCapacity() {
		return sdCapacity;
	}

	public void setSdCapacity(Integer sdCapacity) {
		this.sdCapacity = sdCapacity;
	}

	public Integer getAmplification() {
		return amplification;
	}

	public void setAmplification(Integer amplification) {
		this.amplification = amplification;
	}

	public Integer getSampling() {
		return sampling;
	}

	public void setSampling(Integer sampling) {
		this.sampling = sampling;
	}

	// public ProbeChannelBiasMode getProbeChannelBiasMode() {
	// return probeChannelBiasMode;
	// }
	//
	// public void setProbeChannelBiasMode(
	// ProbeChannelBiasMode probeChannelBiasMode) {
	// this.probeChannelBiasMode = probeChannelBiasMode;
	// }
	//
	// public List<ProbeElectrodeImpedanceMode>
	// getProbeElectrodeImpedanceModes() {
	// return probeElectrodeImpedanceModes;
	// }
	//
	// public short getProbeElectrodeImpedanceModesShort() {
	// short s = 0;
	// for (ProbeElectrodeImpedanceMode p : probeElectrodeImpedanceModes) {
	// s |= p.getCode();
	// s <<= 1;
	// }
	// return s;
	// }
	//
	// public void setProbeElectrodeImpedanceModes(
	// List<ProbeElectrodeImpedanceMode> probeElectrodeImpedanceModes) {
	// this.probeElectrodeImpedanceModes = probeElectrodeImpedanceModes;
	// }

	public Integer getProbeChannelBias() {
		return probeChannelBias;
	}

	public void setProbeChannelBias(Integer probeChannelBias) {
		this.probeChannelBias = probeChannelBias;
	}

	public Integer getPei() {
		return pei;
	}

	public void setPei(Integer pei) {
		this.pei = pei;
	}

	public WearMode getWearMode() {
		return wearMode;
	}

	@Override
	public String toString() {
		return "ECGDeviceInfo{" +
				"ecgDeviceRunMode=" + ecgDeviceRunMode +
				", ecgChannelType=" + ecgChannelType +
				", packetInterval=" + packetInterval +
				", batteryLevel=" + batteryLevel +
				", signalStrength=" + signalStrength +
				", sdRemain=" + sdRemain +
				", sdCapacity=" + sdCapacity +
				", amplification=" + amplification +
				", sampling=" + sampling +
				", probeChannelBias=" + probeChannelBias +
				", pei=" + pei +
				", wearMode=" + wearMode +
				'}';
	}

	public void setWearMode(WearMode wearMode) {
		this.wearMode = wearMode;
	}


}
