package com.foxploit.ignio.devicedataservice.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Document
public class DeviceData {

    @Id
    private String _id;
    private String deviceId;
    private SensorData[] sensorData;
    @Indexed(name="timestamp", expireAfterSeconds=432000)
    private Date timestamp;

    public DeviceData(String deviceId, SensorData[] sensorData, Date timestamp) {
        this.deviceId = deviceId;
        this.sensorData = sensorData;
        this.timestamp = timestamp;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
