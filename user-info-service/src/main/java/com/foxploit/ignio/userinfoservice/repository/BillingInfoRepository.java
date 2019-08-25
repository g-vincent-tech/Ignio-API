package com.foxploit.ignio.userinfoservice.repository;

import com.foxploit.ignio.userinfoservice.domain.BillingInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BillingInfoRepository extends MongoRepository<BillingInfo, String> {
    Optional<BillingInfo> findById(String id);
}
