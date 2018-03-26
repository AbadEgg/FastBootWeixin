package com.mlnx.ecg.store.iml;

import com.alibaba.fastjson.JSONObject;
import com.cybermkd.mongo.kit.MongoKit;
import com.cybermkd.mongo.kit.MongoQuery;
import com.cybermkd.mongo.kit.index.MongoIndex;
import com.cybermkd.mongo.plugin.MongoPlugin;
import com.mlnx.device.ecg.ECGChannelType;
import com.mlnx.ecg.store.EcgStore;
import com.mlnx.ecg.store.config.MlnxDataMongoConfig;
import com.mlnx.ecg.store.domain.Ecg;
import com.mlnx.ecg.store.utils.BeanUtils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by amanda.shan on 2017/5/10.
 */
public class EcgMongoDb implements EcgStore {

    private Logger logger = LoggerFactory.getLogger(EcgMongoDb.class);

    public void init() {

        logger.info("开始初始化mongodb存储");

        MongoPlugin mongoPlugin = new MongoPlugin();

        mongoPlugin.setDebug(false);
        mongoPlugin.add(MlnxDataMongoConfig.HOST, MlnxDataMongoConfig.PORT);
        mongoPlugin.setDatabase(MlnxDataMongoConfig.DATABASE);
        mongoPlugin.auth(MlnxDataMongoConfig.USER, MlnxDataMongoConfig.PWD);
        MongoClient client = mongoPlugin.getMongoClient();
        MongoKit.INSTANCE.init(client, mongoPlugin.getDatabase());

        MongoIndex index = new MongoIndex(MlnxDataMongoConfig.ECG_COLLECTIONNAME);
        index.add(new MongoIndex().ascending("patientId", "startTime").setUnique(true)).compound(); // 组合索引
//        index.deleteAll();        // 删除所有索引
//        index.ascending("patientId").save();  // 保存索引

        logger.info("初始化mongodb存储结束");
    }

    @Override
    public boolean save(List<Ecg> ecgs) {

        MongoQuery query = new MongoQuery();
        query.use(MlnxDataMongoConfig.ECG_COLLECTIONNAME);
        List<Ecg> failEcgs = new ArrayList<Ecg>();
        for (int i = 0; i < ecgs.size(); i++) {
            try {
                boolean sucess = saveEcg(ecgs.get(i));
                if (!sucess)
                    failEcgs.add(ecgs.get(i));
            } catch (RuntimeException e) {
                e.printStackTrace();
                failEcgs.add(ecgs.get(i));
            }
        }
        if (failEcgs.size() > 0) {
            logger.error(String.format("EcgMongoDb saveEcg 总共保存：%d, 失败: %d", ecgs.size(), failEcgs.size()));
            return false;
        }

        return true;
    }

    public boolean saveEcg(Ecg ecg) {
        MongoQuery query = new MongoQuery();
        query.use(MlnxDataMongoConfig.ECG_COLLECTIONNAME);

        return query.set(new Document(BeanUtils.toMap(ecg, false))).save();
    }

    public List<Map<String, Object>> getEcg(long startTime, long endTime, int patientId) {
        return getEcgData(startTime, endTime, patientId, "data");
    }

    @Override
    public List<Map<String, Object>> getEncryEcg(long startTime, long endTime, int patientId) {
        return getEcgData(startTime, endTime, patientId, "encryData");
    }

    @Override
    public List<Map<String, Object>> getFilterEcg(long startTime, long endTime, int patientId) {
        return getEcgData(startTime, endTime, patientId, "filterData");
    }

    private List<Map<String, Object>> getEcgData(long startTime, long endTime, int patientId, String ecgDataKey){
        MongoQuery query = new MongoQuery();
        query.use(MlnxDataMongoConfig.ECG_COLLECTIONNAME);
        query.eq("patientId", patientId);
        query.gte("startTime", startTime);
        query.lte("startTime", endTime);
        query.ascending("startTime");
        query.projection(ecgDataKey, "startTime", "patientId", "deivceId","deviceType");

        List<JSONObject> jsonObjects = query.find();
        List<Map<String, Object>> maps = new ArrayList<>();

        if (jsonObjects != null) {
            for (JSONObject jsonObject : jsonObjects) {

                JSONObject ecgObject = jsonObject.getJSONObject(ecgDataKey);
                if (ecgObject != null) {
                    jsonObject.put("data", ecgObject.getJSONArray("data"));
                }
                if (!ecgDataKey.equals("data")){
                    jsonObject.remove(ecgDataKey);
                }
                maps.add(jsonObject);
            }
        }
        return maps;
    }

    public long count() {
        MongoQuery query = new MongoQuery();
        return query.use(MlnxDataMongoConfig.ECG_COLLECTIONNAME).count();
    }

    public static void main(String[] args) {

        EcgMongoDb ecgMongoDb = new EcgMongoDb();
        ecgMongoDb.init();
//
//        System.out.println(ecgMongoDb.count());
//        logger.error("sbdhbdsbh");

//        ecgMongoDb.save(getSimEcgs());

//
        long startTime = System.currentTimeMillis()-5*1000;
        long endTime = System.currentTimeMillis()-3*1000;


        List<Map<String, Object>> ecgMongs = ecgMongoDb.getFilterEcg(startTime, endTime, 3);
        System.out.println("ecgMongs.size:" + ecgMongs.size());
        System.out.println("原始数据");
        for (int i = 0; i < ecgMongs.size(); i++) {
            System.out.println(ecgMongs.get(i).toString());
        }

        System.out.println(new Date(endTime));
        System.out.println(new Date(1521701715317l));


//        ecgMongs = ecgMongoDb.getFilterEcg(startTime, endTime, 3);
//        System.out.println("ecgMongs.size:" + ecgMongs.size());
//        System.out.println("滤波数据");
//        for (int i = 0; i < 1; i++) {
//            System.out.println(ecgMongs.get(i).toString());
//        }
//
//        ecgMongs = ecgMongoDb.getEncryEcg(startTime, endTime, 0);
//        System.out.println("ecgMongs.size:" + ecgMongs.size());
//        for (int i = 0; i < ecgMongs.size(); i++) {
//            System.out.println(ecgMongs.get(i).toString());
//        }


//        System.out.println(ecgMongs.size());
//
//        byte[] bytes = new byte[]{(byte) 0x80, 0, (byte) 0x80, 0, (byte) 0x80, 0, (byte) 0x80, 0, (byte) 0x80, 0,};
//
//        BASE64Encoder enc = new BASE64Encoder();
//        String mes = enc.encodeBuffer(bytes); //使用BASE64编码
//
//        BASE64Decoder dec = new BASE64Decoder();
//        byte[] after = null;
//        try {
//            after = dec.decodeBuffer(mes);//使用BASE64解码
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("sbdjhsbdh");
    }

    private static void testExplain() {

        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient(MlnxDataMongoConfig.HOST, MlnxDataMongoConfig.PORT);

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase(MlnxDataMongoConfig.DATABASE);
            System.out.println("Connect to database successfully");

            MongoCollection<Document> collection = mongoDatabase.getCollection(MlnxDataMongoConfig.ECG_COLLECTIONNAME);
            System.out.println("集合 test 选择成功");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private static List<Ecg> getSimEcgs() {

        List<Ecg> ecgs = new ArrayList<Ecg>();

        for (int patientId = 1; patientId < 2; patientId++) {
            for (int i = 0; i < 10; i++) {
                Ecg ecg = new Ecg();

                ecg.setPatientId(patientId);
                ecg.setDeivceId("hek07bb123456");
                ecg.setStartTime(System.currentTimeMillis() - 1000 * i);
//                ecg.setStartTime(100000L);
                ecg.setEcgChannelType(ECGChannelType.CHAN_8);
                ecg.setSamplingRate(300);
                ecg.setAmplification(5);
                ecg.setHeartRate(90);
                ecg.setPose(5);


                byte[] bytes = new byte[200];
                for (int j = 0; j < bytes.length; j++) {
                    bytes[j] = (byte) j;
                }
//                ByteBuffer byteBuffer = ByteBuffer.allocate(2000);
//                byteBuffer.put(bytes);
//                byteBuffer.flip();
                ecg.setData(bytes);

                ecgs.add(ecg);
            }
        }

        return ecgs;
    }
}
