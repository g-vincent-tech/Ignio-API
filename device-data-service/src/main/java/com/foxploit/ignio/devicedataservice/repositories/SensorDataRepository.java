package com.foxploit.ignio.devicedataservice.repositories;

import com.foxploit.ignio.devicedataservice.models.SensorData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends MongoRepository<SensorData, String> {
}
