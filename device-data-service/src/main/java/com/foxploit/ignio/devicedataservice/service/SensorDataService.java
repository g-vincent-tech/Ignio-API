package com.foxploit.ignio.devicedataservice.service;

import com.foxploit.ignio.devicedataservice.service.dto.SensorDataDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.foxploit.ignio.devicedataservice.domain.SensorData}.
 */
public interface SensorDataService {

    /**
     * Save a sensorData.
     *
     * @param sensorDataDTO the entity to save.
     * @return the persisted entity.
     */
    SensorDataDTO save(SensorDataDTO sensorDataDTO);

    /**
     * Get all the sensorData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SensorDataDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sensorData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SensorDataDTO> findOne(String id);

    /**
     * Delete the "id" sensorData.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
