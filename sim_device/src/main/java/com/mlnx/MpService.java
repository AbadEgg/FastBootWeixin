package com.mlnx;

import com.mlnx.client.MpClientLis;
import com.mlnx.mptp.ResponseCode;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.utils.MptpLogUtils;
import com.mlnx.support.ListenManager;

import java.util.List;

/**
 * @author fzh
 * @create 2018/3/26 17:32
 */
public class MpService implements MpClientLis {

    private MpClient mpClient;

    private boolean isRegister;


    private List<String> deviceIds;

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public MpService() {
        mpClient = new MpClient(this);
        ListenManager.getInstance().setMpClientLis(this);
    }

    public void register(){
        for (String deviceId:deviceIds) {
            mpClient.register(deviceId);
        }
    }


    public void push(MpPacket packet) {
        if (isRegister) {

            mpClient.push(packet);
            MptpLogUtils.i("发送push包");
        } else {
//            register();
        }
    }

    @Override
    public void receive(MpPacket mpPacket) {
        switch (mpPacket.getHeader().getPacketType()) {
            case Reg_ACK:
                if (mpPacket.getBody().getResponseCode().equals(ResponseCode.SUCESS)) {
                    isRegister = true;
                    MptpLogUtils.i("注册成功");
                }
                break;
            case PINGREQ:
                mpClient.pong();
                MptpLogUtils.i("响应pong包");
                break;
            case PINGRESP:
                MptpLogUtils.d("收到pong包");
                break;
            case PUBLISH:

                break;
            case PUBLISH_ACK:
                break;
            default:
                break;
        }
    }

    @Override
    public void sendError() {
        mpClient.disConnect();
    }

    @Override
    public void close() {
        isRegister = false;
    }
}
