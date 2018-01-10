package com.mlnx.mptp.mptp;

public class VersionManager {

	private static byte[] SUPPORT_VERSIONS = { 0x10 };

	public static byte VERSION_1_0 = 0x10;

	public static boolean isSupport(byte b) {

		for (int i = 0; i < SUPPORT_VERSIONS.length; i++) {
			if (b == SUPPORT_VERSIONS[i])
				return true;
		}
		return false;
	}
}
