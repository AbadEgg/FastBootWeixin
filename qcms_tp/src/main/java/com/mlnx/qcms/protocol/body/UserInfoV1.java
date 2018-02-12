package com.mlnx.qcms.protocol.body;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * @author fzh
 * @create 2018/1/26 16:22
 */
public class UserInfoV1 extends PatientInfo{

    byte[] qrCode;
    byte[] reserveV1;			//在数据库存储中，会使用此字段

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

    public byte[] getReserveV1() {
        return reserveV1;
    }

    public void setReserveV1(byte[] reserveV1) {
        this.reserveV1 = reserveV1;
    }

    public UserInfoV1() {
        packageType = PackageType.PACKAGE_USERINFOV1;
    }

    @Override
    public void decodeData(ByteBuffer byteBuffer) throws UnsupportedEncodingException {
        super.decodeData(byteBuffer);
        byte[] b42 = new byte[42];
        byteBuffer.get(b42);
        qrCode = b42;
        byte[] b8 = new byte[8];
        byteBuffer.get(b8);
        reserveV1 = b8;
    }
}
