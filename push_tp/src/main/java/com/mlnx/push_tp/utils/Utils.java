package com.mlnx.push_tp.utils;

/**
 * Created by Administrator on 2016/1/7.
 */
public class Utils {

	public static StackTraceElement getCallerStackTraceElement() {
		return Thread.currentThread().getStackTrace()[4];
	}

}
