package com.foxploit.ignio.devicedataservice.web.rest;

import com.foxploit.ignio.devicedataservice.service.DeviceDataService;
import com.foxploit.ignio.devicedataservice.web.rest.errors.BadRequestAlertException;
import com.foxploit.ignio.devicedataservice.service.dto.DeviceDataDTO;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.foxploit.ignio.devicedataservice.domain.DeviceData}.
 */
@RestController
@RequestMapping("/api")
public class DeviceDataResource {

    private final Logger log = LoggerFactory.getLogger(DeviceDataResource.class);

    private static final String ENTITY_NAME = "devicedataserviceDeviceData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceDataService deviceDataService;

    public DeviceDataResource(DeviceDataService deviceDataService) {
        this.deviceDataService = deviceDataService;
    }

    /**
     * {@code POST  /device-data} : Create a new deviceData.
     *
     * @param deviceDataDTO the deviceDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceDataDTO, or with status {@code 400 (Bad Request)} if the deviceData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-data")
    public ResponseEntity<DeviceDataDTO> createDeviceData(@Valid @RequestBody DeviceDataDTO deviceDataDTO) throws URISyntaxException {
        log.debug("REST request to save DeviceData : {}", deviceDataDTO);
        if (deviceDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new deviceData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceDataDTO result = deviceDataService.save(deviceDataDTO);
        return ResponseEntity.created(new URI("/api/device-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-data} : Updates an existing deviceData.
     *
     * @param deviceDataDTO the deviceDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceDataDTO,
     * or with status {@code 400 (Bad Request)} if the deviceDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-data")
    public ResponseEntity<DeviceDataDTO> updateDeviceData(@Valid @RequestBody DeviceDataDTO deviceDataDTO) throws URISyntaxException {
        log.debug("REST request to update DeviceData : {}", deviceDataDTO);
        if (deviceDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeviceDataDTO result = deviceDataService.save(deviceDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deviceDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /device-data} : get all the deviceData.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceData in body.
     */
    @GetMapping("/device-data")
    public ResponseEntity<List<DeviceDataDTO>> getAllDeviceData(Pageable pageable) {
        log.debug("REST request to get a page of DeviceData");
        Page<DeviceDataDTO> page = deviceDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /device-data/:id} : get the "id" deviceData.
     *
     * @param id the id of the deviceDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-data/{id}")
    public ResponseEntity<DeviceDataDTO> getDeviceData(@PathVariable String id) {
        log.debug("REST request to get DeviceData : {}", id);
        Optional<DeviceDataDTO> deviceDataDTO = deviceDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceDataDTO);
    }

    /**
     * {@code DELETE  /device-data/:id} : delete the "id" deviceData.
     *
     * @param id the id of the deviceDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-data/{id}")
    public ResponseEntity<Void> deleteDeviceData(@PathVariable String id) {
        log.debug("REST request to delete DeviceData : {}", id);
        deviceDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
