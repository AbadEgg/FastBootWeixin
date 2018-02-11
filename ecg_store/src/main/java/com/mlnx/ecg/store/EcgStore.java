package com.mlnx.ecg.store;

import com.mlnx.ecg.store.domain.Ecg;

import java.util.List;
import java.util.Map;

/**
 * Created by amanda.shan on 2017/12/28.
 */
public interface EcgStore {

    void init();

    boolean save(List<Ecg> ecgs);

    List<Map<String, Object>> getEcg(long startTime, long endTime, int patientId);

}
