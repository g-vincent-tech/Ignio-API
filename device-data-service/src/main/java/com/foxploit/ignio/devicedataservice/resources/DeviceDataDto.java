package com.foxploit.ignio.devicedataservice.resources;

import com.foxploit.ignio.devicedataservice.domain.SensorData;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class DeviceDataDto {

    private String deviceId;

    private List<SensorData> sensorData;

    public DeviceDataDto(String deviceId, List<SensorData> sensorData) {
        this.deviceId = deviceId;
        this.sensorData = sensorData;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<SensorData> getSensorData() {
        return sensorData;
    }

    public void setSensorData(List<SensorData> sensorData) {
        this.sensorData = sensorData;
    }
}
