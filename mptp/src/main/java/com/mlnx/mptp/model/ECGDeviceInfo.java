package com.mlnx.mptp.model;

import com.mlnx.device.ecg.ECGChannelType;
import com.mlnx.device.ecg.ECGDeviceRunMode;
import com.mlnx.mptp.mptp.body.ecg.WearMode;

public class ECGDeviceInfo {

	private ECGDeviceRunMode ecgDeviceRunMode;

	private ECGChannelType ecgChannelType;

	private Integer packetInterval;

	private Integer batteryLevel;

	private Integer signalStrength;

	private Integer sdRemain;

	private Integer sdCapacity;

	private Integer magnification;

	private Integer sampling;

	private Integer probeChannelBias;

	private Integer pei;	// 导联情况

	// private ProbeChannelBiasMode probeChannelBiasMode;
	//
	// private List<ProbeElectrodeImpedanceMode> probeElectrodeImpedanceModes;

	private WearMode wearMode;

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

	public Integer getMagnification() {
		return magnification;
	}

	public void setMagnification(Integer magnification) {
		this.magnification = magnification;
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

	public void setWearMode(WearMode wearMode) {
		this.wearMode = wearMode;
	}

	@Override
	public String toString() {
		return "ECGDeviceInfo [ecgDeviceRunMode=" + ecgDeviceRunMode
				+ ", ecgChannelType=" + ecgChannelType + ", packetInterval="
				+ packetInterval + ", batteryLevel=" + batteryLevel
				+ ", signalStrength=" + signalStrength + ", sdRemain="
				+ sdRemain + ", sdCapacity=" + sdCapacity + ", magnification="
				+ magnification + ", sampling=" + sampling
				+ ", probeChannelBias=" + probeChannelBias
				+ ", pei=" + pei
				+ ", wearMode=" + wearMode + "]";
	}

}
