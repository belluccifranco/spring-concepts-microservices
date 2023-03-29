package com.springconcepts.gatewaymicroservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebRouter {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(r -> r.path("/register/**").uri("lb://register-microservice"))
                .route(r -> r.path("/user/**").uri("lb://user-microservice"))
                .route(r -> r.path("/order/**").uri("lb://order-microservice"))
                .route(r -> r.path("/payment/**").uri("lb://payment-microservice"))
                .build();
    }
}
