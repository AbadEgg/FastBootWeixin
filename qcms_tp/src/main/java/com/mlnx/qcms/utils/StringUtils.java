package com.mlnx.qcms.utils;

import java.io.UnsupportedEncodingException;

public class StringUtils {

	private static final String CHARSET = "UTF-8";

	public static String combineString(String message, byte[] bs) {
		for (int i = 0; i < bs.length; i++) {
			message += String.format("%02x ", bs[i]);
		}
		return message;
	}

	public static String bytesToString(byte[] bs) throws UnsupportedEncodingException {
		String s = new String(bs,CHARSET);
		return s;
	}
}
