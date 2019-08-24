package com.foxploit.ignio.userinfoservice.repository;

import com.foxploit.ignio.userinfoservice.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface UserRepository extends MongoRepository<User, Integer> {
    Optional<User> findByUsername(String userName);
}