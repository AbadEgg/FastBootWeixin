package com.mlnx.ecg.store;

import com.alibaba.fastjson.JSON;
import com.mlnx.ecg.store.domain.Ecg;
import com.mlnx.ecg.store.iml.EcgMongoDb;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by amanda.shan on 2018/3/30.
 */
public class ReadMongodb {

    public static void main(String[] args) throws Exception {
        EcgMongoDb ecgMongoDb = new EcgMongoDb();
        ecgMongoDb.init();
//
//        System.out.println(ecgMongoDb.count());
//        logger.error("sbdhbdsbh");

//        ecgMongoDb.save(getSimEcgs());

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//
//        long startTime = format.parse("2018 03 27 12 38 56").getTime();
//        long endTime = startTime + 60 * 1000;

        // 2018/03/30 13:20
        // 2018/03/30 13:21

        long startTime = format.parse("2018/03/30 13:20:00").getTime();
        long endTime = format.parse("2018/03/30 13:21:00").getTime();


//        List<Map<String, Object>> ecgMongs = ecgMongoDb.getEcgData(startTime, 1, 3, "filterData");
        List<Map<String, Object>> ecgMongs = ecgMongoDb.getEcgData(startTime, endTime, 4, "encryData");
        System.out.println("ecgMongs.size:" + ecgMongs.size());
        System.out.println("滤波数据");

        System.out.println("startTime:" + new Date(startTime));
        System.out.println("endTime:" + new Date(endTime));

//        System.out.println(JSON.toJSONString(ecgMongs));
//
        List<Ecg> ecgs = JSON.parseArray(JSON.toJSONString(ecgMongs), Ecg.class);


        File file = new File("f:/ecgData.txt");
        if (file.exists()) {
            file.delete();
        }
        Writer writer = new FileWriter(file);
        writer.write(JSON.toJSONString(ecgMongs));

        writer.flush();

        System.out.println();

//        for (int i = 0; i < ecgMongs.size(); i++) {
//
////            byte[] bytes = (byte[]) ecgMongs.get(i).get("data");
//
//            Ecg ecg = JsonUtils.parseObject(ecgMongs.get(i)+"", Ecg.class);
//            System.out.println(ecgMongs.get(i));
//
//            System.out.println(ecg);
//        }

    }
}
