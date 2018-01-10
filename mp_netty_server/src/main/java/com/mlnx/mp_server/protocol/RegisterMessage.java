package com.mlnx.mp_server.protocol;

public class RegisterMessage extends AbstractMessage {

	private String deviceId;
	private Integer keepAliveTimer;

	private String usrName;
	private String password;

	public Integer getKeepAliveTimer() {
		return keepAliveTimer;
	}

	public void setKeepAliveTimer(Integer keepAliveTimer) {
		this.keepAliveTimer = keepAliveTimer;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
