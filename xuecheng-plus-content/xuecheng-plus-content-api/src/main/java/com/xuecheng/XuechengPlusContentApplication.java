package com.xuecheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class XuechengPlusContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(XuechengPlusContentApplication.class, args);
    }
}
