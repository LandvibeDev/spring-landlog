package com.landvibe.landlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;

@SpringBootApplication
public class LandlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LandlogApplication.class, args);
    }


}
