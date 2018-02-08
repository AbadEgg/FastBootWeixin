package com.mlnx.push_tp.utils;

import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReadUtils {
    /**
     * 读取时间
     *
     * @param frame
     * @param dataType
     * @param dataLen
     * @return
     */
    public static long readTime(ByteBuffer frame, String dataType, int dataLen)
            throws InvalidPacketException {
        switch (dataType) {
            case "DT":
                if (dataLen != 17 && dataLen != 14)
                    throw new InvalidPacketException("数据格式 数据长度无法对应 dataType:"
                            + dataType + " dataLen = " + dataLen);
                else {
                    byte[] bs = new byte[dataLen];
                    frame.get(bs);
                    String string = new String(bs);
                    SimpleDateFormat format = null;
                    if (dataLen == 17) {
                        format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                    } else if (dataLen == 14) {
                        format = new SimpleDateFormat("yyyyMMddHHmmss");
                    }

                    long time = 0;
                    try {
                        time = format.parse(string).getTime();
                        PushLogUtils.mpDecode("readTime:" + string);
                    } catch (ParseException e) {
                        throw new InvalidPacketException("时间格式解析出错", bs);
                    }
                    return time;
                }
        }
        throw new InvalidPacketException(String.format("非法时间数据格式 %s", dataType));
    }

    /**
     * 读取数据
     *
     * @param frame
     * @param dataType
     * @param dataLen
     * @return
     * @throws InvalidPacketException
     */
    public static int readData(ByteBuffer frame, String dataType, int dataLen)
            throws InvalidPacketException {
        switch (dataType) {

            case "BT":
                if (dataLen != 1)
                    throw new InvalidPacketException("数据格式 数据长度无法对应 dataType:"
                            + dataType + " dataLen = " + dataLen);
                else {
                    byte[] bs = new byte[1];
                    frame.get(bs);
                    int data = 0;
                    data = bs[0] & 0x000000ff;
                    return data;
                }

            case "CS":
                if (dataLen != 1)
                    throw new InvalidPacketException("数据格式 数据长度无法对应 dataType:"
                            + dataType + " dataLen = " + dataLen);
                else {
                    byte[] bs = new byte[1];
                    frame.get(bs);
                    int data = 0;
                    data = bs[0] & 0x000000ff;
                    return data;
                }
            case "US":
                if (dataLen != 2)
                    throw new InvalidPacketException("数据格式 数据长度无法对应 dataType:"
                            + dataType + " dataLen = " + dataLen);
                else {
                    byte[] bs = new byte[2];
                    frame.get(bs);
                    int data = 0;
                    data |= bs[1] & 0x000000ff;
                    data <<= 8;
                    data |= bs[0] & 0x000000ff;
                    return data;
                }
            case "UL":
                if (dataLen != 4)
                    throw new InvalidPacketException("数据格式 数据长度无法对应 dataType:"
                            + dataType + " dataLen = " + dataLen);
                else {
                    byte[] bs = new byte[4];
                    frame.get(bs);
                    int data = 0;
                    data |= bs[3] & 0x000000ff;
                    data <<= 8;
                    data |= bs[2] & 0x000000ff;
                    data <<= 8;
                    data |= bs[1] & 0x000000ff;
                    data <<= 8;
                    data |= bs[0] & 0x000000ff;
                    return data;
                }
        }
        throw new InvalidPacketException(String.format("非法数据格式 %s", dataType));
    }

    /**
     * 读取字符�?
     *
     * @param frame
     * @param dataType
     * @param dataLen
     * @return
     */
    public static String readString(ByteBuffer frame, String dataType,
                                    int dataLen) {
        switch (dataType) {
            case "CS":
            case "DS":
                if (dataLen > 1000)
                    throw new InvalidPacketException("数据格式 数据长度无法对应 dataType:"
                            + dataType + " dataLen = " + dataLen);
                else {
                    byte[] bs = new byte[dataLen];
                    frame.get(bs);
                    return new String(bs);
                }
        }
        throw new InvalidPacketException(String.format("非法数据格式 %s", dataType));
    }

    public static Float readTemp(ByteBuffer frame, String dataType, int dataLen)
            throws InvalidPacketException {
        if (dataLen != 2)
            throw new InvalidPacketException("数据格式 数据长度无法对应 dataType:"
                    + dataType + " dataLen = " + dataLen);
        else {
            byte[] bs = new byte[2];
            frame.get(bs);
            int data = 0;
            data |= bs[0] & 0x000000ff;
            data *= 10;
            data += bs[1] & 0x000000ff;
            return data / 10.0f;
        }
    }

    public static byte[] readBytes(ByteBuffer frame, String dataType,
                                   int dataLen) {
        switch (dataType) {
            case "OW":
                // if (dataLen != 16 && dataLen != 6 && dataLen != 2) {
                // throw new InvalidPacketException("数据格式 数据长度无法对应 dataType:"
                // + dataType + " dataLen = " + dataLen);
                // } else {
                byte[] bs = new byte[dataLen];
                frame.get(bs);
                return bs;
            // }
        }
        throw new InvalidPacketException(String.format("非法数据格式 %s", dataType));

    }

    public static void main(String[] args) {
        byte[] bs = {5, 5};
        int data = 0;
        data |= bs[0] & 0x000000ff;
        data *= 10;
        data += bs[1] & 0x000000ff;
        System.out.println(data/10.0f);
    }

}
