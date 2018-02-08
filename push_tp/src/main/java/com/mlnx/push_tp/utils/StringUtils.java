package com.mlnx.push_tp.utils;

public class StringUtils {
	public static String combineString(String message, byte[] bs) {
		for (int i = 0; i < bs.length; i++) {
			message += String.format("0x%x ", bs[i]);
		}
		return message;
	}
}
