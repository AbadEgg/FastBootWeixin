package com.mlnx.qcms.protocol.head;

import com.mlnx.qcms.protocol.Codec;
import com.mlnx.qcms.utils.ByteUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;


/**
 * 协议头
 *
 * @author fzh
 * @create 2018/1/23 9:16
 */
public class Header implements Codec {

    public static final byte[] Heads = {0x51, 0x43, 0x4D, 0x53}; // 数据包包头字节

    private byte headCheckSum;		//头结构校验核
    private int version;			//版本
    private DeviceType deviceType;		//设备类型
    private int packageNum;		//头结构后跟的包个数
    private int packageBytes;		//整个包包长
    private boolean bPackageOnlyParam;	//是否仅仅只包含参数包
    private MesureMode measureMode;		//测量模式
    private byte[] reserve;		//保留字段

    private static final ByteBuffer buffer = ByteBuffer.allocate(16);

    public byte getHeadCheckSum() {
        return headCheckSum;
    }

    public void setHeadCheckSum(byte headCheckSum) {
        this.headCheckSum = headCheckSum;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public int getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(int packageNum) {
        this.packageNum = packageNum;
    }

    public int getPackageBytes() {
        return packageBytes;
    }

    public void setPackageBytes(int packageBytes) {
        this.packageBytes = packageBytes;
    }

    public boolean isbPackageOnlyParam() {
        return bPackageOnlyParam;
    }

    public void setbPackageOnlyParam(boolean bPackageOnlyParam) {
        this.bPackageOnlyParam = bPackageOnlyParam;
    }

    public MesureMode getMeasureMode() {
        return measureMode;
    }

    public void setMeasureMode(MesureMode measureMode) {
        this.measureMode = measureMode;
    }

    public byte[] getReserve() {
        return reserve;
    }

    public void setReserve( byte[] reserve) {
        this.reserve = reserve;
    }

    @Override
    public void decode(ByteBuffer byteBuf) throws UnsupportedEncodingException {
        headCheckSum = byteBuf.get();
        version= byteBuf.get();
        deviceType = DeviceType.decode(byteBuf.get());
        packageNum = byteBuf.get() & 0x000000ff;
        byte[] b4 = new byte[4];
        byteBuf.get(b4);
        packageBytes = ByteUtils.bytesToInt(b4);
        bPackageOnlyParam = byteBuf.get()==0x01?true:false;
        measureMode = MesureMode.decode(byteBuf.get());
        byte[] b2 = new byte[2];
        byteBuf.get(b2);
        reserve = b2;
    }

    @Override
    public byte[] encode() {
        byte[] bs = new byte[16];
        buffer.clear();

        buffer.put((byte) (version & 0xFF));
        buffer.put((byte) (deviceType.getCode() & 0xFF));
        buffer.put((byte) (packageNum & 0xFF));
        buffer.put(ByteUtils.intToBytes(packageBytes));
        buffer.put((byte) (bPackageOnlyParam?0x01:0x00));
        buffer.put((byte) (measureMode==null?0x00:measureMode.getCode() & 0xFF));
        buffer.put(reserve==null?new byte[2]:reserve);

        buffer.flip();
        byte value = 0;
        for (int i = 0; i < buffer.remaining() ; i++) {
            byte b = buffer.get();
            bs[i+5] = b;
            value += b;
        }
        for (int i = 0; i < Heads.length ; i++) {
            bs[i] = Heads[i];
        }
        bs[4] = value;
        return bs;
    }

}
