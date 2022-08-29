package com.lyd;

import love.forte.simboot.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSimbot
@SpringBootApplication
public class RobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(RobotApplication.class,args);
    }

}
