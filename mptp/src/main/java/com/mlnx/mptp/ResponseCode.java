package com.mlnx.mptp;

public enum ResponseCode {

    SUCESS(0, "成功"), LACK_DEVICEID(1, "缺少设备ID"), ILLEGAL_DEVICE_TYPE(2,
            "非法的设备类型"), ILLEGAL_TOPICE(3, "非法的订阅主题"), LOST_MESSAGE_ID(4,
            "缺少消息ID"), ILLEGAL_CMD_CODE(5, "非法的指令码"), ILLEGAL_STATE_CODE(6,
            "非法的设备状态码"), CLEAR_FLASH_ERR(7, "清空FLASH失败"), UPDATE_DEV_ERR(8,
            "更新设备信息失败"), UN_BIND_PATIENT(9, "设备未绑定患者"), NOT_REGISTER(10, "设备未注册"),
    VERIFY_USR_ERR(11, "用户验证失败"), NOT_SUPPORT_DEVICE_TYPE(12, "不支持的设备类型"), NOT_EXIST_DEVICE(13, "设备不存在");

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
