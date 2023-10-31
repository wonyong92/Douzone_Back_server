package com.example.bootproject.config;

import com.example.bootproject.system.interceptor.LoginInterceptor;
import com.example.bootproject.system.interceptor.WorkingQueueInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebApplicationConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor());
        // TODO : 다른 오쳥에 대해서도 사용할 수 있도록 URI 추가 필요
        registry.addInterceptor(new WorkingQueueInterceptor()).addPathPatterns("/employee/vacation", "/employee/appeal");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}