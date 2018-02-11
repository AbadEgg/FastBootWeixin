package com.mlnx.device_server.service;

import com.mlnx.device_server.comm.config.MpServerConfig;
import com.mlnx.device_server.domain.EcgOnlineDevice;
import com.mlnx.mp_server.MpServer;
import com.mlnx.mp_server.config.ConfigService;
import com.mlnx.mp_server.support.Action;
import com.mlnx.mp_server.support.MpSupportManager;
import com.mlnx.mp_server.support.UsrSupport;
import com.mlnx.mp_session.core.EcgDeviceSession;
import com.mlnx.mp_session.core.SessionManager;
import com.mlnx.mptp.utils.LogLis;
import com.mlnx.mptp.utils.MptpLogUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

/**
 * Created by amanda.shan on 2017/12/19.
 */
@Service
public class MpServerService {

    @Autowired
    private MpServerConfig mpServerConfig;

    private MpServer mpServer;

    protected Logger logger = LoggerFactory.getLogger(MptpLogUtils.class);

    private LogLis logLis = new LogLis() {
        @Override
        public void i(String log) {
            logger.info(log);
        }

        @Override
        public void d(String log) {
            logger.debug(log);
        }

        @Override
        public void w(String log) {
            logger.warn(log);
        }

        @Override
        public void e(String log) {
            logger.error(log);
        }

        @Override
        public void e(String log, Throwable t) {
            logger.error(log, t);
        }
    };

    @PostConstruct
    private void startServer() {

        MpSupportManager.getInstance().setUsrSupport(usrSupport);

        MptpLogUtils.setLogLis(logLis);

        ConfigService.MP_DEVICE_PORT = mpServerConfig.getMpDevicePort();
        ConfigService.MP_WEB_PORT = mpServerConfig.getMpWebPort();

        try {
            mpServer = new MpServer();
            mpServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UsrSupport usrSupport = new UsrSupport() {
        @Override
        public void verifyUsr(Action action) {
            MpSupportManager.getInstance().verifyUsr(true, action);
        }
    };


    public int getEcgDeviceSize() {
        return SessionManager.getEcgSessions().size();
    }

    public List<EcgOnlineDevice> getEcgSessions(int start, int size) {

        List<EcgOnlineDevice> ecgDevices = new ArrayList<>();
        for (int i = start; i < start + size && i < SessionManager.getEcgSessions().size(); i++) {
            ecgDevices.add(new EcgOnlineDevice((EcgDeviceSession) SessionManager.getEcgSessions().get(i)));
        }
        return ecgDevices;
    }
}
