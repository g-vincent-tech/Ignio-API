package com.foxploit.ignio.deviceanalysisservice.web.rest;

import com.foxploit.ignio.deviceanalysisservice.service.AnalysisService;
import com.foxploit.ignio.deviceanalysisservice.service.dto.SensorDataDTO;
import com.foxploit.ignio.deviceanalysisservice.service.impl.AnalysisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AnalysisResource {

    private static final Logger log = LoggerFactory.getLogger(AnalysisService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");

    private final String DEVICE_DATA_SERVICE = "devicedataservice";

    private int dataSetSize;

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    private AnalysisServiceImpl analysisService;

    // Default 2 min 120000
    @Scheduled(fixedRate = 10000)
    public void analyze() {

        URI uri = UriComponentsBuilder.fromUriString("//" + DEVICE_DATA_SERVICE + "/api/sensor-data/all/NODEIGNIOF101?size=8").build().toUri();

        log.info("The Analysis Task Initiated {}", dateFormat.format(new Date()));
        SensorDataDTO[] sensorDataDTOs;
        int slopeDeviationCount = 0;
        float tempSlope, coSlope, lpSlope, particleSlope;
        try{
            sensorDataDTOs = restTemplate.getForObject(uri, SensorDataDTO[].class);
            assert sensorDataDTOs != null;
            dataSetSize = sensorDataDTOs.length;
            List<Float> temperature_data_set = new ArrayList<>();
            List<Float> co_ppm__data_set = new ArrayList<>();
            List<Float> lp_gas_ppm__data_set = new ArrayList<>();
            List<Float> particle_ppm__data_set = new ArrayList<>();

            System.out.println(sensorDataDTOs.length);
            for (SensorDataDTO sensorDataDTO : sensorDataDTOs) {
                temperature_data_set.add(sensorDataDTO.getTemperature());
                co_ppm__data_set.add(sensorDataDTO.getCo_ppm());
                lp_gas_ppm__data_set.add(sensorDataDTO.getLp_gas_ppm());
                particle_ppm__data_set.add(sensorDataDTO.getParticle_ppm());
                System.out.println(sensorDataDTO);
            }
            tempSlope = analysisService.approximateRegression(temperature_data_set);
            coSlope = analysisService.approximateRegression(co_ppm__data_set);
            lpSlope = analysisService.approximateRegression(lp_gas_ppm__data_set);
            particleSlope = analysisService.approximateRegression(particle_ppm__data_set);

            System.out.println(tempSlope);
            System.out.println(coSlope);
            System.out.println(lpSlope);
            System.out.println(particleSlope);

        } catch (ResourceAccessException exception) {
            log.error("Request call was not successful! Might be due to invalid token or unavailable resource ", exception);
        }

    }

}
