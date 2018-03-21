package com.mlnx.local.data.store.device;

import com.alibaba.fastjson.JSONObject;
import com.cybermkd.mongo.kit.MongoQuery;
import com.mlnx.local.data.config.MlnxDataMongoConfig;
import com.mlnx.local.data.domain.DeviceOnlineRecord;
import com.mlnx.local.data.utils.BeanUtils;
import com.mlnx.local.data.utils.DateUtils;
import com.mlnx.mptp.utils.MptpLogUtils;
import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public Set<Date> get(Integer year, Integer month, Integer patientId){
        Set<Date> records = new HashSet<>();
        try {
            Date startTime = DateUtils.getFirstDayOfMonth(year,month);
            Date endTime = DateUtils.getFirstDayOfMonth(year,month+1);
            List<DeviceOnlineRecord> resultDatas = group(get(startTime,endTime,patientId));
            for (int i = 0; i < resultDatas.size()/2 ; i++) {
                Date onlineDate = resultDatas.get(2*i).getDate();
                Date offlineDate = resultDatas.get(2*i+1).getDate();
                records.add(DateUtils.formatDate(onlineDate));
                if(DateUtils.getFutureDate(onlineDate,1).before(offlineDate)){
                    records.add(DateUtils.getFutureDate(onlineDate,1));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            MptpLogUtils.e("日期解析错误:",e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    public List<DeviceOnlineRecord> group(List<DeviceOnlineRecord> list){
        List<DeviceOnlineRecord> listOn = new ArrayList<DeviceOnlineRecord>();
        List<DeviceOnlineRecord> listOff = new ArrayList<DeviceOnlineRecord>();
        List<DeviceOnlineRecord> listAll = new ArrayList<DeviceOnlineRecord>();

        DeviceOnlineRecord onDor = new DeviceOnlineRecord();
        DeviceOnlineRecord offDor = new DeviceOnlineRecord();
        try {
            for (DeviceOnlineRecord dor :list){
                SimpleDateFormat time=new SimpleDateFormat("yyyy MM dd HH mm ss");
                if(dor.getDeviceState().equals("DEVICE_ONLINE")){
                    listOn.add(dor);

                }else{
                    listOff.add(dor);
                }
            }
            for(int i=0 ;i<listOn.size();i++){
                if(i==0){
                    onDor= listOn.get(i);
                    offDor = listOff.get(i);
                    listAll.add(onDor);
                    listAll.add(offDor);
                }
                if(listOn.get(i).getDate().after(offDor.getDate())){
                    onDor=listOn.get(i);
                    for(int j=1;j<listOff.size();j++){
                        if(listOff.get(j).getDate().after(onDor.getDate())){
                            offDor = listOff.get(j);
                            listAll.add(onDor);
                            listAll.add(offDor);
                            break;
                        }
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAll;
    }

}
