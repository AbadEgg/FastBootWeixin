package com.mlnx.mptp.mptp.body;

public enum Command {

    START_BP_DETECT(0x01, "开始检测"), STOP_BP_DETECT(0x02, "停止检测"), QUERY_DEVINFO(
            0x03, "查询设备信息"), BP_HISTORY_RESULT(0x04, "历史数据查询"), UNEMER_CALL(
            0x05, "取消紧急呼叫"), CLEAR_FLASH(0x06, "清空FLASH"), CONFIG(0x07, "配置指令"),
    STAT_ECG(8, "开始心电检测"), STOP_ECG(9, "停止心电检测"), START_BO_DETECT(10, "开始血氧检测"),
    STOP_BO_DETECT(11, "停止血氧检测"), START_TP_DETECT(12, "开始体温检测"), STOP_TP_DETECT(13, "停止体温检测");

    private int code;
    private String description;

    private Command(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Command decode(int code) {
        Command[] commands = Command.values();
        for (int i = 0; i < commands.length; i++) {
            if (code == commands[i].code)
                return commands[i];
        }
        return null;
    }
}
