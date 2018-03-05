package com.mlnx.device_server.comm.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by amanda.shan on 2018/3/5.
 */
public class MacUtils {

    private static Logger logger = LoggerFactory.getLogger(MacUtils.class);

    public static byte[] getMcuId(String mcuId) {
        String[] strs = mcuId.split(":");
        byte[] bytes = new byte[strs.length];
        for (int i = 0; i < strs.length; i++) {
            bytes[i] = (byte) Integer.parseInt(strs[i], 16);
        }

        logger.debug(mcuId);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            buffer.append(String.format("%x ", bytes[i]));
        }
        logger.debug(buffer.toString());

        return bytes;
    }

    public static void main(String[] args) {
        byte[] bytes = getMcuId("00:00:2F:00:16:51:36:32:38:37:37:31");

        for (int i = 0; i < bytes.length; i++) {
            System.out.print(String.format("%x ", bytes[i]));
        }
//        System.out.println(Arrays.toString(bytes));
    }
}
