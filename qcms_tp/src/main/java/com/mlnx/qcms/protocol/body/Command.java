package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.utils.ByteUtils;

import java.nio.ByteBuffer;

/**
 * 设备命令
 *
 * @author fzh
 * @create 2018/1/23 14:55
 */
public class Command extends DataHeader {

    private CmdType cmdType;            //命令类型
    private int param1;            //参数1
    private int param2;            //参数2
    private final static ByteBuffer buffer = ByteBuffer.allocate(13);


    public CmdType getCmdType() {
        return cmdType;
    }

    public void setCmdType(CmdType cmdType) {
        this.cmdType = cmdType;
    }

    public int getParam1() {
        return param1;
    }

    public void setParam1(int param1) {
        this.param1 = param1;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }

    @Override
    public String toString() {
        return "Command{" +
                "packageType=" + packageType +
                ", checksum=" + checksum +
                ", packageLength=" + packageLength +
                ", cmdType=" + cmdType +
                ", param1=" + param1 +
                ", param2=" + param2 +
                '}';
    }

    public Command() {
        packageType = PackageType.PACKAGE_CMD;
        packageLength = 13;
    }

    @Override
    public void decodeData(ByteBuffer buf) {
        cmdType = CmdType.decode(buf.get());
        byte[] b4 = new byte[4];
        buf.get(b4);
        param1 = ByteUtils.bytesToInt(b4);
        buf.get(b4);
        param2 = ByteUtils.bytesToInt(b4);
    }

    @Override
    public byte[] encodeData() {
        byte[] bs = new byte[13];
        buffer.clear();
        buffer.put(ByteUtils.intToBytes(packageLength, 2));
        buffer.put((byte) (cmdType.getCode()));
        buffer.put(ByteUtils.intToBytes(param1));
        buffer.put(ByteUtils.intToBytes(param2));
        buffer.flip();
        byte value = 0;
        for (int i = 0; i < buffer.remaining(); i++) {
            byte b = buffer.get();
            bs[i + 2] = b;
            value += b;
        }
        bs[0] = (byte) (packageType.getCode() & 0xff);
        bs[1] = value;
        return bs;
    }
}

