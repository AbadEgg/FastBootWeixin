package com.mlnx.qcms.protocol;

import com.mlnx.qcms.protocol.body.Body;
import com.mlnx.qcms.protocol.config.TpConfig;
import com.mlnx.qcms.protocol.head.Header;

import java.nio.ByteBuffer;

/**
 * 推送的数据包
 *
 * @author fzh
 * @create 2018/1/23 9:09
 */
public class DataPacket {

    private Header header;
    private Body body;

    private static final ByteBuffer buffer = ByteBuffer
            .allocate(TpConfig.MP_PACKET_CAPABILITY);

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public DataPacket() {
        this.header = new Header();
        this.body = new Body();
    }

    public DataPacket(Header header, Body body) {
        this.header = header;
        this.body = body;
    }

    public byte[] encode(){
        synchronized (DataPacket.class) {
            buffer.clear();

            buffer.put(header.encode());
            buffer.put(body.encode());
            buffer.flip();
            byte[] bs = new byte[buffer.remaining()];
            buffer.get(bs);

            return bs;
        }
    }
}
