package com.mlnx.device.server.mybatis.entity;

public class TEcgDevice {
    private Integer id;

    private String ecgDeviceRunMode;

    private String ecgChannelType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEcgDeviceRunMode() {
        return ecgDeviceRunMode;
    }

    public void setEcgDeviceRunMode(String ecgDeviceRunMode) {
        this.ecgDeviceRunMode = ecgDeviceRunMode == null ? null : ecgDeviceRunMode.trim();
    }

    public String getEcgChannelType() {
        return ecgChannelType;
    }

    public void setEcgChannelType(String ecgChannelType) {
        this.ecgChannelType = ecgChannelType == null ? null : ecgChannelType.trim();
    }
}