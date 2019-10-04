package com.foxploit.ignio.gateway.web.rest;

import com.foxploit.ignio.gateway.service.BillingInfoService;
import com.foxploit.ignio.gateway.service.dto.BillingInfoDTO;
import com.foxploit.ignio.gateway.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.foxploit.ignio.gateway.domain.BillingInfo}.
 */
@RestController
@RequestMapping("/api")
public class BillingInfoResource {

    private final Logger log = LoggerFactory.getLogger(BillingInfoResource.class);

    private static final String ENTITY_NAME = "billingInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillingInfoService billingInfoService;

    public BillingInfoResource(BillingInfoService billingInfoService) {
        this.billingInfoService = billingInfoService;
    }

    /**
     * {@code POST  /billing-info} : Create a new billingInfo.
     *
     * @param billingInfoDTO the billingInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billingInfoDTO, or with status {@code 400 (Bad Request)} if the billingInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/billing-info")
    public ResponseEntity<BillingInfoDTO> createBillingInfo(@Valid @RequestBody BillingInfoDTO billingInfoDTO) throws URISyntaxException {
        log.debug("REST request to save BillingInfo : {}", billingInfoDTO);
        if (billingInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new billingInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillingInfoDTO result = billingInfoService.save(billingInfoDTO);
        return ResponseEntity.created(new URI("/api/billing-info/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /billing-info} : Updates an existing billingInfo.
     *
     * @param billingInfoDTO the billingInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billingInfoDTO,
     * or with status {@code 400 (Bad Request)} if the billingInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billingInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/billing-info")
    public ResponseEntity<BillingInfoDTO> updateBillingInfo(@RequestBody BillingInfoDTO billingInfoDTO) throws URISyntaxException {
        log.debug("REST request to update BillingInfo : {}", billingInfoDTO);
        if (billingInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillingInfoDTO result = billingInfoService.save(billingInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, billingInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /billing-info} : get all the billingInfos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billingInfos in body.
     */
    @GetMapping("/billing-infos")
    public ResponseEntity<List<BillingInfoDTO>> getAllBillingInfos(Pageable pageable) {
        log.debug("REST request to get a page of BillingInfos");
        Page<BillingInfoDTO> page = billingInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /billing-info/:id} : get the "id" billingInfo.
     *
     * @param id the id of the billingInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billingInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/billing-info/{id}")
    public ResponseEntity<BillingInfoDTO> getBillingInfo(@PathVariable String id) {
        log.debug("REST request to get BillingInfo : {}", id);
        Optional<BillingInfoDTO> billingInfoDTO = billingInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billingInfoDTO);
    }

    /**
     * {@code DELETE  /billing-info/:id} : delete the "id" billingInfo.
     *
     * @param id the id of the billingInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/billing-info/{id}")
    public ResponseEntity<Void> deleteBillingInfo(@PathVariable String id) {
        log.debug("REST request to delete BillingInfo : {}", id);
        billingInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
