package com.CloudKeeper.CloudBalanceBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class CloudBalanceBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudBalanceBackendApplication.class, args);
    }
}
