package com.foxploit.ignio.devicedataservice.repository;
import com.foxploit.ignio.devicedataservice.domain.SensorData;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the SensorData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensorDataRepository extends MongoRepository<SensorData, String> {

}
