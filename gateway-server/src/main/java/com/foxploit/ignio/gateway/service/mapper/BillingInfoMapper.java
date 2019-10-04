package com.foxploit.ignio.gateway.service.mapper;

import com.foxploit.ignio.gateway.domain.*;
import com.foxploit.ignio.gateway.service.dto.BillingInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BillingInfo} and its DTO {@link BillingInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BillingInfoMapper extends EntityMapper<BillingInfoDTO, BillingInfo> {



    default BillingInfo fromId(String id) {
        if (id == null) {
            return null;
        }
        BillingInfo billingInfo = new BillingInfo();
        billingInfo.setId(id);
        return billingInfo;
    }
}
