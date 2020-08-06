package com.example.spring.session.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
public class OneApplicationRoot {
    public static void main(String[] args) {
        SpringApplication.run(OneApplicationRoot.class, args);
    }
}
