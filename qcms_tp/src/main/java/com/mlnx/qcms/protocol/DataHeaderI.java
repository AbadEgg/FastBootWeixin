package com.mlnx.qcms.protocol;

import com.mlnx.qcms.protocol.body.PackageType;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public interface DataHeaderI {

    void setPackageType(PackageType packageType);

    void setChecksum(int checksum);

    void setPackageLength(int packageLength);

    void decodeData(ByteBuffer buf) throws UnsupportedEncodingException;

    byte[] encodeData();

}
