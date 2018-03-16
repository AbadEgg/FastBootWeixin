package com.mlnx.local.data.store.device;

import com.alibaba.fastjson.JSONObject;
import com.cybermkd.mongo.kit.MongoQuery;
import com.mlnx.local.data.config.MlnxDataMongoConfig;
import com.mlnx.local.data.domain.DeviceOnlineRecord;
import com.mlnx.local.data.utils.BeanUtils;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeviceStore {

    public boolean save(DeviceOnlineRecord deviceOnlineRecord) {
        MongoQuery query = new MongoQuery();
        query.use(MlnxDataMongoConfig.DEVICE_COLLECTIONNAME);
        return query.set(new Document(BeanUtils.toMap(deviceOnlineRecord, false))).save();
    }

    public List<DeviceOnlineRecord> get(Date startTime, Date endTime,Integer patientId) throws Exception {
        List<DeviceOnlineRecord> records = new ArrayList<>();
        MongoQuery query = new MongoQuery();
        query.use(MlnxDataMongoConfig.DEVICE_COLLECTIONNAME);
        query.eq("patientId",patientId);
        query.gt("date",startTime);
        query.lt("date",endTime);
        List<JSONObject> results = query.find();
        for (JSONObject result:results) {
            DeviceOnlineRecord deviceOnlineRecord = (DeviceOnlineRecord) BeanUtils.mapToBean(result,DeviceOnlineRecord.class);
            records.add(deviceOnlineRecord);
        }
        return records;
    }

}
