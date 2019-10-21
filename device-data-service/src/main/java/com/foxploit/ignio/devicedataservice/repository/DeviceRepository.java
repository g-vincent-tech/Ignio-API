package com.foxploit.ignio.devicedataservice.repository;

import com.foxploit.ignio.devicedataservice.domain.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the Device entity.
 */
@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {

    Optional<Device> findByDeviceId(String deviceId);

}
