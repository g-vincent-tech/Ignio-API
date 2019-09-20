package com.foxploit.ignio.devicedataservice.service.mapper;

import com.foxploit.ignio.devicedataservice.domain.*;
import com.foxploit.ignio.devicedataservice.service.dto.DeviceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Device} and its DTO {@link DeviceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeviceMapper extends EntityMapper<DeviceDTO, Device> {

    default Device fromId(String id) {
        if (id == null) {
            return null;
        }
        Device device = new Device();
        device.setId(id);
        return device;
    }
}
