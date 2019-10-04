package com.foxploit.ignio.deviceanalysisservice.service.impl;

import com.foxploit.ignio.deviceanalysisservice.service.PredictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Service Implementation for managing Predictions.
 */
@Service
@Component
public class PredictServiceImpl implements PredictService {

    private static final Logger log = LoggerFactory.getLogger(PredictService.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");

    @Scheduled(fixedRate = 120000)
    public void predict() {
        log.info("The Prediction Task Initiated {}", dateFormat.format(new Date()));
    }

}
