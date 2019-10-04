package com.foxploit.ignio.devicedataservice.service.impl;

import com.foxploit.ignio.devicedataservice.domain.Device;
import com.foxploit.ignio.devicedataservice.domain.SensorData;
import com.foxploit.ignio.devicedataservice.repository.DeviceRepository;
import com.foxploit.ignio.devicedataservice.repository.SensorDataRepository;
import com.foxploit.ignio.devicedataservice.service.DeviceDataService;
import com.foxploit.ignio.devicedataservice.domain.DeviceData;
import com.foxploit.ignio.devicedataservice.repository.DeviceDataRepository;
import com.foxploit.ignio.devicedataservice.service.dto.DeviceDataDTO;
import com.foxploit.ignio.devicedataservice.service.mapper.DeviceDataMapper;
import com.foxploit.ignio.devicedataservice.service.mapper.SensorDataMapper;
import com.foxploit.ignio.devicedataservice.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link DeviceData}.
 */
@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    private final Logger log = LoggerFactory.getLogger(DeviceDataServiceImpl.class);

    private final DeviceDataRepository deviceDataRepository;

    private final DeviceRepository deviceRepository;

    private final SensorDataRepository sensorDataRepository;

    private final DeviceDataMapper deviceDataMapper;

    public DeviceDataServiceImpl(DeviceDataRepository deviceDataRepository, DeviceRepository deviceRepository, DeviceDataMapper deviceDataMapper, SensorDataMapper sensorDataMapper, SensorDataRepository sensorDataRepository) {
        this.deviceDataRepository = deviceDataRepository;
        this.deviceRepository = deviceRepository;
        this.deviceDataMapper = deviceDataMapper;
        this.sensorDataRepository = sensorDataRepository;
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
        Optional<Device> deviceOptional = deviceRepository.findByDeviceId(deviceDataDTO.getDeviceId());
        if (!deviceOptional.isPresent()) {
            log.error("Save DeviceData failed! : {}", deviceDataDTO);
            throw new BadRequestAlertException("Invalid deviceId! Doesn't exist", deviceDataDTO.getDeviceId(), "invalidid");
        }
        Set<SensorData> sensorDataSet = new HashSet<>();
        DeviceData deviceData = deviceDataMapper.toEntity(deviceDataDTO);
        deviceDataDTO.getSensorData().forEach(sensorData -> {
            sensorData.setDeviceId(deviceDataDTO.getDeviceId());
            sensorDataRepository.save(sensorData);
            sensorDataSet.add(sensorData);
        });
        deviceData.setSensorData(sensorDataSet);
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
