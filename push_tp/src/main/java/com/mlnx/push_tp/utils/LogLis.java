package com.mlnx.push_tp.utils;

/**
 * Created by amanda.shan on 2017/4/27.
 */
public interface LogLis {

    void i(String log);
    void d(String log);
    void w(String log);
    void e(String log);
    void e(String log, Throwable t);
}
