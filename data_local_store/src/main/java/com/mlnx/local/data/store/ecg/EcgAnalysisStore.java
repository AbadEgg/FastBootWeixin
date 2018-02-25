package com.mlnx.local.data.store.ecg;

import com.cybermkd.mongo.kit.MongoQuery;
import com.mlnx.local.data.config.MlnxDataMongoConfig;
import com.mlnx.mptp.model.analysis.HeartResult;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by amanda.shan on 2018/2/25.
 */
public class EcgAnalysisStore {

    /**
     * 保存心电实时分析结果
     *
     * @param time        时间
     * @param heart       心率
     * @param pbNumb      早搏个数
     * @param heartResult 心率分析结果
     */
    public boolean save(long time, Integer heart, Integer pbNumb, HeartResult heartResult) {
        MongoQuery query = new MongoQuery();
        query.use(MlnxDataMongoConfig.ECG_REAL_ANALY_COLLECTIONNAME);

        Map<String, Object> map = new HashMap<>();
        map.put("time", time);
        if (heart != null) {
            map.put("heart", heart);
        }
        if (pbNumb != null) {
            map.put("pbNumb", pbNumb);
        }
        if (heartResult != null) {
            map.put("heartResult", heartResult.getCode());
        }

        return query.set(new Document(map)).save();
    }
}
