package com.xuecheng.content.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient("system-api")
public interface SystemServiceFeignClient {
    @GetMapping("/system/feign-test")
    String feignTest();
}
