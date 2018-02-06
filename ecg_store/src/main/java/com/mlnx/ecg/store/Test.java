package com.mlnx.ecg.store;

import com.alicloud.openservices.tablestore.ClientConfiguration;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.AlwaysRetryStrategy;
import com.mlnx.ecg.store.config.EcgTableConfig;
import com.mlnx.ecg.store.utils.Base64Utils;

/**
 * Created by amanda.shan on 2018/1/10.
 */
public class Test {

    public static void main(String[] args) {

//        EcgStoreTable table = new EcgStoreTable();
//        table.setClient(getSyncClient());
//        table.init();
//        List<Map<String, Object>> ecgs = table.getEcg(System.currentTimeMillis() - 6000, System.currentTimeMillis() , 10000);
//
//        System.out.println("ecgs.size" + ecgs.size());
//
//        for (Map<String, Object> ecg : ecgs){
//            System.out.println(ecg.toString());
//        }

//        byte b = Integer.valueOf("198").byteValue();
//        System.out.println(String.format("0x%x", b));
//        System.out.println(String.format("0x%x", Integer.valueOf("198")));


//        System.out.println(BeanUtils.toMap(new Ecg()));

//        for (int j = 0; j < bytes.length; j++) {
//            bytes[j] = (byte) j;
//        }

        for (int i = 1000; i < 100000; i+=1000) {

            int len = Base64Utils.enc(new byte[i]).getBytes().length;

            System.out.println(String.format("%5d, %5d, %5d", i, len, len*100/i));
        }
    }

    private static SyncClient getSyncClient() {

        // ClientConfiguration提供了很多配置项，以下只列举部分。
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        // 设置建立连接的超时时间。
        clientConfiguration.setConnectionTimeoutInMillisecond(5000);
        // 设置socket超时时间。
        clientConfiguration.setSocketTimeoutInMillisecond(5000);
        // 设置重试策略，若不设置，采用默认的重试策略。
        clientConfiguration.setRetryStrategy(new AlwaysRetryStrategy());
        SyncClient client = new SyncClient(EcgTableConfig.endPoint, EcgTableConfig.accessKeyId, EcgTableConfig
                .accessKeySecret, EcgTableConfig.instanceName, clientConfiguration);

        return client;

    }

}
