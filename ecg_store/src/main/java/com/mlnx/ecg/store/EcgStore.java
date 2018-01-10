package com.mlnx.ecg.store;

import com.mlnx.mptp.model.Ecg;

import java.util.List;

/**
 * Created by amanda.shan on 2017/12/28.
 */
public interface EcgStore {

    void init();

    boolean save(List<Ecg> ecgs);

    List<Ecg> getEcg(long startTime, long endTime, int patientId);

}
