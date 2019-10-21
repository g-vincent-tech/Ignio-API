package com.foxploit.ignio.devicedataservice.service.dto;

import com.foxploit.ignio.devicedataservice.domain.SensorData;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.foxploit.ignio.devicedataservice.domain.DeviceData} entity.
 */
public class DeviceDataDTO implements Serializable {

    private String id;

    @NotNull
    private String deviceId;

    @NotNull
    private Set<SensorData> sensorData = new HashSet<>();

    @NotNull
    private String epoch;

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

    public Set<SensorData> getSensorData() {
        return sensorData;
    }

    public void setSensorData(Set<SensorData> sensorData) {
        this.sensorData = sensorData;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeviceDataDTO deviceDataDTO = (DeviceDataDTO) o;
        if (deviceDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceDataDTO{" + "id=" + getId() + ", deviceId='" + getDeviceId() + "'" + ", sensorData=" + getSensorData().toString() + ", epoch='" + getEpoch() + "'" + "}";
    }
}
