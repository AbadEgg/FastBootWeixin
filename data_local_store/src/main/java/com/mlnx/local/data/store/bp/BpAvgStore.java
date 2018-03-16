package com.mlnx.local.data.store.bp;

import com.alibaba.fastjson.JSONObject;
import com.cybermkd.mongo.kit.MongoQuery;
import com.mlnx.local.data.config.MlnxDataMongoConfig;
import com.mlnx.local.data.domain.BpAvg;
import com.mlnx.local.data.utils.BeanUtils;
import com.mlnx.local.data.utils.DateUtils;
import org.bson.Document;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     * 获取最近一周的日均血压数据
     * @return
     */
    public List<BpAvg> getLastWeekBpAvg(Integer patientId) {
        try {
            return getBpAvg(DateUtils.getPastDate(7),DateUtils.getPastDate(0),patientId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取上上周的日均血压数据
     * @return
     */
    public List<BpAvg> getLastLastWeekBpAvg(Integer patientId) throws Exception {
        return getBpAvg(DateUtils.getPastDate(14),DateUtils.getPastDate(7),patientId);
    }

    public List<BpAvg> getBpAvg(Date startTime, Date endTime, Integer patientId){
        MongoQuery query = new MongoQuery();
        query.use(MlnxDataMongoConfig.BP_AVG_COLLECTIONNAME);
        query.eq("patientId", patientId);
        query.gte("dayTime", startTime);
        query.lt("dayTime", endTime);
        query.ascending("dayTime");
        List<JSONObject> jsonObjects = query.find();
        List<BpAvg> bpAvgs = new ArrayList<>();
        try {
            for (JSONObject jsonObject:jsonObjects) {
                BpAvg bpAvg = null;
                bpAvg = (BpAvg) BeanUtils.mapToBean(jsonObject,BpAvg.class);
                bpAvgs.add(bpAvg);
            }
            return bpAvgs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BpAvg calcBpAvg(List<BpAvg> list){
        if (list != null && list.size() > 0) {
            BpAvg result = new BpAvg();
            int sumSbp = 0;
            int sumDbp = 0;
            int sumHeart = 0;

            int daySumSbp = 0;
            int daySumDbp = 0;
            int daySumHeart = 0;

            int nightSumSbp = 0;
            int nightSumDbp = 0;
            int nightSumHeart = 0;

            for (BpAvg bpAvg :list) {
                sumSbp += bpAvg.getSystolicAvg();
                sumDbp += bpAvg.getDiastolicAvg();
                sumHeart += bpAvg.getHeartAvg();

                daySumSbp += bpAvg.getSystolicDayAvg();
                daySumDbp += bpAvg.getDiastolicDayAvg();
                daySumHeart += bpAvg.getHeartDayAvg();

                nightSumSbp += bpAvg.getSystolicNightAvg();
                nightSumDbp += bpAvg.getDiastolicNightAvg();
                nightSumHeart += bpAvg.getHeartNightAvg();
            }
            result.setDiastolicAvg(sumDbp / list.size());
            result.setSystolicAvg(sumSbp / list.size());
            result.setHeartAvg(sumHeart / list.size());
            result.setSystolicDayAvg(daySumSbp / list.size());
            result.setSystolicNightAvg(nightSumSbp / list.size());
            result.setDiastolicDayAvg(daySumDbp / list.size());
            result.setDiastolicNightAvg(nightSumDbp / list.size());
            result.setHeartDayAvg(daySumHeart / list.size());
            result.setHeartNightAvg(nightSumHeart / list.size());
            return result;
        }else {
            return null;
        }
    }
}
