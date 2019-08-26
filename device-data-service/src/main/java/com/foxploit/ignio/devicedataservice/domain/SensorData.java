package com.foxploit.ignio.devicedataservice.domain;

import javax.persistence.Entity;

@Entity
public class SensorData {

    private float temperature;
    private float co_ppm;
    private float lp_gas_ppm;
    private float particle_ppm;
    private String epoch;

    public SensorData(float temperature, float co_ppm, float lp_gas_ppm, float particle_ppm, String epoch) {
        this.temperature = temperature;
        this.co_ppm = co_ppm;
        this.lp_gas_ppm = lp_gas_ppm;
        this.particle_ppm = particle_ppm;
        this.epoch = epoch;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getCo_ppm() {
        return co_ppm;
    }

    public void setCo_ppm(float co_ppm) {
        this.co_ppm = co_ppm;
    }

    public float getLp_gas_ppm() {
        return lp_gas_ppm;
    }

    public void setLp_gas_ppm(float lp_gas_ppm) {
        this.lp_gas_ppm = lp_gas_ppm;
    }

    public float getParticle_ppm() {
        return particle_ppm;
    }

    public void setParticle_ppm(float particle_ppm) {
        this.particle_ppm = particle_ppm;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

}
