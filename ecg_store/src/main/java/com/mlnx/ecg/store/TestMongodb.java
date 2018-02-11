package com.mlnx.ecg.store;

import com.alibaba.fastjson.JSON;
import com.mlnx.ecg.store.iml.EcgMongoDb;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by amanda.shan on 2018/2/8.
 */
public class TestMongodb {

    static class  Date{
        byte[] data;

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Date{" +
                    "data=" + Arrays.toString(data) +
                    '}';
        }
    }

    public static void main(String[] args) {
        EcgMongoDb ecgMongoDb = new EcgMongoDb();
        ecgMongoDb.init();

        List<Map<String, Object>> ecgMongs = ecgMongoDb.getEcg(1518060990000L, 1518061005000L, 0);

        System.out.println("ecgMongs.size:" + ecgMongs.size());
        for (int i = 0; i < ecgMongs.size(); i++) {

//            System.out.println(ecgMongs.get(i).toString());

            Date date =  JSON.parseObject(ecgMongs.get(i).toString(), Date.class);
            System.out.println(date.toString());
        }
    }


}
