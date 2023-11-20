package com.example.bootproject.controller.rest.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class SystemController {
    @GetMapping("/system/isLogin")
    public ResponseEntity isLogin() {
        log.info("isLogin");
        return ResponseEntity.ok(Map.of("userType", "employee", "success", "true"));
    }
}
