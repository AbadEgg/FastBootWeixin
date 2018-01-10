package com.mlnx.mptp.mptp.body;

public enum DeviceState {

	BP_DETECTING(1, "血压检测状态"), BP_DETECT_FINISH(2, "血压检测完成"), 
	DEVICE_ONLINE(3, "设备上线"), DEVICE_OFFLINE(4, "设备下线"), 
	EXIGENCY_STATE(5, "设备紧急呼叫状态"), NOMAL_STATE(6, "设备从紧急呼叫恢复正常"),
	WAIT_CONFIG(7, "设备等待配置");

	private int code;
	private String description;

	private DeviceState(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static DeviceState decode(int code) {

		DeviceState[] deviceStates = DeviceState.values();
		for (int i = 0; i < deviceStates.length; i++) {
			if (code == deviceStates[i].code)
				return deviceStates[i];
		}
		return null;
	}

}
