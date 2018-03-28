package com.mlnx;


import com.mlnx.mptp.DeviceType;
import com.mlnx.mptp.model.ECGData;
import com.mlnx.mptp.model.ECGDeviceInfo;
import com.mlnx.mptp.mptp.MpPacket;
import com.mlnx.mptp.mptp.body.Body;
import com.mlnx.mptp.mptp.body.ecg.EcgBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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


    public static List<byte[]> readFile(File file, String charset){
        List<byte[]> list = new ArrayList<>();
        //设置默认编码
        if(charset == null){
            charset = "UTF-8";
        }

        if(file.isFile() && file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String text = null;
                while((text = bufferedReader.readLine()) != null){
                    sb.append(text);
                }
                String result = sb.toString().substring(1,sb.toString().length()-1);
                String[] array = result.split(",");
                for (int i = 0; i < array.length/24 ; i++) {
                    byte[] data = new byte[24];
                    for (int j = 0; j < 24; j++) {
                        data[j] = Byte.parseByte(array[i*24+j]);
                    }
                    list.add(data);
                }
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }

    public static void main(String[] args) throws InterruptedException {
        final TestClient testClient = new TestClient();

        testClient.testUsr.register();

        final Random random = new Random();
        Thread.sleep(2000);
        for (int i = 0; i < 1; i++) {
            final int j = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<byte[]> list = readFile(new File("U:/ecgData.txt"),null);
                    long sTime = 0L;
                    long eTime = 0L;
                    long diff = 500L;
                    int hasRead = 0;
                    int read = 0;
                    while (true){
                        sTime = System.currentTimeMillis();
                        EcgBody ecgBody = new EcgBody();

                        ECGDeviceInfo ecgDeviceInfo = new ECGDeviceInfo();
                        ecgDeviceInfo.setBatteryLevel(random.nextInt(10));
                        ecgDeviceInfo.setSignalStrength(random.nextInt(10));
                        ecgBody.setEcgDeviceInfo(ecgDeviceInfo);

                        ECGData ecgData = new ECGData();
                        read = (int) (diff*500L/1000L);
                        byte[] bs = new byte[0];

                        for (int k = 0; k < read; k++) {
                            bs = addBytes(bs,list.get(hasRead++));
                            if (hasRead >= list.size()){
                                hasRead = 0;
                            }
                        }

                        ecgData.setSuccessionData(bs);
                        ecgBody.setEcgData(ecgData);

                        Body body = new Body();
                        body.init();
                        body.setDeviceId("SIMECG000"+(j+1));
                        body.setPacketTime(System.currentTimeMillis());
                        body.setEcgBody(ecgBody);

                        MpPacket packet = new MpPacket().push(DeviceType.ECG_DEVICE, body);

                        testClient.testUsr.push(packet);

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        eTime = System.currentTimeMillis();
                        diff = eTime - sTime;
                    }
                }
            }).start();
        }

    }
}
