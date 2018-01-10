package com.mlnx.device.ecg;

public enum ECGDeviceRunMode {
    HIGH_PRECISION(0, 300, "高精度模式"), PEAK_DETECTION(1, 300, "尖峰检测模式"), ECG_MACHINE(
            2, 300, "心电图机"), GENERAL_CUSTODY(3, 150, "普通监护模式"), SPORTS_CUSTODY(
            4, 75, "运动监护模式"), OPERATION(5, 75, "手术监护模式");

    private int code;
    private int sampling;
    private String description;

    private ECGDeviceRunMode(int code, int sampling, String description) {
        this.code = code;
        this.sampling = sampling;
        this.description = description;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public int getSampling() {
        return sampling;
    }

    public void setSampling(int sampling) {
        this.sampling = sampling;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ECGDeviceRunMode decode(int code) {
        ECGDeviceRunMode[] ecgDeviceRunModes = ECGDeviceRunMode.values();
        for (int i = 0; i < ecgDeviceRunModes.length; i++) {
            if (code == ecgDeviceRunModes[i].code)
                return ecgDeviceRunModes[i];
        }
        return null;
    }

    public static ECGDeviceRunMode decode(String name) {
        ECGDeviceRunMode[] ecgDeviceRunModes = ECGDeviceRunMode.values();
        for (int i = 0; i < ecgDeviceRunModes.length; i++) {
            if (name.equals(ecgDeviceRunModes[i].toString())) {
                return ecgDeviceRunModes[i];
            }
        }
        return null;
    }
}
