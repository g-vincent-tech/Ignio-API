package com.foxploit.ignio.devicedataservice.repositories;

import com.foxploit.ignio.devicedataservice.models.DeviceData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceDataRepository extends MongoRepository<DeviceData, String> {

    List<DeviceData> findAllByDeviceId(String deviceId);

}
