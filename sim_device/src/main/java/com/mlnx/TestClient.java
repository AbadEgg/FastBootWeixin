package com.mlnx;


import com.mlnx.mptp.model.ECGDeviceInfo;
import com.mlnx.mptp.mptp.body.ecg.EcgBody;
import com.mlnx.mptp.push.body.PushDataType;

import java.util.*;


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
                    ECGDeviceInfo ecgDeviceInfo = new ECGDeviceInfo();
                    ecgDeviceInfo.setBatteryLevel(random.nextInt(10));
                    ecgDeviceInfo.setSignalStrength(random.nextInt(10));
                    ecgBody.setEcgDeviceInfo(ecgDeviceInfo);
                    Map<PushDataType, Object> pushDataMap = new HashMap<>();
                    pushDataMap.put(PushDataType.ECG_INFO, ecgBody);
                    testClient.testUsr.push(pushDataMap);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
