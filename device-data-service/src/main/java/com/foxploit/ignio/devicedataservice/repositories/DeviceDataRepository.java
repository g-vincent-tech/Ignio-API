package com.foxploit.ignio.devicedataservice.repositories;

import com.foxploit.ignio.devicedataservice.domain.DeviceData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceDataRepository extends MongoRepository<DeviceData, String> {

    List<DeviceData> findAllByDeviceId(String deviceId);

}
