package com.mlnx.local.data.store.bp;

import com.cybermkd.mongo.kit.MongoQuery;
import com.mlnx.local.data.config.MlnxDataMongoConfig;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by amanda.shan on 2018/2/24.
 */
public class BpStore {

    public boolean saveBp(int sbp, int dbp, int heart, long time){
        MongoQuery query = new MongoQuery();
        query.use(MlnxDataMongoConfig.BP_COLLECTIONNAME);

        Map<String, Object> map = new HashMap<>();
        map.put("time", time);
        map.put("dbp", dbp);
        map.put("sbp", sbp);
        map.put("heart", heart);

        return query.set(new Document(map)).save();
    }
}
