package com.foxploit.ignio.gateway.service;

import com.foxploit.ignio.gateway.service.dto.BillingInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.foxploit.ignio.gateway.domain.BillingInfo}.
 */
public interface BillingInfoService {

    /**
     * Save a billingInfo.
     *
     * @param billingInfoDTO the entity to save.
     * @return the persisted entity.
     */
    BillingInfoDTO save(BillingInfoDTO billingInfoDTO);

    /**
     * Get all the billingInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BillingInfoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" billingInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BillingInfoDTO> findOne(String id);

    /**
     * Delete the "id" billingInfo.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
