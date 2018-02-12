package com.mlnx.qcms.protocol;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public interface Codec {

	public void decode(ByteBuffer buf) throws UnsupportedEncodingException;

	public byte[] encode();

}
