package com.mlnx.mptp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MptpLogUtils {

    private static LogLis logLis;

    public static void setLogLis(LogLis logLis) {
        MptpLogUtils.logLis = logLis;
    }

    public enum LogType {
        DEBUG("调试"), INFO("信息"), WARN("警告"), ERROR("错误");

        private String describe;

        private LogType(String describe) {
            this.describe = describe;
        }

        @Override
        public String toString() {
            return describe;
        }
    }

    // private static LogType[] logTypes = { LogType.ERROR, LogType.DEBUG };

    private static LogType[] logTypes = {LogType.ERROR, LogType.WARN,
            LogType.DEBUG, LogType.INFO};

    public static void setLogType(LogType... logTypes) {
        MptpLogUtils.logTypes = logTypes;
    }

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName
                .lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(),
                caller.getLineNumber());
        return tag;
    }

    private static void print(LogType logType, String log) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (logType.compareTo(LogType.ERROR) == 0) {
            StackTraceElement caller = Utils.getCallerStackTraceElement();
            String tag = generateTag(caller);
            System.err.println(logType.toString() + ": "
                    + sdf.format(Calendar.getInstance().getTime()) + ": " + tag
                    + "\n" + log);

            // writeInLog(logType.toString() + ": "
            // + sdf.format(Calendar.getInstance().getTime()) + ": " + tag
            // + log + "\r\n");
        } else {
            StackTraceElement caller = Utils.getCallerStackTraceElement();
            String tag = generateTag(caller);
            System.out.println(logType.toString() + ": "
                    + sdf.format(Calendar.getInstance().getTime()) + ": " + tag
                    + "\n" + log);

            // writeInLog(logType.toString() + ": "
            // + sdf.format(Calendar.getInstance().getTime()) + ": " + tag
            // + log + "\r\n");
        }

    }

    private static String getTag() {
        StackTraceElement caller = Utils.getCallerStackTraceElement();
        String tag = generateTag(caller);
        return tag;
    }

    public static void mpFrame(String log) {
        if (MpLogLevelInfo.getInstance().isOpenFrameLog()) {
            if (logLis != null)
                logLis.d(getTag() + " " + log);
            else System.out.println(getTag() + " " + log);
        }
    }

    public static void mpDecode(String log) {
        if (MpLogLevelInfo.getInstance().isOpenDecodeLog()) {
            if (logLis != null)
                logLis.d(getTag() + " " + log);
            else System.out.println(getTag() + " " + log);
        }
    }

    public static void i(String log) {
//		if (!isPermissionPrint(LogType.INFO))
//			return;
//		LogType logType = LogType.INFO;
//		print(logType, log);

        {
            if (logLis != null)
                logLis.i(getTag() + " " + log);
            else System.out.println(getTag() + " " + log);
        }
    }

    public static void d(String log) {
//		if (!isPermissionPrint(LogType.DEBUG))
//			return;
//		LogType logType = LogType.DEBUG;
//		print(logType, log);

        if (logLis != null)
            logLis.d(getTag() + " " + log);
        else System.out.println(getTag() + " " + log);
    }

    public static void e(String log) {
//		if (!isPermissionPrint(LogType.ERROR))
//			return;
//		LogType logType = LogType.ERROR;
//		print(logType, log);

        if (logLis != null)
            logLis.e(getTag() + " " + log);
        else System.err.println(getTag() + " " + log);
    }

    public static void e(String log, Throwable t) {
        if (logLis != null)
            logLis.e(getTag() + " " + log, t);
        else {
            System.err.println(getTag() + " " + log);
            t.printStackTrace();
        }
    }

    public static void w(String log) {
//		if (!isPermissionPrint(LogType.WARN))
//			return;
//		LogType logType = LogType.WARN;
//		print(logType, log);

        if (logLis != null)
            logLis.w(getTag() + " " + log);
        else System.out.println(getTag() + " " + log);
    }

    private static boolean isPermissionPrint(LogType logType) {
        for (int i = 0; i < logTypes.length; i++) {
            if (logType.compareTo(logTypes[i]) == 0) {
                return true;
            }
        }
        return false;
    }

    //	final static String FQCN = Log4jLoggerAdapter.class .getName();
    public static void main(String[] args) {
//		print(LogType.ERROR, "shandong");

        d("sdjbsjd");
    }

}
