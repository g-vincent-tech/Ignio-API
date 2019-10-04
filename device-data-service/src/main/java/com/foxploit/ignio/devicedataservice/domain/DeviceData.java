package com.foxploit.ignio.devicedataservice.domain;
import org.springframework.data.annotation.Id;
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
    @Field("device_id")
    private String deviceId;

    @NotNull
    @Field("epoch")
    private String epoch;

    @DBRef
    @Field("sensorData")
    private Set<SensorData> sensorData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceData deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEpoch() {
        return epoch;
    }

    public DeviceData epoch(String epoch) {
        this.epoch = epoch;
        return this;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    public Set<SensorData> getSensorData() {
        return sensorData;
    }

    public DeviceData sensorData(Set<SensorData> sensorData) {
        this.sensorData = sensorData;
        return this;
    }

    public DeviceData addSensorData(SensorData sensorData) {
        this.sensorData.add(sensorData);
        sensorData.setDeviceData(this);
        return this;
    }

    public DeviceData removeSensorData(SensorData sensorData) {
        this.sensorData.remove(sensorData);
        sensorData.setDeviceData(null);
        return this;
    }

    public void setSensorData(Set<SensorData> sensorData) {
        this.sensorData = sensorData;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        return "DeviceData{" +
            "id=" + getId() +
            ", deviceId='" + getDeviceId() + "'" +
            ", epoch='" + getEpoch() + "'" +
            "}";
    }
}
