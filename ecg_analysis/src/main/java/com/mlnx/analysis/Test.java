package com.mlnx.analysis;

import java.io.File;

/**
 * Created by amanda.shan on 2018/2/5.
 */
public class Test {

    private static byte[] cpuID = new byte[]{0x0E, (byte) 0x80, 0x01, (byte) 0x80, 0x16, 0x51, 0x36, 0x32, 0x38,
            0x37, 0x37, 0x31};

    public static void main(String[] args) {

        String path = new File("ecg_analysis" + File.separator + "libecg12.dll").getAbsolutePath();
        System.out.println(path);

        EcgMonitorUtil ecgMonitorUtil = new EcgMonitorUtil(path, 1, cpuID);
    }

}
