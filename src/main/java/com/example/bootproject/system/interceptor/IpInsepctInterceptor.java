package com.example.bootproject.system.interceptor;

import com.example.bootproject.system.util.IpAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class IpInsepctInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = IpAnalyzer.getClientIp(request);
        log.info("incoming ip : {}", ip);
        String[] ipAddressParts = ip.split("\\.|:");
        if (ipAddressParts.length == 8) {
            log.info("ip v6 filtering");
            return  (Integer.parseInt((ipAddressParts[0]),16) <= 65535) &&
                    (Integer.parseInt((ipAddressParts[1]),16) <= 65535) &&
                    (Integer.parseInt((ipAddressParts[2]),16) <= 65535) &&
                    (Integer.parseInt((ipAddressParts[3]),16) <= 65535) &&
                    (Integer.parseInt((ipAddressParts[4]),16) <= 65535) &&
                    (Integer.parseInt((ipAddressParts[5]),16) <= 65535) &&
                    (Integer.parseInt((ipAddressParts[6]),16) <= 65535) &&
                    (Integer.parseInt((ipAddressParts[7]),16) <= 65535);
        } else if(ipAddressParts.length == 4){
            log.info("ip v4 filtering");
            return (Integer.parseInt(ipAddressParts[0]) <= 192) &&
                    (Integer.parseInt(ipAddressParts[1]) <= 255) &&
                    (Integer.parseInt(ipAddressParts[2]) <= 255) &&
                    (Integer.parseInt(ipAddressParts[3]) <= 255);
        } else {
            log.info("invalid ip format {} ",ip);
            return false;
        }
    }
}
