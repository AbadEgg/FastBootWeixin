package com.mlnx.ecg.store.iml;

import com.cybermkd.mongo.kit.MongoKit;
import com.cybermkd.mongo.kit.MongoQuery;
import com.cybermkd.mongo.kit.index.MongoIndex;
import com.cybermkd.mongo.plugin.MongoPlugin;
import com.mlnx.ecg.store.DeviceStore;
import com.mlnx.ecg.store.config.MlnxDataMongoConfig;
import com.mlnx.ecg.store.domain.DeviceOnlineRecord;
import com.mlnx.ecg.store.utils.BeanUtils;
import com.mongodb.MongoClient;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fzh
 * @create 2018/3/13 16:31
 */
public class DeviceMongoDb implements DeviceStore {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init() {
        logger.info("开始初始化mongodb存储");

        MongoPlugin mongoPlugin = new MongoPlugin();

        mongoPlugin.setDebug(false);
        mongoPlugin.add(MlnxDataMongoConfig.HOST, MlnxDataMongoConfig.PORT);
        mongoPlugin.setDatabase(MlnxDataMongoConfig.DATABASE);
        mongoPlugin.auth(MlnxDataMongoConfig.USER, MlnxDataMongoConfig.PWD);
        MongoClient client = mongoPlugin.getMongoClient();
        MongoKit.INSTANCE.init(client, mongoPlugin.getDatabase());

        MongoIndex index = new MongoIndex(MlnxDataMongoConfig.DEVICE_COLLECTIONNAME);
        index.add(new MongoIndex().ascending("deviceId", "date").setUnique(true)).compound(); // 组合索引

        logger.info("初始化mongodb存储结束");
    }

    @Override
    public boolean save(DeviceOnlineRecord deviceOnlineRecord) {
        MongoQuery query = new MongoQuery();
        query.use(MlnxDataMongoConfig.DEVICE_COLLECTIONNAME);
        return query.set(new Document(BeanUtils.toMap(deviceOnlineRecord, false))).save();
    }
}
