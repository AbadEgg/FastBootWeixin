package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum PatientType {
    PATIENT_TYPE_ADU(0x00,"成人"),
    PATIENT_TYPE_PED(0x01,"小儿"),
    PATIENT_TYPE_NEO(0x02,"新生儿");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    PatientType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PatientType decode(int patientTypeCode) {

        int code = patientTypeCode & BITMASK;
        for (PatientType patientType : PatientType.values()) {
            if (patientType.code == code) {
                QcmsLogUtils.mpDecode("receive patientType:" + patientType);
                return patientType;
            }
        }
        return null;
    }
}
