package com.priyank.JournalApplication.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/test")
public class HealthCheckController {
    @GetMapping("check")
    public String getMethodName() {
        return "app is running";
    }
    
}
