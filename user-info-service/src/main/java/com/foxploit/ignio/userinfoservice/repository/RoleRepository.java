package com.foxploit.ignio.userinfoservice.repository;

import com.foxploit.ignio.userinfoservice.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {
    Optional<Role> findByRoleName(String name);
}
