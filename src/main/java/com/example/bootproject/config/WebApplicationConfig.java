package com.example.bootproject.config;

import com.example.bootproject.system.interceptor.LogginInterceptor;
import com.example.bootproject.system.interceptor.SseBroadCastingInterceptorForManager;
import com.example.bootproject.system.interceptor.WorkingQueueInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
public class WebApplicationConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogginInterceptor());
        // TODO : 다른 오쳥에 대해서도 사용할 수 있도록 URI 추가 필요
//        registry.addInterceptor(new WorkingQueueInterceptor()).addPathPatterns("/employee/vacation", "/employee/appeal", "/manager/vacation/process", "/manager/vacation/modify");
//        registry.addInterceptor(new SseBroadCastingInterceptorForManager()).addPathPatterns("/manager/appeal/process","/manager/vacation/process");
        /*
        * /manager/appeal/process -> dto에서 찾아야함
        * /manager/vacation/process -> dto에서 찾아야함
        * */
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}