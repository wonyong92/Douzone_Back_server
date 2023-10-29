package com.example.bootproject.system.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class IpAnalyzer {

    public static String getClientIp(HttpServletRequest req){
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }
}
