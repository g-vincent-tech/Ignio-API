package com.foxploit.ignio.devicedataservice.models;

import javax.persistence.Entity;

@Entity
public class SensorData {

    private float temperature;
    private float co_percentage;
    private float lp_gas_percentage;
    private float particle_percentage;
    private long epoch;

    public SensorData(float temperature, float co_percentage, float lp_gas_percentage, float particle_percentage, long epoch) {
        this.temperature = temperature;
        this.co_percentage = co_percentage;
        this.lp_gas_percentage = lp_gas_percentage;
        this.particle_percentage = particle_percentage;
        this.epoch = epoch;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getCo_percentage() {
        return co_percentage;
    }

    public void setCo_percentage(float co_percentage) {
        this.co_percentage = co_percentage;
    }

    public float getLp_gas_percentage() {
        return lp_gas_percentage;
    }

    public void setLp_gas_percentage(float lp_gas_percentage) {
        this.lp_gas_percentage = lp_gas_percentage;
    }

    public float getParticle_percentage() {
        return particle_percentage;
    }

    public void setParticle_percentage(float particle_percentage) {
        this.particle_percentage = particle_percentage;
    }

    public long getEpoch() {
        return epoch;
    }

    public void setEpoch(long epoch) {
        this.epoch = epoch;
    }

}
