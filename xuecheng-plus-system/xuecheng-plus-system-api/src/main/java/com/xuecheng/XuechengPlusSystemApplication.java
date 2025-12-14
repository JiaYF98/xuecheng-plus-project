package com.xuecheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>
 * 系统管理启动类
 * </p>
 */
@EnableScheduling
@SpringBootApplication
@EnableFeignClients
public class XuechengPlusSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(XuechengPlusSystemApplication.class, args);
    }
}