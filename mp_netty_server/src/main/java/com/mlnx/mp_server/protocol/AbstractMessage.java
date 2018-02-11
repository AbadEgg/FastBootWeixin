package com.mlnx.mp_server.protocol;


import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.mptp.head.QoS;

public abstract class AbstractMessage {

	private QoS qoS;
	private DeviceType deviceType;

	public QoS getQoS() {
		return qoS;
	}

	public void setQoS(QoS qoS) {
		this.qoS = qoS;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

}
