package com.mlnx.mptp.utils;

public class ByteUtils {

	public static byte[] intToBytes(int value) {
		return intToBytes(value, 4);
	}

	public static byte[] intToBytes(int value, int len) {
		byte[] src = new byte[len];

		for (int i = 0; i < len && i < 4; ++i) {
			src[i] = (byte) ((value >> (i * 8)) & 0xFF);
		}
		return src;
	}

	public static byte[] longToBytes(long value) {
		return longToBytes(value, 8);
	}

	public static byte[] longToBytes(long value, int len) {
		byte[] src = new byte[len];

		for (int i = 0; i < len && i < 8; ++i) {
			src[i] = (byte) ((value >> (i * 8)) & 0xFF);
		}
		return src;
	}

	public static int bytesToInt(byte[] src, int len) {
		int value = 0;

		for (int i = 0; i < len && i < 4; ++i) {
			value |= (src[i] & 0xFF) << (i * 8);
		}
		return value;
	}

	public static int bytesToInt(byte[] src) {
		return bytesToInt(src, 4);
	}

	public static long bytesToLong(byte[] src) {
		return ((((long) src[7] & 0xff) << 56) | (((long) src[6] & 0xff) << 48)
				| (((long) src[5] & 0xff) << 40)
				| (((long) src[4] & 0xff) << 32)
				| (((long) src[3] & 0xff) << 24)
				| (((long) src[2] & 0xff) << 16)
				| (((long) src[1] & 0xff) << 8) | (((long) src[0] & 0xff) << 0));
	}

//	public static void main(String[] args) {
//		long sessionID = System.nanoTime();
//		System.out.println(sessionID);
//
//		sessionID = bytesToLong(longToBytes(sessionID));
//		System.out.println("");
//		System.out.println(sessionID);
//	}

}
