package com.foxploit.ignio.devicedataservice.service;

import com.foxploit.ignio.devicedataservice.service.dto.DeviceDataDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.foxploit.ignio.devicedataservice.domain.DeviceData}.
 */
public interface DeviceDataService {

    /**
     * Save a deviceData.
     *
     * @param deviceDataDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceDataDTO save(DeviceDataDTO deviceDataDTO);

    /**
     * Get all the deviceData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceDataDTO> findAll(Pageable pageable);


    /**
     * Get the "id" deviceData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceDataDTO> findOne(String id);

    /**
     * Delete the "id" deviceData.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
