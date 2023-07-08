package com.ms.gateaway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalHostRouteConfig {

    @Bean
    public RouteLocator localeHostRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/api/v1/soda*","/api/v1/soda/*", "/api/v1/soda/upc/*")
                        .uri("http://localhost:8080"))
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/api/v1/soda/*/inventory")
                        .uri("http://localhost:8082"))
                .build();
    }

}
