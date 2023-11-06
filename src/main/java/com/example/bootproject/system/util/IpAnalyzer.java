package com.example.bootproject.system.util;

import javax.servlet.http.HttpServletRequest;

public class IpAnalyzer {

    public static String getClientIp(HttpServletRequest req) {
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }
}
