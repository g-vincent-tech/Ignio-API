package com.foxploit.ignio.devicedataservice.service.mapper;

import com.foxploit.ignio.devicedataservice.domain.*;
import com.foxploit.ignio.devicedataservice.service.dto.DeviceDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeviceData} and its DTO {@link DeviceDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeviceDataMapper extends EntityMapper<DeviceDataDTO, DeviceData> {

    DeviceData toEntity(DeviceDataDTO deviceDataDTO);

    default DeviceData fromId(String id) {
        if (id == null) {
            return null;
        }
        DeviceData deviceData = new DeviceData();
        deviceData.setId(id);
        return deviceData;
    }
}
