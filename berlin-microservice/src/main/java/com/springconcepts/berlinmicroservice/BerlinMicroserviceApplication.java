package com.springconcepts.berlinmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BerlinMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BerlinMicroserviceApplication.class, args);
    }

}
