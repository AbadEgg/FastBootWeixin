package com.mlnx.push_tp.tp.head;


public class Header {

	private byte version;
	private int length;
	private PacketType packetType;
	private QoS qoS = QoS.MOST_ONE;
	private DeviceType deviceType;

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public int getLength() {
		return length;
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
