package com.mlnx.ecg.store.utils;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by amanda.shan on 2017/12/29.
 */
public class Base64Utils {

    private static BASE64Encoder encoder = new BASE64Encoder();
    private static BASE64Decoder decoder = new BASE64Decoder();

    public static String enc(byte[] bytes) {
        return encoder.encode(bytes);
    }

    public static byte[] dec(String s) throws IOException {
        return decoder.decodeBuffer(s);
    }
}
