package com.foxploit.ignio.deviceanalysisservice.service.mapper;

import com.foxploit.ignio.deviceanalysisservice.domain.Device;
import com.foxploit.ignio.deviceanalysisservice.service.dto.DeviceDTO;
import org.mapstruct.Mapper;

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
