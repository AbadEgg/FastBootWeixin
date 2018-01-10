package com.mlnx.mptp.mptp.body.config;

import com.mlnx.mptp.mptp.InvalidPacketException;
import com.mlnx.mptp.mptp.body.GroupType;
import com.mlnx.mptp.mptp.config.MptpConfig;
import com.mlnx.mptp.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by amanda.shan on 2017/6/7.
 */
public class ConfigBody {

    private static final ByteBuffer buffer = ByteBuffer
            .allocate(MptpConfig.CONFIG_BODY_CAPABILITY);

    private String wifiSSid;
    private String wifiPassword;
    private Byte wifiChannel;
    private byte[] serverIp;
    private Integer serverPort;
    private Byte heartChannel;

    public Byte getHeartChannel() {
        return heartChannel;
    }

    public void setHeartChannel(Byte heartChannel) {
        this.heartChannel = heartChannel;
    }

    public String getWifiSSid() {
        return wifiSSid;
    }

    public void setWifiSSid(String wifiSSid) {
        this.wifiSSid = wifiSSid;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }

    public Byte getWifiChannel() {
        return wifiChannel;
    }

    public void setWifiChannel(Byte wifiChannel) {
        this.wifiChannel = wifiChannel;
    }

    public byte[] getServerIp() {
        return serverIp;
    }

    public void setServerIp(byte[] serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public void decodeData(GroupType groupType, ByteBuffer frame,
                           String dataType, int dataLen) throws InvalidPacketException {

    }

    public byte[] encode() {

        buffer.clear();

        if (wifiSSid != null) {
            buffer.put(GroupType.WIFI_SSID.getEncodes());
            buffer.put(new String("CS").getBytes());
            byte[] bs = wifiSSid.getBytes(Charset.forName("US-ASCII"));
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }
        if (wifiPassword != null) {
            buffer.put(GroupType.WIFI_PASSWORD.getEncodes());
            buffer.put(new String("CS").getBytes());
            byte[] bs = wifiPassword.getBytes(Charset.forName("US-ASCII"));
            buffer.put((byte) bs.length);
            buffer.put(bs);
        }
        if (wifiChannel != null) {
            buffer.put(GroupType.WIFI_CHANNEL.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put(wifiChannel);
        }
        if (serverIp != null) {
            buffer.put(GroupType.SERVER_IP.getEncodes());
            buffer.put(new String("UL").getBytes());
            buffer.put((byte) 4);
            buffer.put(serverIp);
        }

        if (serverPort != null) {
            buffer.put(GroupType.SERVER_PORT.getEncodes());
            buffer.put(new String("US").getBytes());
            buffer.put((byte) 2);
            byte[] bytes = ByteUtils.intToBytes(serverPort, 2);
            buffer.put(bytes);
        }

        if (heartChannel != null) {
            buffer.put(GroupType.HEART_CHANNEL.getEncodes());
            buffer.put(new String("BT").getBytes());
            buffer.put((byte) 1);
            buffer.put(heartChannel);
        }

        buffer.flip();
        byte[] bs = new byte[buffer.remaining()];
        buffer.get(bs);
        return bs;
    }
}
