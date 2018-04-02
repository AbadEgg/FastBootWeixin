package com.mlnx.utils;

import com.mlnx.mptp.utils.MptpLogUtils;

import java.nio.ByteBuffer;

public class ByteBuffUtils {

    public static synchronized void addBytes(ByteBuffer packetBuff, byte[] addBytes) {


        if (addBytes != null) {

//            LogUtils.d(String.format("addBytes before pos=%d, limit=%d", packetBuff.position(),
//                    packetBuff.limit()));
            packetBuff.compact();

//            LogUtils.d(String.format("addBytes after pos=%d, limit=%d", packetBuff.position(),
//                    packetBuff.limit()));

            int len = addBytes.length;
            if (len > packetBuff.remaining()) {
                packetBuff.clear();
//                MptpLogUtils.e(String.format("Buffer is Overflow remaining:%d  addlen:%d ",
//                        packetBuff.remaining(), len));
            }
            packetBuff.put(addBytes, 0, len);
            packetBuff.flip();
        }
    }

    public static synchronized void addBytes(ByteBuffer packetBuff, byte[] addBytes, int len) {
        if (addBytes != null) {
            packetBuff.compact();

            if (len > packetBuff.remaining()) {
                packetBuff.clear();
                MptpLogUtils.e(String.format("Buffer is Overflow remaining:%d  addlen:%d ",
                        packetBuff.remaining(), len));
            }
            packetBuff.put(addBytes, 0, len);
            packetBuff.flip();
        }
    }

    public static synchronized int getSize(ByteBuffer packetBuff) {
        return packetBuff.remaining();
    }

    public static synchronized byte[] getBytes(ByteBuffer packetBuff, int getSize) {
        byte[] bs = null;
        if (packetBuff.remaining() >= getSize) {
            bs = new byte[getSize];
            packetBuff.get(bs);
        }
        return bs;
    }
}
