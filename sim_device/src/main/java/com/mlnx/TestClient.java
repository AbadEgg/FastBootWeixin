package com.mlnx;


import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.model.ECGDeviceInfo;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.body.ecg.EcgBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TestClient {

    private MpService testUsr;

    public TestClient() {
        testUsr = new MpService();
        List<String> deviceIds = new ArrayList<>();
        for (int i = 1; i <= 1; i++) {
            String deviceId = "SIMECG000"+i;
            deviceIds.add(deviceId);
        }
        testUsr.setDeviceIds(deviceIds);
    }


    public static void main(String[] args) throws InterruptedException {
        final TestClient testClient = new TestClient();

        testClient.testUsr.register();

        final Random random = new Random();
        Thread.sleep(2000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                    EcgBody ecgBody = new EcgBody();
                    ecgBody.init();
                    ECGDeviceInfo ecgDeviceInfo = new ECGDeviceInfo();
                    ecgDeviceInfo.setBatteryLevel(random.nextInt(10));
                    ecgDeviceInfo.setSignalStrength(random.nextInt(10));
                    ecgBody.setEcgDeviceInfo(ecgDeviceInfo);

                    Body body = new Body();
                    body.init();
                    body.setDeviceId("SIMECG0001");
                    body.setPacketTime(System.currentTimeMillis());
                    body.setEcgBody(ecgBody);

                    MpPacket packet = new MpPacket().push(DeviceType.ECG_DEVICE, body);

                    testClient.testUsr.push(packet);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
