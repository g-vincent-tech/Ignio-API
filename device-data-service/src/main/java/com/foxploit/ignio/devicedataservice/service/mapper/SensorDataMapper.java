package com.foxploit.ignio.devicedataservice.service.mapper;

import com.foxploit.ignio.devicedataservice.domain.*;
import com.foxploit.ignio.devicedataservice.service.dto.SensorDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SensorData} and its DTO {@link SensorDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeviceDataMapper.class})
public interface SensorDataMapper extends EntityMapper<SensorDataDTO, SensorData> {

    SensorDataDTO toDto(SensorDataDTO sensorDataDTO);

    default SensorData fromId(String id) {
        if (id == null) {
            return null;
        }
        SensorData sensorData = new SensorData();
        sensorData.setId(id);
        return sensorData;
    }
}
