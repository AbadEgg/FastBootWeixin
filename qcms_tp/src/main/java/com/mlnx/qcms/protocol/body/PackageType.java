package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum PackageType {
//    PACKAGE_MODULE_BEGIN(0x00,""),
    PACKAGE_ECG(0x00,""),
    PACKAGE_SPO2(0x01,""),
    PACKAGE_TEMP(0x02,""),
    PACKAGE_NIBP(0x03,""),
    PACKAGE_RESP(0x04,""),
    PACKAGE_CO2(0x05,""),
    PACKAGE_AG(0x06,""),
    PACKAGE_CO(0x07,""),
    PACKAGE_IBP1(0x08,""),
    PACKAGE_IBP2(0x09,""),
    PACKAGE_IBP3(0x0a,""),
    PACKAGE_IBP4(0x0b,""),
    PACKAGE_BIS(0x0c,""),
    PACKAGE_MODULE_END(0x0d,""),

    PACKAGE_USERINFO (0x80,""),
    PACKAGE_MODULE_INFO(0x81,""),
    PACKAGE_CMD(0x82,""),
    PACKAGE_ALARM_SETTING(0x83,""),
    PACKAGE_TECHNOLOGY_ALARM(0x84,""),
    PACKAGE_PHYSIOLOGY_ALRAM(0x85,""),
    PACKAGE_USERINFOV1(0x86,""),

    PACKAGE_CONNECT_INFO(0xfe,""),	//网络信息包，网络输出中，永远不会传输该包，属于解包时，用户添加
    PACKAGE_TYPE_MAX(0xff,"");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    PackageType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PackageType decode(int packageCode) {

        int code = packageCode & BITMASK;
        for (PackageType packageType : PackageType.values()) {
            if (packageType.code == code) {
                QcmsLogUtils.mpDecode("receive packageType:" + packageType);
                return packageType;
            }
        }
        return null;
    }
}
