package com.mlnx.analysis;

import com.sun.jna.Library;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.ShortByReference;

/**
 * Created by amanda.shan on 2018/2/6.
 */
public interface AnalysisLib extends Library {

    // 算法初始化
    void InitEcgAna();

    void InitECGFilter();

    // 数据解析
    void GetProcData(byte[] iEcgdata, byte[] gpu8AcId,
                     byte[] gu32EncryptEcgOutData);

    // 获取滤波后波形
    void GetFilterData(int[] filterData);

    // 算法调用
    // 获取算法调用标志
    byte CheckMonitorAnalysisStartFlag();

    // 开始实时分析
    void AlielseEcgAnalysis();

    // 结果获取
    // 结果获取标志
    byte MonitorAnalysisStopFlag();

    void ClearEcgAnalysisFlag();

    // (ECG_ANA_PARAM_LIST)
    byte EcgGetParam(Integer tIndex, ShortByReference pi16Value);

    // (ECG_ANA_ARR_CODE_LIST)
    byte EcgGetArrhythmia(IntByReference type, ShortByReference last_time,
                          ShortByReference currentPos);
}
