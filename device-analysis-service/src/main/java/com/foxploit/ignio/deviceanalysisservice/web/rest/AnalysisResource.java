package com.foxploit.ignio.deviceanalysisservice.web.rest;

import com.foxploit.ignio.deviceanalysisservice.domain.Alert;
import com.foxploit.ignio.deviceanalysisservice.repository.AlertRepository;
import com.foxploit.ignio.deviceanalysisservice.repository.DeviceRepository;
import com.foxploit.ignio.deviceanalysisservice.service.AnalysisService;
import com.foxploit.ignio.deviceanalysisservice.service.DeviceService;
import com.foxploit.ignio.deviceanalysisservice.service.dto.DeviceDTO;
import com.foxploit.ignio.deviceanalysisservice.service.dto.SensorDataDTO;
import com.foxploit.ignio.deviceanalysisservice.service.impl.AlertTypeImpl;
import com.foxploit.ignio.deviceanalysisservice.service.impl.AnalysisServiceImpl;
import com.foxploit.ignio.deviceanalysisservice.service.mapper.DeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AnalysisResource {

    private static final Logger log = LoggerFactory.getLogger(AnalysisService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");

    private final String DEVICE_DATA_SERVICE = "devicedataservice";

    private final String GATEWAY_SERVICE = "igniogateway";

    private final String DEVICEID = "NODEIGNIOF101";

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    private AnalysisServiceImpl analysisService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private DeviceService deviceService;
    private DeviceMapper deviceMapper;

    public AnalysisResource(RestTemplate restTemplate, AnalysisServiceImpl analysisService, DeviceRepository deviceRepository, AlertRepository alertRepository, DeviceService deviceService) {
        this.restTemplate = restTemplate;
        this.analysisService = analysisService;
        this.deviceRepository = deviceRepository;
        this.deviceService = deviceService;
        this.alertRepository = alertRepository;
    }

    // Default 2 min 120000
    @Scheduled(fixedRate = 120000)
    public void analyze() {

        URI uri = UriComponentsBuilder.fromUriString("//" + DEVICE_DATA_SERVICE + "/api/sensor-data/all/" + DEVICEID + "?size=8").build().toUri();

        log.info("The Analysis Task Initiated {}", dateFormat.format(new Date()));
        SensorDataDTO[] sensorDataDTOs;
        int weight = 0;
        float tempSlope, coSlope, lpSlope, particleSlope;
        try{
            sensorDataDTOs = restTemplate.getForObject(uri, SensorDataDTO[].class);

            log.debug("SensorData Response : {}", sensorDataDTOs);

            assert sensorDataDTOs != null;
            List<Float> temperature_data_set = new ArrayList<>();
            List<Float> co_ppm__data_set = new ArrayList<>();
            List<Float> lp_gas_ppm__data_set = new ArrayList<>();
            List<Float> particle_ppm__data_set = new ArrayList<>();

            for (SensorDataDTO sensorDataDTO : sensorDataDTOs) {
                temperature_data_set.add(sensorDataDTO.getTemperature());
                co_ppm__data_set.add(sensorDataDTO.getCo_ppm());
                lp_gas_ppm__data_set.add(sensorDataDTO.getLp_gas_ppm());
                particle_ppm__data_set.add(sensorDataDTO.getParticle_ppm());
            }

            tempSlope = analysisService.approximateRegression(temperature_data_set);
            coSlope = analysisService.approximateRegression(co_ppm__data_set);
            lpSlope = analysisService.approximateRegression(lp_gas_ppm__data_set);
            particleSlope = analysisService.approximateRegression(particle_ppm__data_set);

            weight += analysisService.resolveSlopeLevel(tempSlope);
            weight += analysisService.resolveSlopeLevel(coSlope);
            weight += analysisService.resolveSlopeLevel(lpSlope);
            weight += analysisService.resolveSlopeLevel(particleSlope);

            log.debug("Total weight : {}", weight);
            int alertLevel = analysisService.resolveAlertLevel(weight);
            log.debug("Alert Level: {}", alertLevel);
            if (alertLevel >= 2) {
                alert(alertLevel, DEVICEID);
            }
        } catch (ResourceAccessException exception) {
            log.error("Request call was not successful! Might be due to invalid token or unavailable resource ", exception);
        }

    }

    public void alert(int alertType, String deviceId) {

        URI uri = UriComponentsBuilder.fromUriString("//" + GATEWAY_SERVICE + "/api/alert").build().toUri();

        log.info("The Alert Task Initiated {} for {}", dateFormat.format(new Date()), deviceId);

        DeviceDTO deviceDTO = findDeviceByDeviceId(deviceId);

        if (deviceDTO != null) {
            Alert alert = new Alert().deviceId(deviceDTO.getDeviceId()).ownerId(deviceDTO.getOwnerId()).alertType(AlertTypeImpl.alertMessageResolve(alertType)).timestamp(LocalDateTime.now());
            alertRepository.save(alert);
            try{
                restTemplate.put(uri, alert);
            } catch (ResourceAccessException exception) {
                log.error("Request call was not successful! Might be due to invalid token or unavailable resource ", exception);
            }
        }
    }

    public DeviceDTO findDeviceByDeviceId(String deviceId) {

        URI uri = UriComponentsBuilder.fromUriString("//" + DEVICE_DATA_SERVICE + "/api/device/" + deviceId).build().toUri();

        log.info("The Analysis Task Initiated {}", dateFormat.format(new Date()));

        DeviceDTO deviceDTO = null;

        try{
            deviceDTO = restTemplate.getForObject(uri, DeviceDTO.class);

            log.debug("DeviceDTO Response : {}", deviceDTO);

            assert deviceDTO != null;

        } catch (RestClientException e) {
            log.error("Request call was not successful! Might be due to invalid token or unavailable resource in separate method", e);
        }

        return deviceDTO;

    }

}
