package com.mlnx.analysis;

import com.mlnx.analysis.utils.FileUtils;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.ShortByReference;

import java.io.File;
import java.io.IOException;

public class EcgMonitorUtil {
    private interface MonitorAnalysis extends Library {

        // 算法初始化
        void InitEcgAna();

        void InitECGFilter();

        // 数据解析
        void GetProcData(byte[] iEcgdata, byte[] gpu8AcId,
                         byte[] gu32EncryptEcgOutData);

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

    private MonitorAnalysis monitorAnalysis;
    private byte[] mcuId;

    public EcgMonitorUtil(String dllPath, Integer patientId, byte[] mcuId) {
        this.mcuId = mcuId;
        monitorAnalysis = Native.loadLibrary(dllPath, MonitorAnalysis.class);

        monitorAnalysis.InitEcgAna();
        monitorAnalysis.InitECGFilter();
    }

    private static String getCopiedDllPath(String dllPath, Integer patientId) throws IOException {
        File currDirectory = new File("");
        File dll = new File(dllPath);
        File tempDirectory = new File(currDirectory.getAbsoluteFile()
                + File.separator + "temp");
        if (!tempDirectory.exists()) {
            tempDirectory.mkdir();
        }

        File tempDll = new File(tempDirectory.getAbsoluteFile()
                + File.separator + patientId + ".dll");
        if (!tempDll.exists()){
            FileUtils.copyFile(dll, tempDll);
        }

        return tempDll.getAbsolutePath().replace(".dll", "");
    }


    // 数据解密
    public byte[] ecgDataInput(byte[] encryptedData, byte[] mcuId) {
        byte[] decodedData = new byte[24];
        monitorAnalysis.GetProcData(encryptedData, mcuId, decodedData);
        return decodedData;
    }
}
