package com.foxploit.ignio.gateway.service.impl;

import com.foxploit.ignio.gateway.service.BillingInfoService;
import com.foxploit.ignio.gateway.domain.BillingInfo;
import com.foxploit.ignio.gateway.repository.BillingInfoRepository;
import com.foxploit.ignio.gateway.service.dto.BillingInfoDTO;
import com.foxploit.ignio.gateway.service.mapper.BillingInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BillingInfo}.
 */
@Service
public class BillingInfoServiceImpl implements BillingInfoService {

    private final Logger log = LoggerFactory.getLogger(BillingInfoServiceImpl.class);

    private final BillingInfoRepository billingInfoRepository;

    private final BillingInfoMapper billingInfoMapper;

    public BillingInfoServiceImpl(BillingInfoRepository billingInfoRepository, BillingInfoMapper billingInfoMapper) {
        this.billingInfoRepository = billingInfoRepository;
        this.billingInfoMapper = billingInfoMapper;
    }

    /**
     * Save a billingInfo.
     *
     * @param billingInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BillingInfoDTO save(BillingInfoDTO billingInfoDTO) {
        log.debug("Request to save BillingInfo : {}", billingInfoDTO);
        BillingInfo billingInfo = billingInfoMapper.toEntity(billingInfoDTO);
        billingInfo = billingInfoRepository.save(billingInfo);
        return billingInfoMapper.toDto(billingInfo);
    }

    /**
     * Get all the billingInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<BillingInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BillingInfos");
        return billingInfoRepository.findAll(pageable)
            .map(billingInfoMapper::toDto);
    }


    /**
     * Get one billingInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<BillingInfoDTO> findOne(String id) {
        log.debug("Request to get BillingInfo : {}", id);
        return billingInfoRepository.findById(id)
            .map(billingInfoMapper::toDto);
    }

    /**
     * Delete the billingInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete BillingInfo : {}", id);
        billingInfoRepository.deleteById(id);
    }
}
