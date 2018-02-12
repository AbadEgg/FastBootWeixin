package com.mlnx.qcms.protocol.body;

import java.nio.ByteBuffer;

/**
 * @author fzh
 * @create 2018/1/24 11:05
 */
public class ConnectionInfo extends DataHeader{

    String ipAddr;			//ip地址
    int port;				//端口

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ConnectionInfo() {
        packageType = PackageType.PACKAGE_CONNECT_INFO;
    }

    @Override
    public void decodeData(ByteBuffer buf) {

    }
}
