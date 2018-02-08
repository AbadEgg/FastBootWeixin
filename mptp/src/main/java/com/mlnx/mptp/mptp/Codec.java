package com.mlnx.mptp.mptp;

import java.nio.ByteBuffer;

public interface Codec {

	void decode(ByteBuffer buf);

	byte[] encode();

	void init();
}
