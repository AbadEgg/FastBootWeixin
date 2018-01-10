package com.mlnx.device.ecg;

public enum ECGChannelType {

	CHAN_8(0), CHAN_3(1), CHAN_1(2);

	private int code;

	private ECGChannelType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static ECGChannelType decode(int code)  {
		ECGChannelType[] ecgChannelTypes = ECGChannelType.values();
		for (int i = 0; i < ecgChannelTypes.length; i++) {
			if (code == ecgChannelTypes[i].code)
				return ecgChannelTypes[i];
		}

		return null;
	}

	public static ECGChannelType decode(String name)  {
		ECGChannelType[] ecgChannelTypes = ECGChannelType.values();
		for (int i = 0; i < ecgChannelTypes.length; i++) {
			if (ecgChannelTypes[i].toString().equals(name))
				return ecgChannelTypes[i];
		}

		return null;
	}

}
