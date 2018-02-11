package com.mlnx.mptp.push.head;


import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.PacketType;
import com.mlnx.mptp.mptp.head.QoS;

public class Header {

	private byte version;
	private PacketType packetType;
	private QoS qoS = QoS.MOST_ONE;
	private DeviceType deviceType;

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public PacketType getPacketType() {
		return packetType;
	}

	public void setPacketType(PacketType packetType) {
		this.packetType = packetType;
	}

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
