package com.mlnx;


import com.alibaba.fastjson.JSON;
import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.model.BpResult;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.body.bp.BpBody;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.util.*;


public class TestClient {

    static class Ecg {
        private byte[] data;    // 原始数据

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }
    }

    public static List<byte[]> readFile(File file) throws IOException {
        Reader reader = new FileReader(file);
        StringBuffer buffer = new StringBuffer();
        char[] chars = new char[1000];
        int len = 0;
        while ((len = reader.read(chars)) != -1) {
            buffer.append(chars, 0, len);
        }

        List<Ecg> ecgs = JSON.parseArray(buffer.toString(), Ecg.class);

        List<byte[]> bytes = new ArrayList<>();
        for (Ecg ecg : ecgs) {
            int index = 0;
            while (true) {
                if (ecg.getData().length >= index + 24) {
                    byte[] bytes1 = new byte[24];
                    for (int j = 0; j < bytes1.length; j++) {
                        bytes1[j] = ecg.getData()[index++];
                    }
                    bytes.add(bytes1);
                } else {
                    System.out.println("多余：" + (ecg.getData().length - index));
                    break;
                }
            }
        }

        return bytes;
    }


    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }

    public static void copyBytes(byte[] data1, byte[] data2, int index1) {
        for (int i = 0; i < data2.length; i++) {
            data1[index1++] = data2[i];
        }
    }

    static class PushEcgTimerTask extends TimerTask {

        private String deviceId;
        private MpService testUsr;

        private List<byte[]> list;
        Random random = new Random();

        private int registerDelayCount = 10;
        private Long sendTime = null;
        private int hasRead = 0;

        private ByteBuffer buffer = ByteBuffer.allocate(20000);

        public PushEcgTimerTask(String deviceId, List<byte[]> list) {
            this.deviceId = deviceId;
            this.list = list;
            testUsr = new MpService();
            testUsr.setDeviceId(deviceId);
        }

        @Override
        public void run() {

//            int read = 0;
//
//            if (!testUsr.isRegister()) {
//                registerDelayCount++;
//                if (registerDelayCount >= 10) {
//                    testUsr.register();
//                    registerDelayCount = 0;
//                    System.out.println(String.format("%s %s 发送心电注册包", new Date(), deviceId));
//                }
//            } else {
//                registerDelayCount = 0;
//                if (sendTime == null) {
//                    sendTime = System.currentTimeMillis();
//                } else {
//                    long diff = System.currentTimeMillis() - sendTime;
//                    sendTime = System.currentTimeMillis();
//
//                    EcgBody ecgBody = new EcgBody();
//                    Body body = new Body();
//                    body.init();
//                    body.setDeviceId(deviceId);
//                    body.setPacketTime(System.currentTimeMillis());
//                    body.setEcgBody(ecgBody);
//
//                    ECGDeviceInfo ecgDeviceInfo = new ECGDeviceInfo();
//                    ecgDeviceInfo.setBatteryLevel(random.nextInt(10));
//                    ecgDeviceInfo.setSignalStrength(random.nextInt(10));
//                    ecgBody.setEcgDeviceInfo(ecgDeviceInfo);
//
//                    ECGData ecgData = new ECGData();
//                    read = (int) (diff * 500L / 1000L);
//                    byte[] bs = new byte[read * 24];
//                    int index = 0;
//
//                    for (int k = 0; k < read; k++) {
//
////                        for (int i = 0; i < 8; i++) {
////                            bs[index++] = 0;
////                            bs[index++] = 8;
////                            bs[index++] = 0;
////                        }
//                        copyBytes(bs, list.get(hasRead++), index);
//                        index += 24;
//                        if (hasRead >= list.size()) {
//                            hasRead = 0;
//                        }
//                    }
//
//                    ecgData.setEncrySuccessionData(bs);
//                    ecgBody.setEcgData(ecgData);
//
//                    MpPacket packet = new MpPacket().push(DeviceType.ECG_DEVICE, body);
//
//                    testUsr.push(packet);
//
//                    System.out.println(String.format("%s %s 发送心电数据量:%d", DateUtils.format(System.currentTimeMillis(), "HH:mm:ss:SSS"), deviceId, read));
//                }
//            }
            while (true) {
                BpBody bpBody = new BpBody();
                bpBody.init();
                BpResult bpResult = new BpResult();
                bpResult.setResultDbp(120);
                bpResult.setResultSbp(80);
                bpResult.setResultHeart(60);
                bpBody.setBpResult(bpResult);
                Body body = new Body();
                body.init();
                body.setDeviceId(deviceId);
                body.setPacketTime(System.currentTimeMillis());
                body.setBpBody(bpBody);
                body.setPatientId(7);
                MpPacket packet = new MpPacket().push(DeviceType.ECG_DEVICE, body);

                testUsr.push(packet);


                try {
                    Thread.sleep(5000);
                } catch (Exception e) {

                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
//        List<byte[]> list = readFile(new File("U:/ecgData.txt"));
//
//        for (int i = 11; i < 100; i++) {
//            new Timer().schedule(new PushEcgTimerTask("SIMECG000" + i, list), 0, 500);
//        }

        new Timer().schedule(new PushEcgTimerTask("SIMECG00011", null), 0, 500);

    }
}
