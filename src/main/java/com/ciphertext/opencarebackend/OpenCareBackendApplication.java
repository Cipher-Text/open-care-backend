package com.ciphertext.opencarebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
public class OpenCareBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenCareBackendApplication.class, args);
    }

}
