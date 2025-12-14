package com.xuecheng.content.api.controller;

import com.xuecheng.content.feign.SystemServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignTestController {
    @Autowired
    private SystemServiceFeignClient systemServiceFeignClient;

    @GetMapping("/testFeign")
    public String testFeign() {
        return systemServiceFeignClient.feignTest();
    }
}
