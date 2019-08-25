package com.foxploit.ignio.devicedataservice.repositories;

import com.foxploit.ignio.devicedataservice.domain.SensorData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends MongoRepository<SensorData, String> {
}
