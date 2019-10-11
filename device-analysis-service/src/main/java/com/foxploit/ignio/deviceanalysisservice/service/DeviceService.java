package com.foxploit.ignio.deviceanalysisservice.service;

import com.foxploit.ignio.deviceanalysisservice.service.dto.DeviceDTO;
import java.util.Optional;

/**
 * Service Interface for managing {Device}.
 */
public interface DeviceService {

    /**
     * Get the "deviceId" device.
     *
     * @param deviceId the deviceId of the entity.
     * @return the entity.
     */
    Optional<DeviceDTO> findOneByDeviceId(String deviceId);

}
