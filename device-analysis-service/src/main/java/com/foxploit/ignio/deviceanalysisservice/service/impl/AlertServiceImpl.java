package com.foxploit.ignio.deviceanalysisservice.service.impl;

import com.foxploit.ignio.deviceanalysisservice.domain.Alert;
import com.foxploit.ignio.deviceanalysisservice.repository.AlertRepository;
import com.foxploit.ignio.deviceanalysisservice.service.AlertService;
import com.foxploit.ignio.deviceanalysisservice.service.dto.AlertDTO;
import com.foxploit.ignio.deviceanalysisservice.service.mapper.AlertMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Alert}.
 */
@Service
public class AlertServiceImpl implements AlertService {

    private final Logger log = LoggerFactory.getLogger(AlertServiceImpl.class);

    private final AlertRepository alertRepository;

    private final AlertMapper alertMapper;

    public AlertServiceImpl(AlertRepository alertRepository, AlertMapper alertMapper) {
        this.alertRepository = alertRepository;
        this.alertMapper = alertMapper;
    }

    /**
     * Save a alert.
     *
     * @param alertDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AlertDTO save(AlertDTO alertDTO) {
        log.debug("Request to save Alert : {}", alertDTO);
        Alert alert = alertMapper.toEntity(alertDTO);
        alert = alertRepository.save(alert);
        return alertMapper.toDto(alert);
    }

    /**
     * Get all the alerts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<AlertDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Alerts");
        return alertRepository.findAll(pageable)
            .map(alertMapper::toDto);
    }


    /**
     * Get one alert by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<AlertDTO> findOne(String id) {
        log.debug("Request to get Alert : {}", id);
        return alertRepository.findById(id)
            .map(alertMapper::toDto);
    }

    /**
     * Delete the alert by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Alert : {}", id);
        alertRepository.deleteById(id);
    }
}
