package com.foxploit.ignio.devicedataservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foxploit.ignio.devicedataservice.domain.SensorData;
import com.foxploit.ignio.devicedataservice.repository.SensorDataRepository;
import com.foxploit.ignio.devicedataservice.service.dto.DeviceDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class DBPopulate {

    private static final Logger log = LoggerFactory.getLogger(DevicedataserviceApp.class);

    @Autowired
    SensorDataRepository sensorDataRepository;

    @PostConstruct
    public void populateInitialDataSet() {

        ObjectMapper mapper = new ObjectMapper();

        try{

            System.out.println(System.getProperty("user.dir") + "\\device-data-service\\src\\main\\resources\\sensor-data-2.json");

            File file = new File(System.getProperty("user.dir") + "\\device-data-service\\src\\main\\resources\\sensor-data-2.json");

            DeviceDataDTO deviceDataDTO = mapper.readValue(file, DeviceDataDTO.class);

            System.out.println(deviceDataDTO.getDeviceId());

            System.out.println(deviceDataDTO.getSensorData().size());

            int count = 0;
            for (SensorData sensorData : deviceDataDTO.getSensorData()) {
                if(sensorData.getDeviceId() != "null"){
                    sensorData.setDeviceId("NODEIGNIOF101");
                    sensorData = sensorDataRepository.save(sensorData);
                    System.out.println(sensorData);
                    count++;
                }
            }
            log.debug("Saved SensorData count : {}", count);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
