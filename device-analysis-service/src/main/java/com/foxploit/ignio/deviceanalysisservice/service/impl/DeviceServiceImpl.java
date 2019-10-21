package com.foxploit.ignio.deviceanalysisservice.service.impl;

import com.foxploit.ignio.deviceanalysisservice.domain.Device;
import com.foxploit.ignio.deviceanalysisservice.repository.DeviceRepository;
import com.foxploit.ignio.deviceanalysisservice.service.DeviceService;
import com.foxploit.ignio.deviceanalysisservice.service.dto.DeviceDTO;
import com.foxploit.ignio.deviceanalysisservice.service.mapper.DeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * Get one device by deviceId.
     *
     * @param deviceId the deviceId of the entity.
     * @return the entity.
     */
    @Override
    public Optional<DeviceDTO> findOneByDeviceId(String deviceId) {
        log.debug("Request to get Device : {}", deviceId);
        return deviceRepository.findByDeviceId(deviceId).map(deviceMapper::toDto);
    }

}
