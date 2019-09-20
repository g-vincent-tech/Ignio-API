package com.foxploit.ignio.devicedataservice.repository;

import com.foxploit.ignio.devicedataservice.domain.DeviceData;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the DeviceData entity.
 */

@Repository
public interface DeviceDataRepository extends MongoRepository<DeviceData, String> {

}
