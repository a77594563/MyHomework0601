package com.web.portfolio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 創建時測試之 controller
 * @author user
 */

@RestController
@RequestMapping("/test")
public class TestController {
    
    @GetMapping("/hello")
    public String test(){
        return "Hello Test";
    }
}
