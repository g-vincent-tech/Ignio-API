package com.foxploit.ignio.gateway.service.dto;

import java.time.LocalDateTime;

/**
 * A DTO for the Alert.
 */
public class Alert {

    private String deviceId;

    private String ownerId;

    private String alertType;

    private LocalDateTime timestamp;

    public Alert() {
    }

    public Alert(String deviceId, String ownerId, String alertType, LocalDateTime timestamp) {
        this.deviceId = deviceId;
        this.ownerId = ownerId;
        this.alertType = alertType;
        this.timestamp = timestamp;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
