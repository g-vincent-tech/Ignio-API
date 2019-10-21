package com.foxploit.ignio.devicedataservice.service;

import com.foxploit.ignio.devicedataservice.service.dto.DeviceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.foxploit.ignio.devicedataservice.domain.Device}.
 */
public interface DeviceService {

    /**
     * Save a device.
     *
     * @param deviceDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceDTO save(DeviceDTO deviceDTO);

    /**
     * Update a device.
     *
     * @param deviceDTO the entity to update.
     * @return the persisted entity.
     */
    DeviceDTO update(DeviceDTO deviceDTO);

    /**
     * Get all the devices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" device.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceDTO> findOne(String id);

    /**
     * Get the "deviceId" device.
     *
     * @param deviceId the deviceId of the entity.
     * @return the entity.
     */
    Optional<DeviceDTO> findOneByDeviceId(String deviceId);

    /**
     * Delete the "id" device.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
