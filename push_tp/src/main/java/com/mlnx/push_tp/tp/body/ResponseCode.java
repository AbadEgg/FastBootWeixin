package com.mlnx.push_tp.tp.body;

public enum ResponseCode {

    SUCESS(0, "成功"), ILLEGAL_DEVICE_TYPE(1, "非法的设备类型"), ILLEGAL_TOPICE(2, "非法的订阅主题"),
    LOST_MESSAGE_ID(3, "缺少消息ID"),;

    private int code;
    private String description;

    private ResponseCode(int code, String description) {
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

    public static ResponseCode decode(int code) {
        ResponseCode[] responseCodes = ResponseCode.values();
        for (int i = 0; i < responseCodes.length; i++) {
            if (code == responseCodes[i].code)
                return responseCodes[i];
        }
        return null;
    }

}
