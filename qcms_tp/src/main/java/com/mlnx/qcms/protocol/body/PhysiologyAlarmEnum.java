package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.QcmsLogUtils;

public enum PhysiologyAlarmEnum {

    PALARM_DEFINE_BEGIN(0x00,""),
    PALARM_ECG_ID_MIN(0x00,""),
    PALARM_ECG_HR_HIGH(0x00,""),
    PALARM_ECG_HR_LOW(0x01,""),
    PALARM_ECG_PVCS_HIGH(0x02,""),
    PALARM_ECG_PVCS_LOW(0x03,""),

    //心律失常
    PALARM_ARR_ID_MIN(0x04,""),
    PALARM_ARR_ASYSTOLE(0x04,""),
    PALARM_ARR_VF_VTA(0x05,""),
    PALARM_ARR_ROT(0x06,""),
    PALARM_ARR_RUN(0x07,""),
    PALARM_ARR_CPT(0x08,""),
    PALARM_ARR_VPB(0x09,""),
    PALARM_ARR_BGM(0x0a,""),
    PALARM_ARR_TGM(0x0b,""),
    PALARM_ARR_TAC(0x0c,""),
    PALARM_ARR_BRD(0x0d,""),
    PALARM_ARR_PNC(0x0e,""),
    PALARM_ARR_PNP(0x0f,""),
    PALARM_ARR_MIS(0x10,""),
    PALARM_ARR_SIGNAL_WEAK(0x11,""),
    PALARM_ARR_ID_MAX(0x11,""),

    //ST段
    PALARM_ST_ID_MIN(0x12,""),
    PALARM_ST_I_HIGH(0x12,""),
    PALARM_ST_I_LOW(0x13,""),
    PALARM_ST_II_HIGH(0x14,""),
    PALARM_ST_II_LOW(0x15,""),
    PALARM_ST_III_HIGH(0x16,""),
    PALARM_ST_III_LOW(0x17,""),
    PALARM_ST_AVR_HIGH(0x18,""),
    PALARM_ST_AVR_LOW(0x19,""),
    PALARM_ST_AVL_HIGH(0x1a,""),
    PALARM_ST_AVL_LOW(0x1b,""),
    PALARM_ST_AVF_HIGH(0x1c,""),
    PALARM_ST_AVF_LOW(0x1d,""),
    PALARM_ST_V1_HIGH(0x1e,""),
    PALARM_ST_V1_LOW(0x1f,""),
    PALARM_ST_V2_HIGH(0x20,""),
    PALARM_ST_V2_LOW(0x21,""),
    PALARM_ST_V3_HIGH(0x22,""),
    PALARM_ST_V3_LOW(0x23,""),
    PALARM_ST_V4_HIGH(0x24,""),
    PALARM_ST_V4_LOW(0x25,""),
    PALARM_ST_V5_HIGH(0x26,""),
    PALARM_ST_V5_LOW(0x27,""),
    PALARM_ST_V6_HIGH(0x28,""),
    PALARM_ST_V6_LOW(0x29,""),
    PALARM_ST_ID_MAX(0x29,""),
    PALARM_ECG_ID_MAX(0x29,""),


    //SPO2
    PALARM_SPO2_ID_MIN(0x2a,""),
    PALARM_SPO2_HIGH(0x2a,""),
    PALARM_SPO2_LOW(0x2b,""),
    PALARM_SPO2_PR_HIGH(0x2c,""),
    PALARM_SPO2_PR_LOW(0x2d,""),
    PALARM_SPO2_PI_HIGH(0x2e,""),
    PALARM_SPO2_PI_LOW(0x2f,""),
    PALARM_SPO2_NO_PLUSE(0x30,""),
    PALARM_SPO2_ID_MAX(0x30,""),

    //NIBP
    PALARM_NIBP_ID_MIN(0x31,""),
    PALARM_NIBP_SYS_HIGH(0x31,""),
    PALARM_NIBP_SYS_LOW(0x32,""),
    PALARM_NIBP_MEAN_HIGH(0x33,""),
    PALARM_NIBP_MEAN_LOW(0x34,""),
    PALARM_NIBP_DIA_HIGH(0x35,""),
    PALARM_NIBP_DIA_LOW(0x36,""),
    PALARM_NIBP_ID_MAX(0x36,""),

    //RESP
    PALARM_RESP_ID_MIN(0x37,""),
    PALARM_RESP_RR_HIGH(0x37,""),
    PALARM_RESP_RR_LOW(0x38,""),
    PALARM_RESP_HEART_INTERFERENCE(0x39,""),
    PALARM_RESP_APNEA(0x3a,""),
    PALARM_RESP_ID_MAX(0x3a,""),

    //TEMP
    PALARM_TEMP_ID_MIN(0x3b,""),
    PALARM_TEMP_T1_HIGH(0x3b,""),
    PALARM_TEMP_T1_LOW(0x3c,""),
    PALARM_TEMP_T2_HIGH(0x3d,""),
    PALARM_TEMP_T2_LOW(0x3e,""),
    PALARM_TEMP_TD_HIGH(0x3f,""),
    PALARM_TEMP_TD_LOW(0x40,""),
    PALARM_TEMPGUN_CHANGE_FAST(0x41,""),
    PALARM_TEMPGUN_OUT_OF_RANGE(0x42,""),
    PALARM_TEMP_ID_MAX(0x42,""),

    //BIS
    PALARM_BIS_ID_MIN(0x43,""),
    PALARM_BIS_HIGH(0x43,""),
    PALARM_BIS_LOW(0x44,""),
    PALARM_BIS_ID_MAX(0x44,""),

    //NMT
    PALARM_NMT_ID_MIN(0x45,""),
    PALARM_TOF_TOO_HIGH(0x45,""),
    PALARM_TOF_TOO_LOW(0x46,""),
    PALARM_TOF_COUNT_TOO_HIGH(0x47,""),
    PALARM_TOF_COUNT_TOO_LOW(0x48,""),
    PALARM_NMT_ID_MAX(0x48,""),

    //CO2
    PALARM_CO2_ID_MIN(0x49,""),
    PALARM_CO2_CO2_ET_HIGH(0x49,""),
    PALARM_CO2_CO2_ET_LOW(0x4a,""),
    PALARM_CO2_CO2_FI_HIGH(0x4b,""),
    PALARM_CO2_CO2_FI_LOW(0x4c,""),
    PALARM_CO2_AWRR_HIGH(0x4d,""),
    PALARM_CO2_AWRR_LOW(0x4e,""),
    PALARM_CO2_NORR(0x4f,""),
    PALARM_CO2_ID_MAX(0x4f,""),

    //CO
    PALARM_CO_ID_MIN(0x50,""),
    PALARM_CO_BT_HIGH(0x50,""),
    PALARM_CO_BT_LOW(0x51,""),
    PALARM_CO_CO_HIGH(0x52,""),
    PALARM_CO_CO_LOW(0x53,""),
    PALARM_CO_MULTI_PEAK(0x54,""),
    PALARM_CO_CURVE_TOO_LONG(0x55,""),
    PALARM_CO_CURVE_TOO_SHORT(0x56,""),
    PALARM_CO_RESULT_INVALID(0x57,""),
    PALARM_CO_IT_HIGH(0x58,""),
    PALARM_CO_ID_MAX(0x58,""),

    //AGCO2
    PALARM_AG_ID_MIN(0x59,""),
    PALARM_AG_CO2_ID_MIN(0x59,""),
    PALARM_AG_CO2_ET_HIGH(0x59,""),
    PALARM_AG_CO2_ET_LOW(0x5a,""),
    PALARM_AG_CO2_FI_HIGH(0x5b,""),
    PALARM_AG_CO2_FI_LOW(0x5c,""),
    PALARM_AG_AWRR_HIGH(0x5d,""),
    PALARM_AG_AWRR_LOW(0x5e,""),
    PALARM_AG_NORR(0x5f,""),
    PALARM_AG_CO2_ID_MAX(0x5f,""),

    //AGO2
    PALARM_AG_O2_ID_MIN(0x60,""),
    PALARM_AG_O2_ET_HIGH(0x60,""),
    PALARM_AG_O2_ET_LOW(0x61,""),
    PALARM_AG_O2_FI_HIGH(0x62,""),
    PALARM_AG_O2_FI_LOW(0x63,""),
    PALARM_AG_O2_ID_MAX(0x63,""),

    //AGN2O
    PALARM_AG_N2O_ID_MIN(0x64,""),
    PALARM_AG_N2O_ET_HIGH(0x64,""),
    PALARM_AG_N2O_ET_LOW(0x65,""),
    PALARM_AG_N2O_FI_HIGH(0x66,""),
    PALARM_AG_N2O_FI_LOW(0x67,""),
    PALARM_AG_N2O_ID_MAX(0x67,""),

    //AGAA
    PALARM_AG_AA_ID_MIN(0x68,""),
    PALARM_AG_AAHAL_ID_MIN(0x68,""),
    PALARM_AG_AAHAL_ET_HIGH(0x68,""),
    PALARM_AG_AAHAL_ET_LOW(0x69,""),
    PALARM_AG_AAHAL_FI_HIGH(0x6a,""),
    PALARM_AG_AAHAL_FI_LOW(0x6b,""),
    PALARM_AG_AAHAL_ID_MAX(0x6b,""),
    PALARM_AG_AAENF_ID_MIN(0x6c,""),
    PALARM_AG_AAENF_ET_HIGH(0x6c,""),
    PALARM_AG_AAENF_ET_LOW(0x6d,""),
    PALARM_AG_AAENF_FI_HIGH(0x6e,""),
    PALARM_AG_AAENF_FI_LOW(0x6f,""),
    PALARM_AG_AAENF_ID_MAX(0x6f,""),
    PALARM_AG_AAISO_ID_MIN(0x70,""),
    PALARM_AG_AAISO_ET_HIGH(0x70,""),
    PALARM_AG_AAISO_ET_LOW(0x71,""),
    PALARM_AG_AAISO_FI_HIGH(0x72,""),
    PALARM_AG_AAISO_FI_LOW(0x73,""),
    PALARM_AG_AAISO_ID_MAX(0x73,""),
    PALARM_AG_AASEV_ID_MIN(0x74,""),
    PALARM_AG_AASEV_ET_HIGH(0x74,""),
    PALARM_AG_AASEV_ET_LOW(0x75,""),
    PALARM_AG_AASEV_FI_HIGH(0x76,""),
    PALARM_AG_AASEV_FI_LOW(0x77,""),
    PALARM_AG_AASEV_ID_MAX(0x77,""),
    PALARM_AG_AADES_ID_MIN(0x78,""),
    PALARM_AG_AADES_ET_HIGH(0x78,""),
    PALARM_AG_AADES_ET_LOW(0x79,""),
    PALARM_AG_AADES_FI_HIGH(0x7a,""),
    PALARM_AG_AADES_FI_LOW(0x7b,""),
    PALARM_AG_AADES_ID_MAX(0x7b,""),
    PALARM_AG_AA_ID_MAX(0x7b,""),

    PALARM_AG_ID_MAX(0x7b,""),


    //IBP
    PALARM_IBP_ID_MIN(0x7c,""),
    PALARM_IBP_IBP1_ID_MIN(0x7c,""),
    PALARM_IBP_IBP1_CH1_ID_MIN(0x7c,""),
    PALARM_IBP_IBP1_CH1_SYS_ID_MIN(0x7c,""),
    PALARM_IBP1_CH1_ART_SYS_HIGH(0x7c,""),
    PALARM_IBP1_CH1_ART_SYS_LOW(0x7d,""),
    PALARM_IBP1_CH1_PA_SYS_HIGH(0x7e,""),
    PALARM_IBP1_CH1_PA_SYS_LOW(0x7f,""),
    PALARM_IBP1_CH1_AO_SYS_HIGH(0x80,""),
    PALARM_IBP1_CH1_AO_SYS_LOW(0x81,""),
    PALARM_IBP1_CH1_UAP_SYS_HIGH(0x82,""),
    PALARM_IBP1_CH1_UAP_SYS_LOW(0x83,""),
    PALARM_IBP1_CH1_BAP_SYS_HIGH(0x84,""),
    PALARM_IBP1_CH1_BAP_SYS_LOW(0x85,""),
    PALARM_IBP1_CH1_FAP_SYS_HIGH(0x86,""),
    PALARM_IBP1_CH1_FAP_SYS_LOW(0x87,""),
    PALARM_IBP1_CH1_P1_SYS_HIGH(0x88,""),
    PALARM_IBP1_CH1_P1_SYS_LOW(0x89,""),
    PALARM_IBP_IBP1_CH1_SYS_ID_MAX(0x89,""),
    //
    PALARM_IBP_IBP1_CH1_MEAN_ID_MIN(0x8a,""),
    PALARM_IBP1_CH1_ART_MEAN_HIGH(0x8a,""),
    PALARM_IBP1_CH1_ART_MEAN_LOW(0x8b,""),
    PALARM_IBP1_CH1_PA_MEAN_HIGH(0x8c,""),
    PALARM_IBP1_CH1_PA_MEAN_LOW(0x8d,""),
    PALARM_IBP1_CH1_AO_MEAN_HIGH(0x8e,""),
    PALARM_IBP1_CH1_AO_MEAN_LOW(0x8f,""),
    PALARM_IBP1_CH1_UAP_MEAN_HIGH(0x90,""),
    PALARM_IBP1_CH1_UAP_MEAN_LOW(0x91,""),
    PALARM_IBP1_CH1_BAP_MEAN_HIGH(0x92,""),
    PALARM_IBP1_CH1_BAP_MEAN_LOW(0x93,""),
    PALARM_IBP1_CH1_FAP_MEAN_HIGH(0x94,""),
    PALARM_IBP1_CH1_FAP_MEAN_LOW(0x95,""),
    PALARM_IBP1_CH1_CVP_MEAN_HIGH(0x96,""),
    PALARM_IBP1_CH1_CVP_MEAN_LOW(0x97,""),
    PALARM_IBP1_CH1_LAP_MEAN_HIGH(0x98,""),
    PALARM_IBP1_CH1_LAP_MEAN_LOW(0x99,""),
    PALARM_IBP1_CH1_RAP_MEAN_HIGH(0x9a,""),
    PALARM_IBP1_CH1_RAP_MEAN_LOW(0x9b,""),
    PALARM_IBP1_CH1_ICP_MEAN_HIGH(0x9c,""),
    PALARM_IBP1_CH1_ICP_MEAN_LOW(0x9d,""),
    PALARM_IBP1_CH1_UVP_MEAN_HIGH(0x9e,""),
    PALARM_IBP1_CH1_UVP_MEAN_LOW(0x9f,""),
    PALARM_IBP1_CH1_P1_MEAN_HIGH(0xa0,""),
    PALARM_IBP1_CH1_P1_MEAN_LOW(0xa1,""),
    PALARM_IBP_IBP1_CH1_MEAN_ID_MAX(0xa1,""),
    //
    PALARM_IBP_IBP1_CH1_DIA_ID_MIN(0xa2,""),
    PALARM_IBP1_CH1_ART_DIA_HIGH(0xa2,""),
    PALARM_IBP1_CH1_ART_DIA_LOW(0xa3,""),
    PALARM_IBP1_CH1_PA_DIA_HIGH(0xa4,""),
    PALARM_IBP1_CH1_PA_DIA_LOW(0xa5,""),
    PALARM_IBP1_CH1_AO_DIA_HIGH(0xa6,""),
    PALARM_IBP1_CH1_AO_DIA_LOW(0xa7,""),
    PALARM_IBP1_CH1_UAP_DIA_HIGH(0xa8,""),
    PALARM_IBP1_CH1_UAP_DIA_LOW(0xa9,""),
    PALARM_IBP1_CH1_BAP_DIA_HIGH(0xaa,""),
    PALARM_IBP1_CH1_BAP_DIA_LOW(0xab,""),
    PALARM_IBP1_CH1_FAP_DIA_HIGH(0xac,""),
    PALARM_IBP1_CH1_FAP_DIA_LOW(0xad,""),
    PALARM_IBP1_CH1_P1_DIA_HIGH(0xae,""),
    PALARM_IBP1_CH1_P1_DIA_LOW(0xaf,""),
    PALARM_IBP_IBP1_CH1_DIA_ID_MAX(0xaf,""),
    PALARM_IBP_IBP1_CH1_ID_MAX(0xaf,""),
    //
    PALARM_IBP_IBP1_CH2_ID_MIN(0xb0,""),
    PALARM_IBP_IBP1_CH2_SYS_ID_MIN(0xb0,""),
    PALARM_IBP1_CH2_ART_SYS_HIGH(0xb0,""),
    PALARM_IBP1_CH2_ART_SYS_LOW(0xb1,""),
    PALARM_IBP1_CH2_PA_SYS_HIGH(0xb2,""),
    PALARM_IBP1_CH2_PA_SYS_LOW(0xb3,""),
    PALARM_IBP1_CH2_AO_SYS_HIGH(0xb4,""),
    PALARM_IBP1_CH2_AO_SYS_LOW(0xb5,""),
    PALARM_IBP1_CH2_UAP_SYS_HIGH(0xb6,""),
    PALARM_IBP1_CH2_UAP_SYS_LOW(0xb7,""),
    PALARM_IBP1_CH2_BAP_SYS_HIGH(0xb8,""),
    PALARM_IBP1_CH2_BAP_SYS_LOW(0xb9,""),
    PALARM_IBP1_CH2_FAP_SYS_HIGH(0xba,""),
    PALARM_IBP1_CH2_FAP_SYS_LOW(0xbb,""),
    PALARM_IBP1_CH2_P1_SYS_HIGH(0xbc,""),
    PALARM_IBP1_CH2_P1_SYS_LOW(0xbd,""),
    PALARM_IBP_IBP1_CH2_SYS_ID_MAX(0xbd,""),
    //
    PALARM_IBP_IBP1_CH2_MEAN_ID_MIN(0xbe,""),
    PALARM_IBP1_CH2_ART_MEAN_HIGH(0xbe,""),
    PALARM_IBP1_CH2_ART_MEAN_LOW(0xbf,""),
    PALARM_IBP1_CH2_PA_MEAN_HIGH(0xc0,""),
    PALARM_IBP1_CH2_PA_MEAN_LOW(0xc1,""),
    PALARM_IBP1_CH2_AO_MEAN_HIGH(0xc2,""),
    PALARM_IBP1_CH2_AO_MEAN_LOW(0xc3,""),
    PALARM_IBP1_CH2_UAP_MEAN_HIGH(0xc4,""),
    PALARM_IBP1_CH2_UAP_MEAN_LOW(0xc5,""),
    PALARM_IBP1_CH2_BAP_MEAN_HIGH(0xc6,""),
    PALARM_IBP1_CH2_BAP_MEAN_LOW(0xc7,""),
    PALARM_IBP1_CH2_FAP_MEAN_HIGH(0xc8,""),
    PALARM_IBP1_CH2_FAP_MEAN_LOW(0xc9,""),
    PALARM_IBP1_CH2_CVP_MEAN_HIGH(0xca,""),
    PALARM_IBP1_CH2_CVP_MEAN_LOW(0xcb,""),
    PALARM_IBP1_CH2_LAP_MEAN_HIGH(0xcc,""),
    PALARM_IBP1_CH2_LAP_MEAN_LOW(0xcd,""),
    PALARM_IBP1_CH2_RAP_MEAN_HIGH(0xce,""),
    PALARM_IBP1_CH2_RAP_MEAN_LOW(0xcf,""),
    PALARM_IBP1_CH2_ICP_MEAN_HIGH(0xd0,""),
    PALARM_IBP1_CH2_ICP_MEAN_LOW(0xd1,""),
    PALARM_IBP1_CH2_UVP_MEAN_HIGH(0xd2,""),
    PALARM_IBP1_CH2_UVP_MEAN_LOW(0xd3,""),
    PALARM_IBP1_CH2_P1_MEAN_HIGH(0xd4,""),
    PALARM_IBP1_CH2_P1_MEAN_LOW(0xd5,""),
    PALARM_IBP_IBP1_CH2_MEAN_ID_MAX(0xd5,""),
    //
    PALARM_IBP_IBP1_CH2_DIA_ID_MIN(0xd6,""),
    PALARM_IBP1_CH2_ART_DIA_HIGH(0xd6,""),
    PALARM_IBP1_CH2_ART_DIA_LOW(0xd7,""),
    PALARM_IBP1_CH2_PA_DIA_HIGH(0xd8,""),
    PALARM_IBP1_CH2_PA_DIA_LOW(0xd9,""),
    PALARM_IBP1_CH2_AO_DIA_HIGH(0xda,""),
    PALARM_IBP1_CH2_AO_DIA_LOW(0xdb,""),
    PALARM_IBP1_CH2_UAP_DIA_HIGH(0xdc,""),
    PALARM_IBP1_CH2_UAP_DIA_LOW(0xdd,""),
    PALARM_IBP1_CH2_BAP_DIA_HIGH(0xde,""),
    PALARM_IBP1_CH2_BAP_DIA_LOW(0xdf,""),
    PALARM_IBP1_CH2_FAP_DIA_HIGH(0xe0,""),
    PALARM_IBP1_CH2_FAP_DIA_LOW(0xe1,""),
    PALARM_IBP1_CH2_P1_DIA_HIGH(0xe2,""),
    PALARM_IBP1_CH2_P1_DIA_LOW(0xe3,""),
    PALARM_IBP_IBP1_CH2_DIA_ID_MAX(0xe3,""),
    PALARM_IBP_IBP1_CH2_ID_MAX(0xe3,""),
    PALARM_IBP_IBP1_ID_MAX(0xe3,""),
    //
    PALARM_IBP_IBP2_ID_MIN(0xe4,""),
    PALARM_IBP_IBP2_CH1_ID_MIN(0xe4,""),
    PALARM_IBP_IBP2_CH1_SYS_ID_MIN(0xe4,""),
    PALARM_IBP2_CH1_ART_SYS_HIGH(0xe4,""),
    PALARM_IBP2_CH1_ART_SYS_LOW(0xe5,""),
    PALARM_IBP2_CH1_PA_SYS_HIGH(0xe6,""),
    PALARM_IBP2_CH1_PA_SYS_LOW(0xe7,""),
    PALARM_IBP2_CH1_AO_SYS_HIGH(0xe8,""),
    PALARM_IBP2_CH1_AO_SYS_LOW(0xe9,""),
    PALARM_IBP2_CH1_UAP_SYS_HIGH(0xea,""),
    PALARM_IBP2_CH1_UAP_SYS_LOW(0xeb,""),
    PALARM_IBP2_CH1_BAP_SYS_HIGH(0xec,""),
    PALARM_IBP2_CH1_BAP_SYS_LOW(0xed,""),
    PALARM_IBP2_CH1_FAP_SYS_HIGH(0xee,""),
    PALARM_IBP2_CH1_FAP_SYS_LOW(0xef,""),
    PALARM_IBP2_CH1_P1_SYS_HIGH(0xf0,""),
    PALARM_IBP2_CH1_P1_SYS_LOW(0xf1,""),
    PALARM_IBP_IBP2_CH1_SYS_ID_MAX(0xf1,""),
    //
    PALARM_IBP_IBP2_CH1_MEAN_ID_MIN(0xf2,""),
    PALARM_IBP2_CH1_ART_MEAN_HIGH(0xf2,""),
    PALARM_IBP2_CH1_ART_MEAN_LOW(0xf3,""),
    PALARM_IBP2_CH1_PA_MEAN_HIGH(0xf4,""),
    PALARM_IBP2_CH1_PA_MEAN_LOW(0xf5,""),
    PALARM_IBP2_CH1_AO_MEAN_HIGH(0xf6,""),
    PALARM_IBP2_CH1_AO_MEAN_LOW(0xf7,""),
    PALARM_IBP2_CH1_UAP_MEAN_HIGH(0xf8,""),
    PALARM_IBP2_CH1_UAP_MEAN_LOW(0xf9,""),
    PALARM_IBP2_CH1_BAP_MEAN_HIGH(0xfa,""),
    PALARM_IBP2_CH1_BAP_MEAN_LOW(0xfb,""),
    PALARM_IBP2_CH1_FAP_MEAN_HIGH(0xfc,""),
    PALARM_IBP2_CH1_FAP_MEAN_LOW(0xfd,""),
    PALARM_IBP2_CH1_CVP_MEAN_HIGH(0xfe,""),
    PALARM_IBP2_CH1_CVP_MEAN_LOW(0xff,""),
    PALARM_IBP2_CH1_LAP_MEAN_HIGH(0x0100,""),
    PALARM_IBP2_CH1_LAP_MEAN_LOW(0x0101,""),
    PALARM_IBP2_CH1_RAP_MEAN_HIGH(0x0102,""),
    PALARM_IBP2_CH1_RAP_MEAN_LOW(0x0103,""),
    PALARM_IBP2_CH1_ICP_MEAN_HIGH(0x0104,""),
    PALARM_IBP2_CH1_ICP_MEAN_LOW(0x0105,""),
    PALARM_IBP2_CH1_UVP_MEAN_HIGH(0x0106,""),
    PALARM_IBP2_CH1_UVP_MEAN_LOW(0x0107,""),
    PALARM_IBP2_CH1_P1_MEAN_HIGH(0x0108,""),
    PALARM_IBP2_CH1_P1_MEAN_LOW(0x0109,""),
    PALARM_IBP_IBP2_CH1_MEAN_ID_MAX(0x0109,""),
    //
    PALARM_IBP_IBP2_CH1_DIA_ID_MIN(0x010a,""),
    PALARM_IBP2_CH1_ART_DIA_HIGH(0x010a,""),
    PALARM_IBP2_CH1_ART_DIA_LOW(0x010b,""),
    PALARM_IBP2_CH1_PA_DIA_HIGH(0x010c,""),
    PALARM_IBP2_CH1_PA_DIA_LOW(0x010d,""),
    PALARM_IBP2_CH1_AO_DIA_HIGH(0x010e,""),
    PALARM_IBP2_CH1_AO_DIA_LOW(0x010f,""),
    PALARM_IBP2_CH1_UAP_DIA_HIGH(0x0110,""),
    PALARM_IBP2_CH1_UAP_DIA_LOW(0x0111,""),
    PALARM_IBP2_CH1_BAP_DIA_HIGH(0x0112,""),
    PALARM_IBP2_CH1_BAP_DIA_LOW(0x0113,""),
    PALARM_IBP2_CH1_FAP_DIA_HIGH(0x0114,""),
    PALARM_IBP2_CH1_FAP_DIA_LOW(0x0115,""),
    PALARM_IBP2_CH1_P1_DIA_HIGH(0x0116,""),
    PALARM_IBP2_CH1_P1_DIA_LOW(0x0117,""),
    PALARM_IBP_IBP2_CH1_DIA_ID_MAX(0x0117,""),
    PALARM_IBP_IBP2_CH1_ID_MAX(0x0117,""),
    //
    PALARM_IBP_IBP2_CH2_ID_MIN(0x0118,""),
    PALARM_IBP_IBP2_CH2_SYS_ID_MIN(0x0118,""),
    PALARM_IBP2_CH2_ART_SYS_HIGH(0x0118,""),
    PALARM_IBP2_CH2_ART_SYS_LOW(0x0119,""),
    PALARM_IBP2_CH2_PA_SYS_HIGH(0x011a,""),
    PALARM_IBP2_CH2_PA_SYS_LOW(0x011b,""),
    PALARM_IBP2_CH2_AO_SYS_HIGH(0x011c,""),
    PALARM_IBP2_CH2_AO_SYS_LOW(0x011d,""),
    PALARM_IBP2_CH2_UAP_SYS_HIGH(0x011e,""),
    PALARM_IBP2_CH2_UAP_SYS_LOW(0x011f,""),
    PALARM_IBP2_CH2_BAP_SYS_HIGH(0x0120,""),
    PALARM_IBP2_CH2_BAP_SYS_LOW(0x0121,""),
    PALARM_IBP2_CH2_FAP_SYS_HIGH(0x0122,""),
    PALARM_IBP2_CH2_FAP_SYS_LOW(0x0123,""),
    PALARM_IBP2_CH2_P1_SYS_HIGH(0x0124,""),
    PALARM_IBP2_CH2_P1_SYS_LOW(0x0125,""),
    PALARM_IBP_IBP2_CH2_SYS_ID_MAX(0x0125,""),
    //
    PALARM_IBP_IBP2_CH2_MEAN_ID_MIN(0x0126,""),
    PALARM_IBP2_CH2_ART_MEAN_HIGH(0x0126,""),
    PALARM_IBP2_CH2_ART_MEAN_LOW(0x0127,""),
    PALARM_IBP2_CH2_PA_MEAN_HIGH(0x0128,""),
    PALARM_IBP2_CH2_PA_MEAN_LOW(0x0129,""),
    PALARM_IBP2_CH2_AO_MEAN_HIGH(0x012a,""),
    PALARM_IBP2_CH2_AO_MEAN_LOW(0x012b,""),
    PALARM_IBP2_CH2_UAP_MEAN_HIGH(0x012c,""),
    PALARM_IBP2_CH2_UAP_MEAN_LOW(0x012d,""),
    PALARM_IBP2_CH2_BAP_MEAN_HIGH(0x012e,""),
    PALARM_IBP2_CH2_BAP_MEAN_LOW(0x012f,""),
    PALARM_IBP2_CH2_FAP_MEAN_HIGH(0x0130,""),
    PALARM_IBP2_CH2_FAP_MEAN_LOW(0x0131,""),
    PALARM_IBP2_CH2_CVP_MEAN_HIGH(0x0132,""),
    PALARM_IBP2_CH2_CVP_MEAN_LOW(0x0133,""),
    PALARM_IBP2_CH2_LAP_MEAN_HIGH(0x0134,""),
    PALARM_IBP2_CH2_LAP_MEAN_LOW(0x0135,""),
    PALARM_IBP2_CH2_RAP_MEAN_HIGH(0x0136,""),
    PALARM_IBP2_CH2_RAP_MEAN_LOW(0x0137,""),
    PALARM_IBP2_CH2_ICP_MEAN_HIGH(0x0138,""),
    PALARM_IBP2_CH2_ICP_MEAN_LOW(0x0139,""),
    PALARM_IBP2_CH2_UVP_MEAN_HIGH(0x013a,""),
    PALARM_IBP2_CH2_UVP_MEAN_LOW(0x013b,""),
    PALARM_IBP2_CH2_P1_MEAN_HIGH(0x013c,""),
    PALARM_IBP2_CH2_P1_MEAN_LOW(0x013d,""),
    PALARM_IBP_IBP2_CH2_MEAN_ID_MAX(0x013d,""),
    //
    PALARM_IBP_IBP2_CH2_DIA_ID_MIN(0x013e,""),
    PALARM_IBP2_CH2_ART_DIA_HIGH(0x013e,""),
    PALARM_IBP2_CH2_ART_DIA_LOW(0x013f,""),
    PALARM_IBP2_CH2_PA_DIA_HIGH(0x0140,""),
    PALARM_IBP2_CH2_PA_DIA_LOW(0x0141,""),
    PALARM_IBP2_CH2_AO_DIA_HIGH(0x0142,""),
    PALARM_IBP2_CH2_AO_DIA_LOW(0x0143,""),
    PALARM_IBP2_CH2_UAP_DIA_HIGH(0x0144,""),
    PALARM_IBP2_CH2_UAP_DIA_LOW(0x0145,""),
    PALARM_IBP2_CH2_BAP_DIA_HIGH(0x0146,""),
    PALARM_IBP2_CH2_BAP_DIA_LOW(0x0147,""),
    PALARM_IBP2_CH2_FAP_DIA_HIGH(0x0148,""),
    PALARM_IBP2_CH2_FAP_DIA_LOW(0x0149,""),
    PALARM_IBP2_CH2_P1_DIA_HIGH(0x014a,""),
    PALARM_IBP2_CH2_P1_DIA_LOW(0x014b,""),
    PALARM_IBP_IBP2_CH2_DIA_ID_MAX(0x014b,""),
    PALARM_IBP_IBP2_CH2_ID_MAX(0x014b,""),
    PALARM_IBP_IBP2_ID_MAX(0x014b,""),
    PALARM_IBP_ID_MAX(0x014b,""),

    PALARM_DEFINE_END(0x014c,"");

    private int code;

    private String description;

    private static final int BITMASK = 0xFF;

    PhysiologyAlarmEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PhysiologyAlarmEnum decode(int physiologyAlarmEnumCode) {

        int code = physiologyAlarmEnumCode & BITMASK;
        for (PhysiologyAlarmEnum physiologyAlarmEnum : PhysiologyAlarmEnum.values()) {
            if (physiologyAlarmEnum.code == code) {
                QcmsLogUtils.mpDecode("receive physiologyAlarmEnum:" + physiologyAlarmEnum);
                return physiologyAlarmEnum;
            }
        }
        return null;
    }
}