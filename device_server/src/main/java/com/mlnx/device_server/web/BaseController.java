package com.mlnx.device_server.web;


import com.mlnx.device_server.comm.utils.MD5Util;
import com.mlnx.device_server.domain.result.ExceptionMsg;
import com.mlnx.device_server.domain.result.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * Created by amanda.shan on 2017/2/28.
 */
@Controller
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Response result(ExceptionMsg msg) {
        return new Response(msg);
    }

    protected Response result() {
        return new Response();
    }

    protected String getPwd(String password) {
        try {
            String pwd = MD5Util.encrypt(password);
            return pwd;
        } catch (Exception e) {
            logger.error("密码加密异常：", e);
        }
        return null;
    }

    protected String getToken(){
        try {
            String token = MD5Util.encrypt(System.currentTimeMillis()+"");
            return token;
        } catch (Exception e) {
            logger.error("获取token异常：", e);
        }
        return null;
    }
}
