package com.mlnx.ecg.store;

import com.alibaba.fastjson.JSON;
import com.mlnx.ecg.store.iml.EcgMongoDb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by amanda.shan on 2018/2/8.
 */
public class TestMongodb {

    static class Data {
        byte[] data;

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "data=" + Arrays.toString(data) +
                    '}';
        }
    }

    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }

    public static void main(String[] args) throws ParseException {
        EcgMongoDb ecgMongoDb = new EcgMongoDb();
        ecgMongoDb.init();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sTime = sdf.parse("2018-3-28 10:30:00");
        Date eTime = sdf.parse("2018-3-28 10:31:00");
//        List<Map<String, Object>> ecgMongs = ecgMongoDb.getFilterEcg(sTime.getTime() ,eTime.getTime(), 3);
        List<Map<String, Object>> ecgMongs = ecgMongoDb.getFilterEcg(System.currentTimeMillis()-600000 ,System.currentTimeMillis(), 4);
        System.out.println("ecgMongs.size:" + ecgMongs.size());
        byte[] datas = new byte[0];
        for (int i = 0; i < ecgMongs.size(); i++) {
            Data data =  JSON.parseObject(ecgMongs.get(i).toString(), Data.class);
            datas = addBytes(datas,Arrays.copyOfRange(data.getData(),0,data.getData().length-4));
//            System.out.println(JSON.toJSONString(ecgMongs.get(0)));
        }
        save2Txt(datas);

    }

    public static void save2Txt(byte[] datas){
        FileOutputStream fop = null;
        File file;

        try {

            file = new File("U:/ecgData.txt");
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] in = Arrays.toString(datas).getBytes();
            fop.write(in);
            fop.flush();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
