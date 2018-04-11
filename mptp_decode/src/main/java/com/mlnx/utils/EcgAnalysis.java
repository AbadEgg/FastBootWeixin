package com.mlnx.utils;

import com.mlnx.analysis.AnalysisLib;
import com.mlnx.analysis.filter.EcgFilter;
import com.mlnx.analysis.utils.FileUtils;
import com.mlnx.mptp.model.analysis.HeartResult;
import com.mlnx.mptp.model.analysis.RealEcgAnalysResult;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.ShortByReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author fzh
 * @create 2018/4/2 14:18
 */
public class EcgAnalysis {

    private Logger logger = LoggerFactory.getLogger(EcgAnalysis.class);

    private String deviceId;
    public byte[] gpu8AcId = new byte[]{0x0E, (byte) 0x80, 0x01, (byte) 0x80, 0x16, 0x51, 0x36, 0x32, 0x38,
            0x37, 0x37, 0x31};

//    private byte[] gpu8AcId = new byte[]{0x00, 0x00, 0x2F, 0x00, 0x16, 0x51, 0x36, 0x32, 0x38, 0x37, 0x37, 0x31};


    private AnalysisLib analysisLib;

    public EcgAnalysis(String deviceId, byte[] gpu8AcId) {
        this.deviceId = deviceId;
        this.gpu8AcId = gpu8AcId;
    }

    public EcgAnalysis(String deviceId) {
        this.deviceId = deviceId;
    }

    public EcgAnalysis() {
    }

    public AnalysisLib getAnalysisLib() {
        return analysisLib;
    }

    public void init() throws IOException {

        String path = new File("ecg_analysis" + File.separator + "libecg12.dll").getAbsolutePath();

//        path = getCopiedDllPath(path, deviceId);
        analysisLib = (AnalysisLib) Native.loadLibrary(path, AnalysisLib.class);
        initAnalysis();
    }

    private void initAnalysis() {
        // 算法初始化
        analysisLib.InitEcgAna();
        analysisLib.InitECGFilter();

//        setEcgFilter(FilterManager.getSsportEcgFilter());
    }

    public void setEcgFilter(EcgFilter ecgFilter) {
        analysisLib.SetFilter(ecgFilter.getLowpassFilterList().getCode(), ecgFilter.getHighpassFilterList().getCode()
                , ecgFilter.getEcgStopFilterList().getCode());
    }

    public RealEcgAnalysResult realAnalysis(byte[] encryptionEcgDatas, long packetTime) {

        int encryptionIndex = 0;
        int index = 0;
        RealEcgAnalysResult result = null;

        if (encryptionEcgDatas != null && gpu8AcId != null) {

            result = new RealEcgAnalysResult();
            result.setTime(packetTime);

            byte[] ecgData = new byte[encryptionEcgDatas.length];
            byte[] filterEcgData = new byte[encryptionEcgDatas.length];
            result.setEcgData(ecgData);
            result.setFilterEcgData(filterEcgData);

            while (encryptionEcgDatas.length - encryptionIndex >= 24) {

                byte[] iEcgdata = new byte[24];
                byte[] gu32EncryptEcgOutData = new byte[24];
                int[] filterData = new int[8];

                for (int i = 0; i < 24; i++, encryptionIndex++) {
                    iEcgdata[i] = encryptionEcgDatas[encryptionIndex];
                }

                // 读取一行并解析数据
                analysisLib.GetProcData(iEcgdata, gpu8AcId, gu32EncryptEcgOutData);

                analysisLib.GetFilterData(filterData);

                // 判断是否可分析标志
                byte monitorFlag = analysisLib.CheckMonitorAnalysisStartFlag();

                if (monitorFlag == 1) {
                    // 调用分析函数
                    analysisLib.AlielseEcgAnalysis();
//                    logger.debug("分析完成");

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

                        for (int i = 2, j = 0; i <= 9 && j < 8; i++, j++) {
                            analysisLib.EcgGetParam(i, shortByReference);
                            builder.append("st" + (j + 1) + "：" + shortByReference.getValue() + " ");
                            result.getSts()[j] = (int) shortByReference.getValue();
                        }

                        IntByReference type = new IntByReference();
                        ShortByReference last_time = new ShortByReference();
                        ShortByReference currentPos = new ShortByReference();
                        analysisLib.EcgGetArrhythmia(type, last_time, currentPos);

                        result.setHeartResult(HeartResult.decode(type.getValue()));

                        builder.append(" 疾病type:" + (type.getValue() & 0x7F)
                                + "--last_time:" + last_time.getValue()
                                + "--currentPos:" + currentPos.getValue());

//                        logger.debug("出结果:" + builder.toString());
                    }
                    analysisLib.ClearEcgAnalysisFlag();
                }

                byte[] bytes = new byte[24];
                int j = 0;
                for (int i = 0; i < filterData.length; i++) {
                    bytes[j++] = (byte) (filterData[i] >> 16);
                    bytes[j++] = (byte) (filterData[i] >> 8);
                    bytes[j++] = (byte) (filterData[i]);
                }

                for (int i = 0; i < gu32EncryptEcgOutData.length; i++, index++) {
                    ecgData[index] = gu32EncryptEcgOutData[i];
                    filterEcgData[index] = bytes[i];
                }
            }
        }
        return result;
    }

    // 数据滤波
    public int[] ecgFilter() {
        int[] filterData = new int[8];
        analysisLib.GetFilterData(filterData);
        return filterData;
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

//        byte[] gpu8AcId = new byte[]{0x0E, (byte) 0x80, 0x01, (byte) 0x80, 0x16, 0x51, 0x36, 0x32, 0x38,
//                0x37, 0x37, 0x31};
//
//        StringBuilder builder = new StringBuilder();
//        for (byte b : gpu8AcId) {
//            builder.append(String.format("%02x:", b));
//        }
//        builder.delete(builder.length() - 1, builder.length());
//
//        builder.append(builder.toString());
    }
}
