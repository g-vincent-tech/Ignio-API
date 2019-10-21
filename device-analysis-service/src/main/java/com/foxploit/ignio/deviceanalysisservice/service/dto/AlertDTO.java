package com.foxploit.ignio.deviceanalysisservice.service.dto;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.foxploit.ignio.deviceanalysisservice.domain.Alert} entity.
 */
public class AlertDTO implements Serializable {

    private String id;

    @NotNull
    private String deviceId;

    @NotNull
    private String ownerId;

    @NotNull
    private String alertType;

    private @NotNull LocalDateTime timestamp;

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

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public @NotNull LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NotNull LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlertDTO alertDTO = (AlertDTO) o;
        if (alertDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alertDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlertDTO{" +
            "id=" + getId() +
            ", deviceId='" + getDeviceId() + "'" +
            ", ownerId='" + getOwnerId() + "'" +
            ", alertType='" + getAlertType() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
