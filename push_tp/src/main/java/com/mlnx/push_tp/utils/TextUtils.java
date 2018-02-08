package com.mlnx.push_tp.utils;

/**
 * Created by amanda.shan on 2017/3/2.
 */
public class TextUtils {

    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
}
