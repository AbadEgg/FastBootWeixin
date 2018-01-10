
package com.mlnx.device_server.domain.result;

public enum ExceptionMsg {
	SUCCESS("0000", "操作成功"),
	FAILED("999999","操作失败"),
    ParamError("000001", "参数错误！"),

    DeviceNotRegister("000100", "该设备编号未注册！"),
    BindDeviceError("000101", "设备绑定失败"),
    BindEcgDeviceError("000102", "心电设备在线，绑定失败"),

    ConfigDeviceError("000103", "设备配置失败"),
    ConfigDeviceNotOnline("000104", "配置设备不在线"),
    ;
   private ExceptionMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private String code;
    private String msg;
    
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

