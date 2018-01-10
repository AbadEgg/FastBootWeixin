package com.mlnx.device_server;

import com.alicloud.openservices.tablestore.ClientConfiguration;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.AlwaysRetryStrategy;
import com.mlnx.device_server.comm.config.OTSConfig;
import com.mlnx.ecg.store.EcgStore;
import com.mlnx.ecg.store.iml.EcgMongoDb;
import com.mlnx.ecg.store.iml.EcgStoreTable;
import com.mlnx.ecg.store.utils.OTSUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by amanda.shan on 2017/5/16.
 */
@Configuration
public class BeanConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OTSConfig otsConfig;

    @Value("${ecgSaveType}")
    private Integer ecgSaveType;    //  0 表格存储   1 mongodb存储

    @Bean
    public SyncClient syncClient() {

        if (ecgSaveType == 0){
            // ClientConfiguration提供了很多配置项，以下只列举部分。
            ClientConfiguration clientConfiguration = new ClientConfiguration();
            // 设置建立连接的超时时间。
            clientConfiguration.setConnectionTimeoutInMillisecond(5000);
            // 设置socket超时时间。
            clientConfiguration.setSocketTimeoutInMillisecond(5000);
            // 设置重试策略，若不设置，采用默认的重试策略。
            clientConfiguration.setRetryStrategy(new AlwaysRetryStrategy());
            SyncClient client = new SyncClient(otsConfig.getEndPoint(), otsConfig.getAccessKeyId(), otsConfig
                    .getAccessKeySecret(), otsConfig.getInstanceName(), clientConfiguration);

            List<String> strings =  OTSUtils.listTableWithFuture(client);

            logger.info("阿里table表列表");
            for (int i = 0; i < strings.size(); i++) {
                logger.info(strings.get(i));
            }

            return client;
        }else{
            return null;
        }
    }


    @Bean
    public EcgStore ecgStore(SyncClient syncClient) {

        EcgStore ecgStore = null;

        if (ecgSaveType == 0){
            EcgStoreTable ecgStoreTable = new EcgStoreTable();
            ecgStoreTable.setClient(syncClient);
            ecgStoreTable.init();
            ecgStore = ecgStoreTable;
        }else{
            EcgMongoDb ecgMongoDb = new EcgMongoDb();
            ecgMongoDb.init();
            ecgStore = ecgMongoDb;
        }

        return ecgStore;
    }
}
