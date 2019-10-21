package com.foxploit.ignio.devicedataservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A SensorData.
 */
@Document(collection = "sensor_data")
public class SensorData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("deviceId")
    private String deviceId;

    @NotNull
    @Field("temperature")
    private Float temperature;

    @NotNull
    @Field("co_ppm")
    private Float co_ppm;

    @NotNull
    @Field("lp_gas_ppm")
    private Float lp_gas_ppm;

    @NotNull
    @Field("particle_ppm")
    private Float particle_ppm;

    @NotNull
    @Field("epoch")
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

    public SensorData deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public SensorData temperature(Float temperature) {
        this.temperature = temperature;
        return this;
    }

    public Float getCo_ppm() {
        return co_ppm;
    }

    public void setCo_ppm(Float co_ppm) {
        this.co_ppm = co_ppm;
    }

    public SensorData co_ppm(Float co_ppm) {
        this.co_ppm = co_ppm;
        return this;
    }

    public Float getLp_gas_ppm() {
        return lp_gas_ppm;
    }

    public void setLp_gas_ppm(Float lp_gas_ppm) {
        this.lp_gas_ppm = lp_gas_ppm;
    }

    public SensorData lp_gas_ppm(Float lp_gas_ppm) {
        this.lp_gas_ppm = lp_gas_ppm;
        return this;
    }

    public Float getParticle_ppm() {
        return particle_ppm;
    }

    public void setParticle_ppm(Float particle_ppm) {
        this.particle_ppm = particle_ppm;
    }

    public SensorData particle_ppm(Float particle_ppm) {
        this.particle_ppm = particle_ppm;
        return this;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    public SensorData epoch(String epoch) {
        this.epoch = epoch;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SensorData)) {
            return false;
        }
        return id != null && id.equals(((SensorData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SensorData{" + "id=" + getId() + ", deviceId='" + getDeviceId() + "'" + ", temperature=" + getTemperature() + ", co_ppm=" + getCo_ppm() + ", lp_gas_ppm=" + getLp_gas_ppm() + ", particle_ppm=" + getParticle_ppm() + ", epoch='" + getEpoch() + "'" + "}";
    }
}
