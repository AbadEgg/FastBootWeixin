package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum TechnologyAlarmEnum {

    TALARM_DEFINE_BEGIN(0x00,""),
    //SYSTEM
    TALARM_SYS_BAT_LOW(0x00,""),
    TALARM_SYS_BAT_RUNOUT(0x01,""),
    TALARM_SYS_BAT_DISCONNECT(0x02,""),
    TALARM_SYS_AC_DISCONNECT(0x03,""),
    //ECG
    TALARM_ECG_ID_MIN(0x04,""),
    TALARM_ECG_COMM_STOP(0x04,""),
    TALARM_ECG_COMM_ERR(0x05,""),
    TALARM_ECG_CFG_ERR(0x06,""),
    TALARM_ECG_SELFCHECK_ERR(0x07,""),
    TALARM_ECG_LEAD_OFF(0x08,""),
    TALARM_ECG_LEAD_OFF_V(0x09,""),
    TALARM_ECG_LEAD_OFF_LL(0x0a,""),
    TALARM_ECG_LEAD_OFF_LA(0x0a,""),
    TALARM_ECG_LEAD_OFF_RA(0x0c,""),
    TALARM_ECG_LEAD_OFF_RL(0x0d,""),
    TALARM_ECG_LEAD_OFF_V1(0x0e,""),
    TALARM_ECG_LEAD_OFF_V2(0x0f,""),
    TALARM_ECG_LEAD_OFF_V3(0x10,""),
    TALARM_ECG_LEAD_OFF_V4(0x11,""),
    TALARM_ECG_LEAD_OFF_V5(0x12,""),
    TALARM_ECG_LEAD_OFF_V6(0x13,""),
    TALARM_ECG_LEARNING(0x14,""),
    TALARM_ECG_NIOSE_SIGNAL(0x15,""),
    TALARM_ECG_OVERLOAD(0x16,""),
    TALARM_ECG_ID_MAX(0x16,""),
    //SPO2
    TALARM_SPO2_ID_MIN(0x17,""),
    TALARM_SPO2_COMM_STOP(0x17,""),
    TALARM_SPO2_COMM_ERR(0x18,""),
    TALARM_SPO2_NO_SENSOR(0x19,""),
    TALARM_SPO2_SENSOR_OFF(0x1a,""),
    TALARM_SPO2_SEARCH_TIMEOUT(0x1b,""),
    TALARM_SPO2_SIGNAL_WEAK(0x1c,""),
    TALARM_SPO2_SEARCH_PULSE(0x1d,""),
    TALARM_SPO2_SIGNAL_UNSTABLE(0x1e,""),
    TALARM_SPO2_SPO2_FAILURE(0x1f,""),
    TALARM_SPO2_SENSOR_ERROR(0x20,""),
    TALARM_SPO2_LOW_PERFUSION(0x21,""),
    TALARM_SPO2_LOW_SIGNAL_IQ(0x22,""),
    TALARM_SPO2_DEFECTIVE_SENSOR(0x23,""),
    TALARM_SPO2_INTERFERENCE_DETECTED(0x24,""),
    TALARM_SPO2_TOO_MUCH_LIGHT(0x25,""),
    TALARM_SPO2_UNRECOGNIZED_SENSOR(0x26,""),
    TALARM_SPO2_NO_CABLE(0x27,""),
    TALARM_SPO2_SET_PROCESSING_ACTIVE(0x28,""),
    TALARM_SPO2_DIAGNOSTIC_FAILURE(0x29,""),
    TALARM_SPO2_NO_ADHESIVE_SENSOR(0x2a,""),
    TALARM_SPO2_DEMO_MODE(0x2b,""),
    TALARM_SPO2_ID_MAX(0x2b,""),
    //NIBP
    TALARM_NIBP_ID_MIN(0x2c,""),
    TALARM_NIBP_COMM_STOP(0x2c,""),
    TALARM_NIBP_COMM_ERR(0x2d,""),
    TALARM_NIBP_CFG_ERR(0x2e,""),
    TALARM_NIBP_ID_MAX(0x2e,""),
    //TEMP
    TALARM_TEMP_ID_MIN(0x2f,""),
    TALARM_T1_SENSOR_OFF(0x2f,""),
    TALARM_T2_SENSOR_OFF(0x30,""),
    TALARM_TEMPGUN_NOT_READY(0x31,""),
    TALARM_TEMPGUN_BATTERY_EMPTY(0x32,""),
    TALARM_TEMPGUN_SYSTEM_ERR(0x33,""),
    TALARM_TEMP_ID_MAX(0x33,""),
    //RESP
    TALARM_RESP_ID_MIN(0x34,""),
    TALARM_RESP_INTERFERENCE(0x34,""),
    TALARM_RESP_ID_MAX(0x34,""),
    //BIS
    TALARM_BIS_ID_MIN(0x35,""),
    TALARM_BIS_COMM_STOP(0x35,""),
    TALARM_BIS_CFG_ERROR(0x36,""),
    TALARM_BIS_SENSOR_INVALID(0x37,""),
    TALARM_BIS_HIGH_IMPED(0x38,""),
    TALARM_BIS_SENSOR_OFF(0x39,""),
    TALARM_BIS_DSC_ERROR(0x3a,""),
    TALARM_BIS_DSC_MALF(0x3b,""),
    TALARM_BIS_NO_CABLE(0x3c,""),
    TALARM_BIS_NO_SENSOR(0x3d,""),
    TALARM_BIS_SENSOR_TYPE_ERR(0x3e,""),
    TALARM_BIS_TOO_MANY_USES(0x3f,""),
    TALARM_BIS_SQI_LOW_50(0x40,""),
    TALARM_BIS_SQI_LOW_15(0x41,""),
    TALARM_BIS_SENSOR_EXPIRED(0x42,""),
    TALARM_BIS_SENSOR_FAULT(0x43,""),
    TALARM_BIS_CONNECT_ELECTRODE(0x44,""),
    TALARM_BIS_SELFCHECK_ERROR(0x45,""),
    TALARM_BIS_RECONNECT_BIS(0x46,""),
    TALARM_BIS_ID_MAX(0x46,""),

    TALARM_NMT_ID_MIN(0x47,""),
    TALARM_NMT_COMM_STOP(0x47,""),
    TALARM_NMT_TEMP_OVERFLOW(0x48,""),
    TALARM_NMT_SURFACE_CABLE_OFF(0x49,""),
    TALARM_NMT_NEEDLE_CABLE_OFF(0x4a,""),
    TALARM_NMT_ACCELERATION_OFF(0x4b,""),
    TALARM_NMT_TEMP_SENSOR_OFF(0x4c,""),
    TALARM_NMT_INTERNAL_ERROR(0x4d,""),
    TALARM_NMT_CALIBRATION_ERROR(0x4e,""),
    TALARM_NMT_SKIN_RESIST(0x4f,""),
    TALARM_NMT_ELECTRODE_ERROR(0x50,""),
    TALARM_NMT_PTC_ERROR(0x51,""),
    TALARM_NMT_BAT_LOW(0x52,""),
    TALARM_NMT_ACCSIG_TOO_HIGH(0x53,""),
    TALARM_NMT_BAT_EMPTY(0x54,""),
    TALARM_NMT_ID_MAX(0x54,""),
    //CO
    TALARM_CO_ID_MIN(0x55,""),
    TALARM_CO_COMM_STOP(0x55,""),
    TALARM_CO_COMM_ERR(0x56,""),
    TALARM_CO_MEASURE_TIMEOUT(0x57,""),
    TALARM_CO_BASE_LINE_OFFSET(0x58,""),
    TALARM_CO_BIG_NOISE(0x59,""),
    TALARM_CO_SMALL_NOISE(0x5a,""),
    TALARM_CO_CALIBRATING(0x5b,""),
    TALARM_CO_CALCU_BASE_LINE(0x5c,""),
    TALARM_CO_IT_SENSOR_OFF(0x5d,""),
    TALARM_CO_BT_SENSOR_OFF(0x5e,""),
    TALARM_CO_ID_MAX(0x5e,""),
    //CO2
    TALARM_CO2_ID_MIN(0x5f,""),
    TALARM_CO2_COMM_STOP(0x5f,""),
    TALARM_CO2_COMM_ERR(0x60,""),
    TALARM_CO2_SENSOR_OFF(0x61,""),
    TALARM_CO2_O2_SENSOR_REPL(0x62,""),
    TALARM_CO2_CHECK_ADAPT(0x63,""),
    TALARM_CO2_CHECK_LINE(0x64,""),
    TALARM_CO2_UNSPEC_ACC(0x65,""),
    TALARM_CO2_SENSOR_ERR(0x66,""),
    TALARM_CO2_O2_CALIB(0x67,""),
    TALARM_CO2_O2_ROOM_CALIB(0x68,""),
    TALARM_CO2_SOFT_ERR(0x69,""),
    TALARM_CO2_HD_ERR(0x6a,""),
    TALARM_CO2_SPEED_OUT(0x6b,""),
    TALARM_CO2_FAC_CAL_MISS(0x6c,""),
    TALARM_CO2_O2_SENSOR_ERR(0x6d,""),
    TALARM_CO2_REPLACE_ADPT(0x6e,""),
    TALARM_CO2_ADPT_BLOCK(0x6f,""),
    TALARM_CO2_NO_LINE(0x70,""),
    TALARM_CO2_NO_ADPT(0x71,""),
    TALARM_CO2_O2_PORT_ERR(0x72,""),
    TALARM_CO2_CO2_OUT_RANGE(0x73,""),
    TALARM_CO2_N2O_OUT_RANGE(0x74,""),
    TALARM_CO2_O2_OUT_RANGE(0x075,""),
    TALARM_CO2_TEMP_OUT_RANGE(0x76,""),
    TALARM_CO2_PRESS_OUT_RANGE(0x77,""),
    TALARM_CO2_ZERO_LEV_ERR(0x78,""),
    TALARM_CO2_AG_UNRELIABLE(0x79,""),
    TALARM_CO2_ZEROING(0x7a,""),
    TALARM_CO2_SLEEPING(0x7b,""),
    TALARM_CO2_STARTING(0x7c,""),
    TALARM_CO2_SPAN_CALING(0x7d,""),
    TALARM_CO2_SPAN_CAL_ERR(0x7e,""),
    TALARM_CO2_NEED_ZERO(0x7f,""),
    TALARM_CO2_ID_MAX(0x7f,""),
    //AG
    TALARM_AG_ID_MIN(0x80,""),
    TALARM_AG_COMM_STOP(0x80,""),
    TALARM_AG_COMM_ERR(0x81,""),
    TALARM_AG_SENSOR_OFF(0x82,""),
    TALARM_AG_O2_SENSOR_REPL(0x83,""),
    TALARM_AG_CHECK_ADAPT(0x84,""),
    TALARM_AG_CHECK_LINE(0x85,""),
    TALARM_AG_UNSPEC_ACC(0x86,""),
    TALARM_AG_SENSOR_ERR(0x87,""),
    TALARM_AG_O2_CALIB(0x88,""),
    TALARM_AG_O2_ROOM_CALIB(0x89,""),
    TALARM_AG_SOFT_ERR(0x8a,""),
    TALARM_AG_HD_ERR(0x8b,""),
    TALARM_AG_SPEED_OUT(0x8c,""),
    TALARM_AG_FAC_CAL_MISS(0x8d,""),
    TALARM_AG_O2_SENSOR_ERR(0x8e,""),
    TALARM_AG_REPLACE_ADPT(0x8f,""),
    TALARM_AG_ADPT_BLOCK(0x90,""),
    TALARM_AG_NO_LINE(0x91,""),
    TALARM_AG_NO_ADPT(0x92,""),
    TALARM_AG_O2_PORT_ERR(0x93,""),
    TALARM_AG_CO2_OUT_RANGE(0x94,""),
    TALARM_AG_N2O_OUT_RANGE(0x95,""),
    TALARM_AG_AX_OUT_RANGE(0x96,""),
    TALARM_AG_O2_OUT_RANGE(0x97,""),
    TALARM_AG_TEMP_OUT_RANGE(0x98,""),
    TALARM_AG_PRESS_OUT_RANGE(0x99,""),
    TALARM_AG_MIXED_AGENTS(0x9a,""),
    TALARM_AG_ZERO_LEV_ERR(0x9b,""),
    TALARM_AG_AG_UNRELIABLE(0x9c,""),
    TALARM_AG_ZEROING(0x9d,""),
    TALARM_AG_SLEEPING(0x9e,""),
    TALARM_AG_STARTING(0x9f,""),
    TALARM_AG_SPAN_CALING(0xa0,""),
    TALARM_AG_SPAN_CAL_ERR(0xa1,""),
    TALARM_AG_ID_MAX(0xa1,""),
    //IBP
    TALARM_IBP_ID_MIN(0xa2,""),
    TALARM_IBP_IBP1_ID_MIN(0xa2,""),
    TALARM_IBP1_COMM_STOP(0xa2,""),
    TALARM_IBP1_COMM_ERR(0xa3,""),
    TALARM_IBP1_CH1_SENSOR_OFF(0xa4,""),
    TALARM_IBP1_CH2_SENSOR_OFF(0xa5,""),
    TALARM_IBP_IBP1_ID_MAX(0xa5,""),

    TALARM_IBP_IBP2_ID_MIN(0xa6,""),
    TALARM_IBP2_COMM_STOP(0xa6,""),
    TALARM_IBP2_COMM_ERR(0xa7,""),
    TALARM_IBP2_CH1_SENSOR_OFF(0xa8,""),
    TALARM_IBP2_CH2_SENSOR_OFF(0xa9,""),
    TALARM_IBP_IBP2_ID_MAX(0xa9,""),
    TALARM_IBP_ID_MAX(0xa9,""),

    TALARM_DEFINE_END(0xaa,"");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    TechnologyAlarmEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TechnologyAlarmEnum decode(int technologyAlarmEnumCode) {

        int code = technologyAlarmEnumCode & BITMASK;
        for (TechnologyAlarmEnum technologyAlarmEnum : TechnologyAlarmEnum.values()) {
            if (technologyAlarmEnum.code == code) {
                QcmsLogUtils.mpDecode("receive technologyAlarmEnum:" + technologyAlarmEnum);
                return technologyAlarmEnum;
            }
        }
        return null;
    }
}