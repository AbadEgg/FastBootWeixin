package com.mlnx.mptp.mptp;

import java.nio.ByteBuffer;

public interface Codec {

	public void decode(ByteBuffer buf);

	public byte[] encode();
}
