package com.foxploit.ignio.devicedataservice.service.impl;

import com.foxploit.ignio.devicedataservice.service.SensorDataService;
import com.foxploit.ignio.devicedataservice.domain.SensorData;
import com.foxploit.ignio.devicedataservice.repository.SensorDataRepository;
import com.foxploit.ignio.devicedataservice.service.dto.SensorDataDTO;
import com.foxploit.ignio.devicedataservice.service.mapper.SensorDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SensorData}.
 */
@Service
public class SensorDataServiceImpl implements SensorDataService {

    private final Logger log = LoggerFactory.getLogger(SensorDataServiceImpl.class);

    private final SensorDataRepository sensorDataRepository;

    private final SensorDataMapper sensorDataMapper;

    public SensorDataServiceImpl(SensorDataRepository sensorDataRepository, SensorDataMapper sensorDataMapper) {
        this.sensorDataRepository = sensorDataRepository;
        this.sensorDataMapper = sensorDataMapper;
    }

    /**
     * Save a sensorData.
     *
     * @param sensorDataDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SensorDataDTO save(SensorDataDTO sensorDataDTO) {
        log.debug("Request to save SensorData : {}", sensorDataDTO);
        SensorData sensorData = sensorDataMapper.toEntity(sensorDataDTO);
        sensorData = sensorDataRepository.save(sensorData);
        return sensorDataMapper.toDto(sensorData);
    }

    /**
     * Get all the sensorData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<SensorDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SensorData");
        return sensorDataRepository.findAll(pageable)
            .map(sensorDataMapper::toDto);
    }


    /**
     * Get one sensorData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<SensorDataDTO> findOne(String id) {
        log.debug("Request to get SensorData : {}", id);
        return sensorDataRepository.findById(id)
            .map(sensorDataMapper::toDto);
    }

    /**
     * Get all sensorData by deviceId.
     *
     *
     * @param pageable the pagination information.
     * @param deviceId the deviceId of the entity.
     * @return the list of entities.
     */
    @Override
    public Page<SensorDataDTO> findByDeviceId(Pageable pageable, String deviceId) {
        log.debug("Request to get all SensorData by deviceId");
        return sensorDataRepository.findByDeviceId(pageable, deviceId)
            .map(sensorDataMapper::toDto);
    }

    /**
     * Delete the sensorData by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete SensorData : {}", id);
        sensorDataRepository.deleteById(id);
    }
}
