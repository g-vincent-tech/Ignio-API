package com.foxploit.ignio.devicedataservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Device.
 */
@Document(collection = "device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("deviceId")
    private String deviceId;

    @Field("owner_id")
    private String ownerId;

    @Field("created")
    private LocalDate created;

    @Field("purchased")
    private LocalDate purchased;

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

    public Device deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Device ownerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public Device created(LocalDate created) {
        this.created = created;
        return this;
    }

    public LocalDate getPurchased() {
        return purchased;
    }

    public void setPurchased(LocalDate purchased) {
        this.purchased = purchased;
    }

    public Device purchased(LocalDate purchased) {
        this.purchased = purchased;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Device)) {
            return false;
        }
        return id != null && id.equals(((Device) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Device{" + "id=" + getId() + ", deviceId='" + getDeviceId() + "'" + ", ownerId='" + getOwnerId() + "'" + ", created='" + getCreated() + "'" + ", purchased='" + getPurchased() + "'" + "}";
    }
}
