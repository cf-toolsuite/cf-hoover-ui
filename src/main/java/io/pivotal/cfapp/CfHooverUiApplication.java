package io.pivotal.cfapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CfHooverUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CfHooverUiApplication.class, args);
    }

}
