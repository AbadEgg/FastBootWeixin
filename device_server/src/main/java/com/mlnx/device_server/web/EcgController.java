package com.mlnx.device_server.web;


import com.mlnx.device_server.domain.result.ExceptionMsg;
import com.mlnx.device_server.domain.result.Response;
import com.mlnx.device_server.domain.result.ResponseData;
import com.mlnx.device_server.service.EcgService;
import com.mlnx.device_server.service.MpServerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by amanda.shan on 2017/4/28.
 */
@RestController
@RequestMapping("mlnx_device/ecg")
public class EcgController extends BaseController {

    @Autowired
    private MpServerService mpServerService;

    @Autowired
    private EcgService ecgService;

    @RequestMapping(value = "/getEcg", method = {RequestMethod.GET, RequestMethod.POST})
    public Response getEcg(Integer patientId, Long startTime, Long endTime) {

        Response response;
        if (patientId == null || startTime == null || endTime == null)
            response = result(ExceptionMsg.ParamError);
        else
            response = new ResponseData(ecgService.getEcg(patientId, startTime, endTime));
        return response;
    }

    @RequestMapping(value = "/getEncryEcg", method = {RequestMethod.GET, RequestMethod.POST})
    public Response getEncryEcg(Integer patientId, Long startTime, Long endTime) {

        Response response;
        if (patientId == null || startTime == null || endTime == null)
            response = result(ExceptionMsg.ParamError);
        else
            response = new ResponseData(ecgService.getEncryEcg(patientId, startTime, endTime));
        return response;
    }

}
