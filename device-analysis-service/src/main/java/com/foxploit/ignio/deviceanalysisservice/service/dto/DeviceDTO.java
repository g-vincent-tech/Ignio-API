package com.foxploit.ignio.deviceanalysisservice.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {Device} entity.
 */
public class DeviceDTO implements Serializable {

    private String id;

    @NotNull
    private String deviceId;

    private String ownerId;

    private LocalDate created;

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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getPurchased() {
        return purchased;
    }

    public void setPurchased(LocalDate purchased) {
        this.purchased = purchased;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeviceDTO deviceDTO = (DeviceDTO) o;
        if (deviceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceDTO{" + "id=" + getId() + ", deviceId='" + getDeviceId() + "'" + ", ownerId='" + getOwnerId() + "'" + ", created='" + getCreated() + "'" + ", purchased='" + getPurchased() + "'" + "}";
    }

}
