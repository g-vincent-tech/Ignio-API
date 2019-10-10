package com.foxploit.ignio.deviceanalysisservice.web.rest;

import com.foxploit.ignio.deviceanalysisservice.service.AnalysisService;
import com.foxploit.ignio.deviceanalysisservice.service.dto.SensorDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class AnalysisResource {

    private static final Logger log = LoggerFactory.getLogger(AnalysisService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");

    private final String DEVICE_DATA_SERVICE = "devicedataservice";

    //        http://localhost:8080/services/devicedataservice

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    // Default 2 min 120000
    @Scheduled(fixedRate = 10000)
    public void analyze() {

        URI uri = UriComponentsBuilder.fromUriString("//" + DEVICE_DATA_SERVICE + "/api/sensor-data/all/NODEIGNIOF101?size=8").build().toUri();

        log.info("The Analysis Task Initiated {}", dateFormat.format(new Date()));
        SensorDataDTO[] sensorDataDTOs = restTemplate.getForObject(uri, SensorDataDTO[].class);
        int[] temperature_DataSet = new int[8];
        int[] co_ppm_DataSet = new int[8];
        int[] lp_gas_ppm_DataSet = new int[8];
        int[] particle_ppm_DataSet = new int[8];
        assert sensorDataDTOs != null;
        for (SensorDataDTO sensorDataDTO : sensorDataDTOs) {
            System.out.println(sensorDataDTO);
        }

    }

}
