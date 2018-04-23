package com.april.testproject;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Component
@RestController
@RequestMapping("/api/1")
public class ApiController {

    @GetMapping("test")
    public Object test(){
        return new HashMap<>();
    }
}
