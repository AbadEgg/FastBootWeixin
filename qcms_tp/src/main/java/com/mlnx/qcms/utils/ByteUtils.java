package com.mlnx.qcms.utils;

/**
 * @author fzh
 * @create 2018/1/24 20:18
 */
public class ByteUtils {

    public static byte[] intToBytes(int value) {
        return intToBytes(value, 4);
    }

    public static byte[] intToBytes(int value, int len) {
        byte[] src = new byte[len];

        for (int i = 0; i < len && i < 4; ++i) {
            src[i] = (byte) ((value >> (i * 8)) & 0xFF);
        }
        return src;
    }

    public static byte[] longToBytes(long value) {
        return longToBytes(value, 8);
    }

    public static byte[] longToBytes(long value, int len) {
        byte[] src = new byte[len];

        for (int i = 0; i < len && i < 8; ++i) {
            src[i] = (byte) ((value >> (i * 8)) & 0xFF);
        }
        return src;
    }

    //无符号转换
    public static int bytesToInt(byte[] src, int len) {
        int value = 0;

        for (int i = 0; i < len && i < 4; ++i) {
            value |= (src[i] & 0xFF) << (i * 8);
        }
        return value;
    }

    //有符号转换
    public static int bytesToSignInt(byte[] src, int len) {
        int value = 0;

        for (int i = 0; i < 4; ++i) {
            if (i < len){
                value |= (src[i] & 0xFF) << (i * 8);
            }else{
                if ((byte)(src[len-1] & 0x80 )== (byte)0x80){
                    value |= (0xFF) << (i * 8);
                }
            }
        }
        return value;

    }

    public static int bytesToInt(byte[] src) {
        return bytesToInt(src, 4);
    }

    public static long bytesToLong(byte[] src) {
        return ((((long) src[7] & 0xff) << 56) | (((long) src[6] & 0xff) << 48)
                | (((long) src[5] & 0xff) << 40)
                | (((long) src[4] & 0xff) << 32)
                | (((long) src[3] & 0xff) << 24)
                | (((long) src[2] & 0xff) << 16)
                | (((long) src[1] & 0xff) << 8) | (((long) src[0] & 0xff) << 0));
    }


    public static void main(String[] args) {
        byte[] bytes = {(byte) 0xf0,0x01,0x02,0x03};
        System.out.println(String.format("%x",ByteUtils.bytesToInt(bytes)));
    }
}
