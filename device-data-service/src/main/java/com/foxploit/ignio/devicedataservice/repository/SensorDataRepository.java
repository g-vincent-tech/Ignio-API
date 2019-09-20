package com.foxploit.ignio.devicedataservice.repository;

import com.foxploit.ignio.devicedataservice.domain.SensorData;
import com.foxploit.ignio.devicedataservice.service.dto.SensorDataDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the SensorData entity.
 */

@Repository
public interface SensorDataRepository extends MongoRepository<SensorData, String> {

    Page<SensorDataDTO> findByDeviceId(Pageable pageable, String deviceId);

}
