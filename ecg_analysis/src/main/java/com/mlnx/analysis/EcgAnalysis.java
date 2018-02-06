package com.mlnx.analysis;

import com.mlnx.analysis.domain.HeartResult;
import com.mlnx.analysis.domain.ReadEcgAnalysResult;
import com.mlnx.analysis.utils.FileUtils;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.ShortByReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


/**
 * Created by amanda.shan on 2018/2/6.
 */
public class EcgAnalysis {

    private Logger logger = LoggerFactory.getLogger(EcgAnalysis.class);

    private String deviceId;
    private byte[] gpu8AcId = new byte[]{0x0E, (byte) 0x80, 0x01, (byte) 0x80, 0x16, 0x51, 0x36, 0x32, 0x38,
            0x37, 0x37, 0x31};

    private AnalysisLib analysisLib;

    public EcgAnalysis(String deviceId) {
        this.deviceId = deviceId;
    }

    public void init() throws IOException {

        String path = new File("ecg_analysis" + File.separator + "libecg12.dll").getAbsolutePath();

        path = getCopiedDllPath(path, deviceId);
        analysisLib = Native.loadLibrary(path, AnalysisLib.class);

        // 算法初始化
        analysisLib.InitEcgAna();

        analysisLib.InitECGFilter();
    }

    public ReadEcgAnalysResult realAnalysis(byte[] encryptionEcgDatas, long packetTime) {

        int encryptionIndex = 0;
        int index = 0;
        ReadEcgAnalysResult result = null;

        if (encryptionEcgDatas != null && gpu8AcId != null) {

            result = new ReadEcgAnalysResult();
            result.setTime(packetTime);

            byte[] ecgData = new byte[encryptionEcgDatas.length];
            result.setEcgData(ecgData);

            while (encryptionEcgDatas.length - encryptionIndex >= 24) {

                byte[] iEcgdata = new byte[24];
                byte[] gu32EncryptEcgOutData = new byte[24];

                for (int i = 0; i < 24; i++, encryptionIndex++) {
                    iEcgdata[i] = encryptionEcgDatas[encryptionIndex];
                }

                // 读取一行并解析数据
                analysisLib.GetProcData(iEcgdata, gpu8AcId, gu32EncryptEcgOutData);

                // 判断是否可分析标志
                byte monitorFlag = analysisLib.CheckMonitorAnalysisStartFlag();

                if (monitorFlag == 1) {
                    // 调用分析函数
                    analysisLib.AlielseEcgAnalysis();
                    logger.info("分析完成");

                    StringBuilder builder = new StringBuilder();

                    byte monitorResultFlag = analysisLib.MonitorAnalysisStopFlag();
                    if (monitorResultFlag == 1) {
                        // 获取结果
                        ShortByReference shortByReference = new ShortByReference();
                        analysisLib.EcgGetParam(0, shortByReference);
                        builder.append("心率：" + shortByReference.getValue() + " ");
                        if (shortByReference.getValue() >= 0) {
                            result.setHeart((int) shortByReference.getValue());
                        }

                        analysisLib.EcgGetParam(1, shortByReference);
                        builder.append("早搏个数：" + shortByReference.getValue() + " ");
                        if (shortByReference.getValue() >= 0) {
                            result.setPbNumb((int) shortByReference.getValue());
                        }

                        IntByReference type = new IntByReference();
                        ShortByReference last_time = new ShortByReference();
                        ShortByReference currentPos = new ShortByReference();
                        analysisLib.EcgGetArrhythmia(type, last_time, currentPos);

                        result.setHeartResult(HeartResult.decode(type.getValue()));

                        builder.append(" 疾病type:" + (type.getValue() & 0x7F)
                                + "--last_time:" + last_time.getValue()
                                + "--currentPos:" + currentPos.getValue());

                        logger.info("出结果:" + builder.toString());
                    }
                    analysisLib.ClearEcgAnalysisFlag();
                }

                for (int i = 0; i < gu32EncryptEcgOutData.length; i++, index++) {
                    ecgData[index] = gu32EncryptEcgOutData[i];
                }
            }
        }
        return result;
    }

    private static String getCopiedDllPath(String dllPath, String deviceId) throws IOException {
        File currDirectory = new File("");
        File dll = new File(dllPath);
        File tempDirectory = new File(currDirectory.getAbsoluteFile()
                + File.separator + "temp");
        if (!tempDirectory.exists()) {
            tempDirectory.mkdir();
        }

        File tempDll = new File(tempDirectory.getAbsoluteFile()
                + File.separator + deviceId + "_ecg.dll");

        if (tempDll.exists()) {
            tempDll.delete();
        }

        if (!tempDll.exists()) {
            FileUtils.copyFile(dll, tempDll);
        }

        return tempDll.getAbsolutePath().replace(".dll", "");
    }

    public static void main(String[] args) throws IOException {
        EcgAnalysis ecgAnalysis = new EcgAnalysis("111");
        ecgAnalysis.init();
    }
}
