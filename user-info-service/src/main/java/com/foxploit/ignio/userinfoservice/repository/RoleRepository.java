package com.foxploit.ignio.userinfoservice.repository;


import com.foxploit.ignio.userinfoservice.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, Long> {
    Optional<Role> findByRoleName(String name);
}
