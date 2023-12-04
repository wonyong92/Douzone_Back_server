package com.example.bootproject.system.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
@RequiredArgsConstructor
@Slf4j
public class CorsInterceptor implements HandlerInterceptor {
    @Value("${front.server.url}")
    private String frontServerUrl;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("cors interceptor");
        // CORS 설정
        response.setHeader("Access-Control-Allow-Origin", frontServerUrl);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }
}
