package com.mlnx.ecg.store;

import com.mlnx.ecg.store.domain.DeviceOnlineRecord;

public interface DeviceStore {

    void init();

    boolean save(DeviceOnlineRecord deviceOnlineRecord);
}
