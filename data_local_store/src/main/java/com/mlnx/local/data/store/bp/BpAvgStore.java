package com.mlnx.local.data.store.bp;

import com.cybermkd.mongo.kit.MongoQuery;
import com.mlnx.local.data.config.MlnxDataMongoConfig;
import com.mlnx.local.data.domain.BpAvg;
import com.mlnx.local.data.utils.BeanUtils;
import org.bson.Document;

/**
 * @author fzh
 * @create 2018/3/14 11:23
 */
public class BpAvgStore {

    public boolean saveBpAvg(BpAvg bpAvg){
        MongoQuery query = new MongoQuery();
        query.use(MlnxDataMongoConfig.BP_AVG_COLLECTIONNAME);
        return query.set(new Document(BeanUtils.toMap(bpAvg,false))).save();
    }
}
