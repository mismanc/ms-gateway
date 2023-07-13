package com.ms.gateaway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local-discovery")
public class LoadBalancedRoutes {

    @Bean
    public RouteLocator loadBalancerRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/soda*", "/api/v1/soda/*", "/api/v1/soda/upc/*")
                        .uri("lb://soda-service"))
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("lb://order-service"))
                .route(r -> r.path("/api/v1/soda/*/inventory")
                        .filters(f -> f.circuitBreaker(c -> c.setName("inventoryCB").setFallbackUri("forward:/inventory-failover")
                                .setRouteId("inv-failover")))
                        .uri("lb://inventory-service"))
                .route(r -> r.path("/inventory-failover/**")
                        .uri("lb://inventory-failover"))
                .build();
    }

}
