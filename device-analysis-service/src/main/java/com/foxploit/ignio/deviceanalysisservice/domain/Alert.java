package com.foxploit.ignio.deviceanalysisservice.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A Alert.
 */
@Document(collection = "alert")
public class Alert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("device_id")
    private String deviceId;

    @NotNull
    @Field("owner_id")
    private String ownerId;

    @NotNull
    @Field("alert_type")
    private String alertType;

    @Field("timestamp")
    private @NotNull LocalDateTime timestamp;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove


    public Alert() {
    }

    public Alert(String deviceId, String ownerId, String alertType, LocalDateTime timestamp) {
        this.deviceId = deviceId;
        this.ownerId = ownerId;
        this.alertType = alertType;
        this.timestamp = timestamp;
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

    public Alert deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Alert ownerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAlertType() {
        return alertType;
    }

    public Alert alertType(String alertType) {
        this.alertType = alertType;
        return this;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public @NotNull LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Alert timestamp(@NotNull LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(@NotNull LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alert)) {
            return false;
        }
        return id != null && id.equals(((Alert) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Alert{" +
            "id=" + getId() +
            ", deviceId='" + getDeviceId() + "'" +
            ", ownerId='" + getOwnerId() + "'" +
            ", alertType='" + getAlertType() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            "}";
    }
}
