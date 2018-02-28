package com.mlnx.ecg.store.config;

/**
 * Created by amanda.shan on 2017/5/10.
 */
public class MlnxDataMongoConfig {

    public static String HOST = "192.168.1.167";
    public static int PORT = 27017;

    public static String DATABASE = "mlnx_data";

    public static String USER = "mlnx";
    public static String PWD = "medgen2011";

    public static String ECG_COLLECTIONNAME = "ecg_collection";
    public static String BP_COLLECTIONNAME = "bp_collection";
    public static String BS_COLLECTIONNAME = "bs_collection";

}
