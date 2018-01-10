package com.mlnx.device_server.comm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by amanda.shan on 2017/3/29.
 */
@Component
public class OTSConfig {

    @Value("${ecg.table.endPoint}")
    private String endPoint;
    @Value("${ecg.table.accessKeyId}")
    private String accessKeyId;
    @Value("${ecg.table.accessKeySecret}")
    private String accessKeySecret;
    @Value("${ecg.table.instanceName}")
    private String instanceName;

    @Value("${ecg.table.COLUMN_KEY1_NAME}")
    private String COLUMN_KEY1_NAME;
    @Value("${ecg.table.COLUMN_KEY2_NAME}")
    private String COLUMN_KEY2_NAME;

    @Value("${ecg.table.TABLE_NAME}")
    private String TABLE_NAME;

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getCOLUMN_KEY1_NAME() {
        return COLUMN_KEY1_NAME;
    }

    public void setCOLUMN_KEY1_NAME(String COLUMN_KEY1_NAME) {
        this.COLUMN_KEY1_NAME = COLUMN_KEY1_NAME;
    }

    public String getCOLUMN_KEY2_NAME() {
        return COLUMN_KEY2_NAME;
    }

    public void setCOLUMN_KEY2_NAME(String COLUMN_KEY2_NAME) {
        this.COLUMN_KEY2_NAME = COLUMN_KEY2_NAME;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    @Override
    public String toString() {
        return "OTSConfig{" +
                "endPoint='" + endPoint + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", instanceName='" + instanceName + '\'' +
                ", COLUMN_KEY1_NAME='" + COLUMN_KEY1_NAME + '\'' +
                ", COLUMN_KEY2_NAME='" + COLUMN_KEY2_NAME + '\'' +
                ", TABLE_NAME='" + TABLE_NAME + '\'' +
                '}';
    }
}
