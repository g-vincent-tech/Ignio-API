package com.foxploit.ignio.devicedataservice.config.dbmigrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxploit.ignio.devicedataservice.DevicedataserviceApp;
import com.foxploit.ignio.devicedataservice.domain.SensorData;
import com.foxploit.ignio.devicedataservice.service.dto.DeviceDataDTO;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.io.IOException;

/**
 * Creates the initial database setup.
 */
@ChangeLog(order = "001")
public class InitialSetupMigration {

    private static final Logger log = LoggerFactory.getLogger(DevicedataserviceApp.class);

    @ChangeSet(order = "01", author = "initiator", id = "01-addInitialSensorData")
    public void addInitialSensorData(MongoTemplate mongoTemplate) {

        ObjectMapper mapper = new ObjectMapper();

        try{
            // System.out.println(System.getProperty("user.dir") + "\\device-data-service\\src\\main\\resources\\initial-sensor-data.json");

            File file = new File(System.getProperty("user.dir") + "\\device-data-service\\src\\main\\resources\\initial-sensor-data.json");
            DeviceDataDTO deviceDataDTO = mapper.readValue(file, DeviceDataDTO.class);
            System.out.println(deviceDataDTO.getDeviceId());
            log.info("Found SensorData: {} from deviceID: {}", deviceDataDTO.getSensorData().size(), deviceDataDTO.getDeviceId());
            int count = 0;

            for (SensorData sensorData : deviceDataDTO.getSensorData()) {
                if (sensorData.getDeviceId() != "null") {
                    sensorData.setDeviceId("NODEIGNIOF101");
                    mongoTemplate.save(sensorData);
                    count++;
                }
            }
            log.debug("Saved SensorData count : {}", count);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
