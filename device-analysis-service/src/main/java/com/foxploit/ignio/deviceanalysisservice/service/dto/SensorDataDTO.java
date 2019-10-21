package com.foxploit.ignio.deviceanalysisservice.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {SensorData} entity.
 */
public class SensorDataDTO implements Serializable {

    private String id;

    @NotNull
    private String deviceId;

    @NotNull
    private Float temperature;

    @NotNull
    private Float co_ppm;

    @NotNull
    private Float lp_gas_ppm;

    @NotNull
    private Float particle_ppm;

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

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getCo_ppm() {
        return co_ppm;
    }

    public void setCo_ppm(Float co_ppm) {
        this.co_ppm = co_ppm;
    }

    public Float getLp_gas_ppm() {
        return lp_gas_ppm;
    }

    public void setLp_gas_ppm(Float lp_gas_ppm) {
        this.lp_gas_ppm = lp_gas_ppm;
    }

    public Float getParticle_ppm() {
        return particle_ppm;
    }

    public void setParticle_ppm(Float particle_ppm) {
        this.particle_ppm = particle_ppm;
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

        SensorDataDTO sensorDataDTO = (SensorDataDTO) o;
        if (sensorDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sensorDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SensorDataDTO{" + "id=" + getId() + ", deviceId='" + getDeviceId() + "'" + ", temperature=" + getTemperature() + ", co_ppm=" + getCo_ppm() + ", lp_gas_ppm=" + getLp_gas_ppm() + ", particle_ppm=" + getParticle_ppm() + ", epoch='" + getEpoch() + "'" + "}";
    }

}
