package com.mlnx.config;

import java.net.URI;

public class Config {

    public static String HOST = "192.168.1.101";
//    public static String HOST = "118.31.1.101";
//        public static String HOST = "118.31.184.191";
//    public static String HOST = "192.168.1.138";
    //	public static String HOST = "47.90.53.171";
//	public static String HOST = "192.168.1.121";
    public static int PORT = 22222;

    public static long registerInterval = 1 * 10 * 1000;
    public static long pingIntegerval = 10 * 1000;

    public static URI LIFE_PORT_USR_URI = URI.create(String.format("ws://%s:%d/", HOST, PORT));


}
