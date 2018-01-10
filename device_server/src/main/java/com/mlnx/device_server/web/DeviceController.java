package com.mlnx.device_server.web;


import com.mlnx.device_server.comm.utils.TextUtils;
import com.mlnx.device_server.domain.result.ExceptionMsg;
import com.mlnx.device_server.domain.result.Response;
import com.mlnx.device_server.domain.result.ResponseData;
import com.mlnx.device_server.service.DeviceService;
import com.mlnx.device_server.service.MpServerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by amanda.shan on 2017/5/8.
 */
@RestController
@RequestMapping("mlnx_device/device")
public class DeviceController extends BaseController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MpServerService mpServerService;


    @RequestMapping(value = "/configDevice", method = {RequestMethod.GET, RequestMethod.POST})
    public Response configDevice(String deviceId, String ssid, String password, Byte wifiChannel, byte[] serverIp,
                                 Integer serverPort, Byte heartChannel) {

        String error = deviceService.configDevice(deviceId, ssid, password, wifiChannel, serverIp,
                serverPort, heartChannel);
        if (TextUtils.isEmpty(error))
            return result();
        else {
            ExceptionMsg exceptionMsg = ExceptionMsg.ConfigDeviceError;
            exceptionMsg.setMsg(error);
            return new Response(exceptionMsg);
        }
    }

    @RequestMapping(value = "/testConfigDevice", method = {RequestMethod.GET, RequestMethod.POST})
    public Response testConfigDevice(String deviceId) {

        if (deviceService.testConfigDevice(deviceId))
            return result();
        else{
            return result(ExceptionMsg.ConfigDeviceNotOnline);
        }
    }


    //====================多惨服务器 在线信息========================//

    @RequestMapping(value = "/ecgsize", method = {RequestMethod.GET, RequestMethod.POST})
    public Response getEcgDeviceSize() {

        ResponseData responseData = new ResponseData(mpServerService.getEcgDeviceSize());
        return responseData;
    }

    @RequestMapping(value = "/ecgdevice_list", method = {RequestMethod.GET, RequestMethod.POST})
    public Response getEcgDevices(int start, int size) {

        ResponseData responseData = new ResponseData(mpServerService.getEcgSessions(start, size));
        return responseData;
    }
}
