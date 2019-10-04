package com.foxploit.ignio.devicedataservice.web.rest;

import com.foxploit.ignio.devicedataservice.service.SensorDataService;
import com.foxploit.ignio.devicedataservice.web.rest.errors.BadRequestAlertException;
import com.foxploit.ignio.devicedataservice.service.dto.SensorDataDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.foxploit.ignio.devicedataservice.domain.SensorData}.
 */
@RestController
@RequestMapping("/api")
public class SensorDataResource {

    private final Logger log = LoggerFactory.getLogger(SensorDataResource.class);

    private static final String ENTITY_NAME = "devicedataserviceSensorData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SensorDataService sensorDataService;

    public SensorDataResource(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    /**
     * {@code POST  /sensor-data} : Create a new sensorData.
     *
     * @param sensorDataDTO the sensorDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sensorDataDTO, or with status {@code 400 (Bad Request)} if the sensorData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sensor-data")
    public ResponseEntity<SensorDataDTO> createSensorData(@Valid @RequestBody SensorDataDTO sensorDataDTO) throws URISyntaxException {
        log.debug("REST request to save SensorData : {}", sensorDataDTO);
        if (sensorDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new sensorData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SensorDataDTO result = sensorDataService.save(sensorDataDTO);
        return ResponseEntity.created(new URI("/api/sensor-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sensor-data} : Updates an existing sensorData.
     *
     * @param sensorDataDTO the sensorDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sensorDataDTO,
     * or with status {@code 400 (Bad Request)} if the sensorDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sensorDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sensor-data")
    public ResponseEntity<SensorDataDTO> updateSensorData(@Valid @RequestBody SensorDataDTO sensorDataDTO) throws URISyntaxException {
        log.debug("REST request to update SensorData : {}", sensorDataDTO);
        if (sensorDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SensorDataDTO result = sensorDataService.save(sensorDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sensorDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sensor-data} : get all the sensorData.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sensorData in body.
     */
    @GetMapping("/sensor-data")
    public ResponseEntity<List<SensorDataDTO>> getAllSensorData(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of SensorData");
        Page<SensorDataDTO> page = sensorDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sensor-data/:id} : get the "id" sensorData.
     *
     * @param id the id of the sensorDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sensorDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sensor-data/{id}")
    public ResponseEntity<SensorDataDTO> getSensorData(@PathVariable String id) {
        log.debug("REST request to get SensorData : {}", id);
        Optional<SensorDataDTO> sensorDataDTO = sensorDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sensorDataDTO);
    }

    /**
     * {@code GET  /sensor-data/all/:deviceId} : get the "deviceId" sensorData.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param deviceId the deviceId of the sensorDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sensorDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sensor-data/all/{deviceId}")
    public ResponseEntity<List<SensorDataDTO>> getAllSensorDataByDeviceId(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @PathVariable String deviceId) {
        log.debug("REST request to get a page of SensorData by deviceId : {}", deviceId);
        Page<SensorDataDTO> page = sensorDataService.findByDeviceId(pageable, deviceId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code DELETE  /sensor-data/:id} : delete the "id" sensorData.
     *
     * @param id the id of the sensorDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sensor-data/{id}")
    public ResponseEntity<Void> deleteSensorData(@PathVariable String id) {
        log.debug("REST request to delete SensorData : {}", id);
        sensorDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
