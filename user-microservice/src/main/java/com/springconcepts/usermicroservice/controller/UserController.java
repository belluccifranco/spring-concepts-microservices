package com.springconcepts.usermicroservice.controller;

import com.springconcepts.usermicroservice.service.UserService;
import org.openapitools.api.UsersApi;
import org.openapitools.model.User;
import org.openapitools.model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController implements UsersApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<User>> getUsers() {
        return UsersApi.super.getUsers();
    }

    @Override
    public ResponseEntity<User> getUserById(String userId) {
        return UsersApi.super.getUserById(userId);
    }

    @Override
    public ResponseEntity<User> saveUser(UserDTO userDTO) {
        return UsersApi.super.saveUser(userDTO);
    }

//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getUsers() {
//        return new ResponseEntity<>(userService.findUsers(), HttpStatus.OK);
//    }
//
//    @GetMapping("/users/{userId}")
//    public ResponseEntity<User> getUserById(@PathVariable String userId) {
//        return new ResponseEntity<>(userService.findUserByUserId(userId), HttpStatus.OK);
//    }
//
//    @PostMapping("/users")
//    public ResponseEntity<User> saveUser(@RequestBody UserDTO userDTO) {
//        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
//    }
}
