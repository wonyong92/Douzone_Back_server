package com.example.bootproject.system.interceptor;

import com.example.bootproject.vo.vo1.request.notification.SseEmitterWithEmployeeInformationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SseBroadCastingInterceptorForManager implements HandlerInterceptor {

    public static final List<SseEmitterWithEmployeeInformationDto> managerEmitters = new ArrayList<>();

    public SseBroadCastingInterceptorForManager() {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 컨트롤러 메서드 정보 가져오기
        Method method = handlerMethod.getMethod();
        Parameter[] parameters = method.getParameters();
        log.info("sse send employeeId = " + request.getRequestURI() + " " + parameters[0]);
        for (SseEmitterWithEmployeeInformationDto dto : managerEmitters) {
            try {
                dto.getSseEmitter().send(SseEmitter.event().data("Intercepted message").name("message"));
            } catch (Exception e) {
                // 예외 처리 로직 추가
            }
        }

    }
}
