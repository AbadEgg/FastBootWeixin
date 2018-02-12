package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum AgModuleItem {

    AG_ITEM_CO2(0x00,""),
    AG_ITEM_N2O(0x01,""),
    AG_ITEM_O2(0x02,""),
    AG_ITEM_AA(0x03,""),
    AG_ITEM_MAX(0x04,"");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    AgModuleItem(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AgModuleItem decode(int agModuleItemCode) {

        int code = agModuleItemCode & BITMASK;
        for (AgModuleItem agModuleItem : AgModuleItem.values()) {
            if (agModuleItem.code == code) {
                QcmsLogUtils.mpDecode("receive agModuleItem:" + agModuleItem);
                return agModuleItem;
            }
        }
        return null;
    }
}
