package com.example.naturesway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Collections;

@SpringBootApplication
@EnableScheduling
public class NaturesWayApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NaturesWayApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port",4444));
        app.run(args);
    }

}
