package com.foxploit.ignio.devicedataservice.resources;

import com.foxploit.ignio.devicedataservice.models.DeviceData;
import com.foxploit.ignio.devicedataservice.models.SensorData;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class AllDeviceData {

    private String deviceId;
    private List<DeviceData> deviceData;
    private List<SensorData> sensorData;

    public AllDeviceData(String deviceId, List<DeviceData> deviceData, List<SensorData> sensorData) {
        this.deviceId = deviceId;
        this.deviceData = deviceData;
        this.sensorData = sensorData;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<DeviceData> getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(List<DeviceData> deviceData) {
        this.deviceData = deviceData;
    }

    public List<SensorData> getSensorData() {
        return sensorData;
    }

    public void setSensorData(List<SensorData> sensorData) {
        this.sensorData = sensorData;
    }
}
