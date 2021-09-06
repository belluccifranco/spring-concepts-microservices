package com.springconcepts.usermicroservice;

import com.springconcepts.usermicroservice.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
