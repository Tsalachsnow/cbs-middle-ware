package com.cbs.middleware;

import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CBSPaymentGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CBSPaymentGatewayApplication.class, args);
    }
}
