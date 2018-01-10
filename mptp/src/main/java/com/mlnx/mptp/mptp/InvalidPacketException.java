package com.mlnx.mptp.mptp;

public class InvalidPacketException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPacketException() {
		super();
	}

	public InvalidPacketException(String message) {

		super(message);
	}

	public InvalidPacketException(String message, byte[] bs) {
		super(apliceString(message, bs));
	}

	private static String apliceString(String message, byte[] bs) {
		message += "，收到的数据包为";
		for (int i = 0; i < bs.length; i++) {
			message += String.format("0x%x ", bs[i]);
		}
		return message;
	}
}
