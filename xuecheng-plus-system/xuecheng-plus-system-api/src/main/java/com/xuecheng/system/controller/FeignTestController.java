package com.xuecheng.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FeignTestController {
    @GetMapping("/feign-test")
    public String feignTest() {
        log.info("feign接口调用测试！");
        return "feign接口调用成功！";
    }
}
