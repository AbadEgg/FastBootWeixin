package com.mlnx.qcms.utils;

public interface LogLis {

    void i(String log);
    void d(String log);
    void w(String log);
    void e(String log);
    void e(String log, Throwable t);
}
