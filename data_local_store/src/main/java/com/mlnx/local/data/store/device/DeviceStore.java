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
            Date current = new Date();
            if(endTime.after(current)){
                endTime = current;
            }
            List<DeviceOnlineRecord> resultDatas = group(get(startTime,endTime,patientId),startTime,endTime);
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

    public List<DeviceOnlineRecord> group(List<DeviceOnlineRecord> list,Date startTime, Date endTime){
        List<DeviceOnlineRecord> listOn = new ArrayList<DeviceOnlineRecord>();
        List<DeviceOnlineRecord> listOff = new ArrayList<DeviceOnlineRecord>();
        List<DeviceOnlineRecord> listAll = new ArrayList<DeviceOnlineRecord>();

        DeviceOnlineRecord onDor = new DeviceOnlineRecord();
        DeviceOnlineRecord offDor = new DeviceOnlineRecord();
        DeviceOnlineRecord deviceOnlineRecord=new DeviceOnlineRecord();

        try {
            SimpleDateFormat time=new SimpleDateFormat("yyyy MM dd HH mm ss");
            for (DeviceOnlineRecord dor :list){
                if(dor.getDeviceState().equals("DEVICE_ONLINE")){
                    listOn.add(dor);

                }else{
                    listOff.add(dor);
                }
            }
            if(listOff.size()==0 && listOn.size()!=0){
                onDor=listOn.get(0);
                deviceOnlineRecord.setDate(endTime);
                deviceOnlineRecord.setDeviceState("DEVICE_OFFLINE");
                deviceOnlineRecord.setDeviceId(onDor.getDeviceId());
                deviceOnlineRecord.setPatientId(onDor.getPatientId());
                listAll.add(onDor);
                listAll.add(deviceOnlineRecord);

            }else if(listOn.size()==0 && listOff.size()!=0){
                offDor=listOff.get(0);
                deviceOnlineRecord.setDate(startTime);
                deviceOnlineRecord.setDeviceState("DEVICE_ONLINE");
                deviceOnlineRecord.setDeviceId(offDor.getDeviceId());
                deviceOnlineRecord.setPatientId(offDor.getPatientId());
                listAll.add(offDor);
                listAll.add(deviceOnlineRecord);
            }else if(listOn.size()==0 && listOff.size()==0){
            }else{
                for(int i=0 ;i<listOn.size();i++){
                    if(i==0){
                        if(listOn.get(i).getDate().getTime()<(listOff.get(i).getDate().getTime())){
                            onDor=listOn.get(i);
                            offDor = listOff.get(i);
                            listAll.add(onDor);
                            listAll.add(offDor);
                        }else{
                            onDor=listOn.get(i);
                            deviceOnlineRecord.setDeviceId(listOn.get(i).getDeviceId());
                            deviceOnlineRecord.setPatientId(listOn.get(i).getPatientId());
                            deviceOnlineRecord.setDeviceState(listOn.get(i).getDeviceState());
                            deviceOnlineRecord.setDate(startTime);
                            offDor = listOff.get(i);
                            listAll.add(deviceOnlineRecord);
                            listAll.add(offDor);
                            for(int j=0;j<listOff.size();j++){
                                if(listOff.get(j).getDate().getTime()>=(onDor.getDate().getTime())){
                                    offDor = listOff.get(j);
                                    listAll.add(onDor);
                                    listAll.add(offDor);
                                    break;
                                }
                            }

                        }

                    }
                    if(listOn.get(i).getDate().getTime()>=(offDor.getDate().getTime())){
                        onDor=listOn.get(i);
                        for(int j=0;j<listOff.size();j++){
                            if(listOff.get(j).getDate().getTime()>(onDor.getDate().getTime())){
                                offDor = listOff.get(j);
                                listAll.add(onDor);
                                listAll.add(offDor);
                                break;
                            }
                        }
                    }


                }
                for(int m=0;m<listOn.size();m++){//最后是上线设备没有下线
                    if(listOn.get(m).getDate().getTime()>=listAll.get(listAll.size()-1).getDate().getTime()){
                        onDor=listOn.get(m);
                        offDor =listOff.get(0);
                        offDor.setDate(endTime);
                        listAll.add(onDor);
                        listAll.add(offDor);
                        break;
                    }
                }
            }
            if(listAll.size()==2){//只有一组上下线数据取下线设备的最后时间
                offDor= listAll.get(1);
                for(int j=0;j<listOff.size();j++){
                    if(listOff.get(j).getDate().after(offDor.getDate()) && listOff.get(j).getDate().before(startTime)){
                        offDor = listOff.get(j);
                    }
                }
                listAll.set(1, offDor);
            }
            for(int k=0;k<listAll.size();k=k+2){//取下线设备的最后时间
                offDor= listAll.get(k+1);
                for(int j=0;j<listOff.size();j++){
                    if(listOff.get(j).getDate().after(offDor.getDate()) && listOff.get(j).getDate().before(listAll.get(k+2).getDate())){
                        offDor = listOff.get(j);
                    }
                }
                listAll.set(k+1, offDor);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAll;
    }

}
