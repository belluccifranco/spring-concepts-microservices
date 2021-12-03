package com.springconcepts.usermicroservice.repository;

import com.springconcepts.usermicroservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
