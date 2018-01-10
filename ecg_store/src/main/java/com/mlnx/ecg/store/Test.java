package com.mlnx.ecg.store;

import com.alicloud.openservices.tablestore.ClientConfiguration;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.AlwaysRetryStrategy;
import com.mlnx.ecg.store.config.EcgTableConfig;
import com.mlnx.ecg.store.iml.EcgStoreTable;
import com.mlnx.mptp.model.Ecg;

import java.util.List;

/**
 * Created by amanda.shan on 2018/1/10.
 */
public class Test {

    public static void main(String[] args) {

        EcgStoreTable table = new EcgStoreTable();
        table.setClient(getSyncClient());
        table.init();
        List<Ecg> ecgs = table.getEcg(System.currentTimeMillis() - 2000, System.currentTimeMillis() - 1000, 10000);

        System.out.println("ecgs.size" + ecgs.size());
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
