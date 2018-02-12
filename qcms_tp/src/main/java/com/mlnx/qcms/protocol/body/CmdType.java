package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum CmdType {

    CMD_BEGIN_COMMUNICATION(0x00,"开始通讯"),
    CMD_BEGIN_COMMUNICATION_OK(0x01,"同意开始通讯"),
    CMD_DISCONNECT(0x02,"断开连接"),
    CMD_START_NIBP(0x03,"启动nibp测量"),
    CMD_STOP_NIBP(0x04,"停止nibp测量"),
    CMD_GET_ALARM_SETTING(0x05,"获取报警设置,中央站发送给床边机"),
    CMD_CONNECT_HEART(0x06,"连接心跳包"),
    CMD_ACK_REQUEST(0x07,"向中央站发送信息收到回复请求"),
    CMD_ACK_ANSWER(0x08,"收到中央站的回复");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    CmdType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static CmdType decode(int cmdTypeCode) {

        int code = cmdTypeCode & BITMASK;
        for (CmdType cmdType : CmdType.values()) {
            if (cmdType.code == code) {
                QcmsLogUtils.mpDecode("receive cmdType:" + cmdType);
                return cmdType;
            }
        }
        return null;
    }
}
