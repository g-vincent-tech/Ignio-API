package com.foxploit.ignio.deviceanalysisservice.service.mapper;

import com.foxploit.ignio.deviceanalysisservice.domain.Alert;
import com.foxploit.ignio.deviceanalysisservice.service.dto.AlertDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Alert} and its DTO {@link AlertDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlertMapper extends EntityMapper<AlertDTO, Alert> {



    default Alert fromId(String id) {
        if (id == null) {
            return null;
        }
        Alert alert = new Alert();
        alert.setId(id);
        return alert;
    }
}
