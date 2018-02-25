package com.mlnx.local.data.store;

import com.cybermkd.mongo.kit.MongoKit;
import com.cybermkd.mongo.plugin.MongoPlugin;
import com.mlnx.local.data.config.MlnxDataMongoConfig;
import com.mongodb.MongoClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by amanda.shan on 2018/2/25.
 */
public class LocalStore {

    private Logger logger = LoggerFactory.getLogger(LocalStore.class);

    public void init() {

        logger.info("开始初始化data local store mongodb存储");

        MongoPlugin mongoPlugin = new MongoPlugin();

        mongoPlugin.setDebug(false);
        mongoPlugin.add(MlnxDataMongoConfig.HOST, MlnxDataMongoConfig.PORT);
        mongoPlugin.setDatabase(MlnxDataMongoConfig.DATABASE);
        mongoPlugin.auth(MlnxDataMongoConfig.USER, MlnxDataMongoConfig.PWD);
        MongoClient client = mongoPlugin.getMongoClient();
        MongoKit.INSTANCE.init(client, mongoPlugin.getDatabase());

        logger.info("初始化data local store mongodb存储结束");
    }

}
