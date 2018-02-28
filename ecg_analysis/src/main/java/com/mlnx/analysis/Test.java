package com.mlnx.analysis;

import java.io.IOException;

/**
 * Created by amanda.shan on 2018/2/5.
 */
public class Test {

    private static byte[] cpuID = new byte[]{0x0E, (byte) 0x80, 0x01, (byte) 0x80, 0x16, 0x51, 0x36, 0x32, 0x38,
            0x37, 0x37, 0x31};


//    private static byte[] ecgs = new byte[]{-128, -107, -111, -127, 19, -9, 127, -95, 40, 127, -101, -81, 127, -101,
//            -27, 127, -35, 61, -128, 43, 88, -128, 74, 54};
//
//    private static byte[] erecgs = new byte[]{-33, -6, 95, 4, 80, 125, -74, -109, -120, -94, 21, 68, -28, 50, 10, 59,
//            116, 25, -53, -30, -114, -14, -72, 56};

    private static byte[] ecgs = new byte[]{-101, -5, 103, -72, 6, 47, -121, 106, -122, -58, -90, -71, 34, 25, -104,
            -97, -9, 125, -80, -22, 51, 100, -52, 34};

    private static byte[] erecgs = new byte[]{15, -90, 10, 107, 6, 47, -121, 106, 127, 96, 108, -29, 34, 25, -104,
            -97, -13, -21, -101, 74, 51, 100, -52, 34,};

    public static void main(String[] args) throws IOException {

        System.out.println(ecgs.length);
        System.out.println(erecgs.length);

        byte[] gu32EncryptEcgOutData = new byte[24];

        EcgAnalysis ecgAnalysis = new EcgAnalysis("test");
        ecgAnalysis.init();

        // 读取一行并解析数据
        ecgAnalysis.getAnalysisLib().GetProcData(erecgs, cpuID, gu32EncryptEcgOutData);

        for (int i = 0; i < erecgs.length; i++) {
            System.out.print(String.format("%02x ", erecgs[i]));
        }
        System.out.println();

        for (int i = 0; i < gu32EncryptEcgOutData.length; i++) {
            System.out.print(String.format("%02x ", gu32EncryptEcgOutData[i]));
        }
        System.out.println();

        for (int i = 0; i < ecgs.length; i++) {
            System.out.print(String.format("%02x ", ecgs[i]));
        }
        System.out.println();
    }

}
