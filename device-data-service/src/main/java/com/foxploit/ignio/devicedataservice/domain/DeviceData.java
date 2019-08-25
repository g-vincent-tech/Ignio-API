package com.foxploit.ignio.devicedataservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DeviceData {

    @Id
    private String _id;
    private String deviceId;
    private SensorData[] sensorData;
    private String epoch;

    public DeviceData(String deviceId, SensorData[] sensorData, String epoch) {
        this.deviceId = deviceId;
        this.sensorData = sensorData;
        this.epoch = epoch;
    }

    public String get_id() {
        return _id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public SensorData[] getSensorData() {
        return sensorData;
    }

    public void setSensorData(SensorData[] sensorData) {
        this.sensorData = sensorData;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

}
