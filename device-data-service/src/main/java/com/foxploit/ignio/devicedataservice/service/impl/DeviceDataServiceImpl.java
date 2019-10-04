package com.foxploit.ignio.devicedataservice.service.impl;

import com.foxploit.ignio.devicedataservice.service.DeviceDataService;
import com.foxploit.ignio.devicedataservice.domain.DeviceData;
import com.foxploit.ignio.devicedataservice.repository.DeviceDataRepository;
import com.foxploit.ignio.devicedataservice.service.dto.DeviceDataDTO;
import com.foxploit.ignio.devicedataservice.service.mapper.DeviceDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DeviceData}.
 */
@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    private final Logger log = LoggerFactory.getLogger(DeviceDataServiceImpl.class);

    private final DeviceDataRepository deviceDataRepository;

    private final DeviceDataMapper deviceDataMapper;

    public DeviceDataServiceImpl(DeviceDataRepository deviceDataRepository, DeviceDataMapper deviceDataMapper) {
        this.deviceDataRepository = deviceDataRepository;
        this.deviceDataMapper = deviceDataMapper;
    }

    /**
     * Save a deviceData.
     *
     * @param deviceDataDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeviceDataDTO save(DeviceDataDTO deviceDataDTO) {
        log.debug("Request to save DeviceData : {}", deviceDataDTO);
        DeviceData deviceData = deviceDataMapper.toEntity(deviceDataDTO);
        deviceData = deviceDataRepository.save(deviceData);
        return deviceDataMapper.toDto(deviceData);
    }

    /**
     * Get all the deviceData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<DeviceDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceData");
        return deviceDataRepository.findAll(pageable)
            .map(deviceDataMapper::toDto);
    }


    /**
     * Get one deviceData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<DeviceDataDTO> findOne(String id) {
        log.debug("Request to get DeviceData : {}", id);
        return deviceDataRepository.findById(id)
            .map(deviceDataMapper::toDto);
    }

    /**
     * Delete the deviceData by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete DeviceData : {}", id);
        deviceDataRepository.deleteById(id);
    }
}
