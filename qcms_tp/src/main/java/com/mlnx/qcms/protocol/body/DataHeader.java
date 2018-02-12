package com.mlnx.qcms.protocol.body;

import com.mlnx.qcms.protocol.Codec;
import com.mlnx.qcms.protocol.DataHeaderI;
import com.mlnx.qcms.protocol.config.TpConfig;
import com.mlnx.qcms.utils.ByteUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * @author fzh
 * @create 2018/1/25 9:50
 */
public abstract class DataHeader implements DataHeaderI{

    PackageType packageType;		//包类型
    int checksum;			//校验核
    int packageLength;	//包长

    public PackageType getPackageType() {
        return packageType;
    }

    @Override
    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public int getChecksum() {
        return checksum;
    }

    @Override
    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }

    public int getPackageLength() {
        return packageLength;
    }

    @Override
    public void setPackageLength(int packageLength) {
        this.packageLength = packageLength;
    }

    @Override
    public byte[] encodeData() {
        return new byte[0];
    }

    @Override
    public void decodeData(ByteBuffer buf) throws UnsupportedEncodingException {

    }
}
