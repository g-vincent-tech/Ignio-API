package com.foxploit.ignio.devicedataservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DeviceData.
 */
@Document(collection = "device_data")
public class DeviceData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("deviceId")
    private String deviceId;

    @DBRef
    @Field("sensorData")
    private Set<SensorData> sensorData = new HashSet<>();

    @NotNull
    @Field("epoch")
    @Indexed(name = "timestamp", expireAfterSeconds = 432000)
    private String epoch;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceData deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    public DeviceData epoch(String epoch) {
        this.epoch = epoch;
        return this;
    }

    public Set<SensorData> getSensorData() {
        return sensorData;
    }

    public void setSensorData(Set<SensorData> sensorData) {
        this.sensorData = sensorData;
    }

    public DeviceData sensorData(Set<SensorData> sensorData) {
        this.sensorData = sensorData;
        return this;
    }

    public DeviceData addSensorData(SensorData sensorData) {
        this.sensorData.add(sensorData);
        return this;
    }

    public DeviceData removeSensorData(SensorData sensorData) {
        this.sensorData.remove(sensorData);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceData)) {
            return false;
        }
        return id != null && id.equals(((DeviceData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DeviceData{" + "id=" + getId() + ", deviceId='" + getDeviceId() + "'" + ", epoch='" + getEpoch() + "'" + "}";
    }

}
