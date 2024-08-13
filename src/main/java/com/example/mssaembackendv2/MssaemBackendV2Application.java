package com.example.mssaembackendv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MssaemBackendV2Application {

    public static void main(String[] args) {
        SpringApplication.run(MssaemBackendV2Application.class, args);
    }

}
