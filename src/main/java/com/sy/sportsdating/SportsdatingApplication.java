package com.sy.sportsdating;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sy.sportsdating.mapper")
public class SportsdatingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsdatingApplication.class, args);
    }

}
