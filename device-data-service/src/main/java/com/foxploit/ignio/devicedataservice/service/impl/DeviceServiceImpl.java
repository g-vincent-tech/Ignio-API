package com.foxploit.ignio.devicedataservice.service.impl;

import com.foxploit.ignio.devicedataservice.domain.Device;
import com.foxploit.ignio.devicedataservice.repository.DeviceRepository;
import com.foxploit.ignio.devicedataservice.service.DeviceService;
import com.foxploit.ignio.devicedataservice.service.dto.DeviceDTO;
import com.foxploit.ignio.devicedataservice.service.mapper.DeviceMapper;
import com.foxploit.ignio.devicedataservice.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Device}.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    public DeviceServiceImpl(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    /**
     * Save a device.
     *
     * @param deviceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeviceDTO save(DeviceDTO deviceDTO) {
        log.debug("Request to save Device : {}", deviceDTO);
        Optional<Device> deviceOptional = deviceRepository.findByDeviceId(deviceDTO.getDeviceId());
        if (deviceOptional.isPresent()) {
            log.error("Save Device failed! : {}", deviceDTO);
            throw new BadRequestAlertException("Invalid deviceId", deviceDTO.getDeviceId(), "idexists");
        }
        Device device = deviceMapper.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        return deviceMapper.toDto(device);
    }

    /**
     * Get all the devices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<DeviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        return deviceRepository.findAll(pageable)
            .map(deviceMapper::toDto);
    }


    /**
     * Get one device by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<DeviceDTO> findOne(String id) {
        log.debug("Request to get Device : {}", id);
        return deviceRepository.findById(id)
            .map(deviceMapper::toDto);
    }

    /**
     * Get one device by deviceId.
     *
     * @param deviceId the deviceId of the entity.
     * @return the entity.
     */
    @Override
    public Optional<DeviceDTO> findOneByDeviceId(String deviceId) {
        log.debug("Request to get Device : {}", deviceId);
        return deviceRepository.findByDeviceId(deviceId)
            .map(deviceMapper::toDto);
    }

    /**
     * Delete the device by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Device : {}", id);
        deviceRepository.deleteById(id);
    }

    /**
     * Update the device by id.
     *
     * @param deviceDTO the entity to update.
     * @return the persisted entity.
     */
    @Override
    public DeviceDTO update(DeviceDTO deviceDTO) {
        log.debug("Request to update Device : {}", deviceDTO);
        Optional<Device> deviceOptional = deviceRepository.findByDeviceId(deviceDTO.getDeviceId());
        if (!deviceOptional.isPresent()) {
            log.error("Update Device failed! : {}", deviceDTO);
            throw new BadRequestAlertException("Invalid deviceId", deviceDTO.getDeviceId(), "iddoesn'texists");
        }
        Device updatedDevice = deviceMapper.toEntity(deviceDTO);
        Device device = deviceOptional.get();
        if(updatedDevice.getOwnerId() != null){
            device.setOwnerId(updatedDevice.getOwnerId());
        }
        if(updatedDevice.getCreated() != null){
            device.setCreated(updatedDevice.getCreated());

        }
        if(updatedDevice.getPurchased() != null){
            device.setPurchased(updatedDevice.getPurchased());
        }
        device = deviceRepository.save(device);
        return deviceMapper.toDto(device);
    }
}
