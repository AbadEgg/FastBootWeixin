package com.mlnx.mptp.mptp.head;

import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.PacketType;
import com.mlnx.mptp.mptp.Codec;
import com.mlnx.mptp.mptp.VersionManager;
import com.mlnx.mptp.utils.ByteUtils;

import java.nio.ByteBuffer;

public class Header implements Codec {

	public static final int LengthByteSize = 4; // 数据包长度字段字节数
	public static final int HEAD_CONTANT_LEN = 1 + 1 + 2; // 包头内容字节长度
	public static final int MaxLength = 10000;
	public static final byte[] Heads = { 0x55, (byte) 0xF5, 0x58 }; // 数据包包头字节

	private static final ByteBuffer buffer = ByteBuffer.allocate(30);

	private byte version;
	private int length;
	private PacketType packetType;
	private QoS qoS = QoS.MOST_ONE;
	private DeviceType deviceType;
	private int checkSum;

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public int getLength() {
		return length;
	}

	public Header setBodyLength(int bodyLength) {
		length = bodyLength + HEAD_CONTANT_LEN;
		return this;
	}

	public PacketType getPacketType() {
		return packetType;
	}

	public Header setPacketType(PacketType packetType) {
		this.packetType = packetType;
		return this;
	}

	public QoS getQoS() {
		return qoS;
	}

	public Header setQoS(QoS qoS) {
		this.qoS = qoS;
		return this;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public Header setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
		return this;
	}

	public int getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(int checkSum) {
		this.checkSum = checkSum;
	}

	@Override
	public void decode(ByteBuffer buf) {

		length = buf.capacity();
		// 包类型
		byte code = buf.get();
		packetType = PacketType.decode(code);
		qoS = QoS.decode(code);
		// 设备类型
		code = buf.get();
		deviceType = DeviceType.decode(code);
		// check sum
		byte[] bs = new byte[2];
		buf.get(bs);
		checkSum = 0;
		checkSum |= bs[1] & 0x000000ff;
		checkSum <<= 8;
		checkSum |= bs[0] & 0x000000ff;
	}

	@Override
	public byte[] encode() {

		buffer.clear();
		buffer.put(Heads);
		buffer.put(VersionManager.VERSION_1_0);
		buffer.put(ByteUtils.intToBytes(length, LengthByteSize));

		int packetCode = 0;
		packetCode = PacketType.encode(packetCode, packetType);
		packetCode = QoS.encode(packetCode, qoS);
		buffer.put((byte) packetCode);

		buffer.put((byte) deviceType.getCode());
		buffer.put(ByteUtils.intToBytes(checkSum, 2));

		buffer.flip();
		byte[] bs = new byte[buffer.remaining()];
		buffer.get(bs);

		return bs;
	}

	@Override
	public void init() {

	}
}
