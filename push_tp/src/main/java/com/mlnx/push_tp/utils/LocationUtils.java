package com.mlnx.push_tp.utils;

/**
 * Created by amanda.shan on 2017/10/18.
 */
public class LocationUtils {

    public static Float gpsLongitudeChange(String longi){
        Float longitude= Integer.parseInt(longi.substring(0, 3))+
                Float.valueOf(longi.substring(3, longi.length() - 2))/60;
        return longitude;
    }

    public static Float gpsLatitudeChange(String lati){
        Float latitude= Integer.parseInt(lati.substring(0, 2))+
                Float.valueOf(lati.substring(2, lati.length() - 2))/60;

        return latitude;
    }

    public static void main(String[] args) {
        String longi = "12140.1111E";
        String lati = "3410.2222E";

        System.out.println(gpsLongitudeChange(longi));
        System.out.println(gpsLatitudeChange(lati));
    }
}
