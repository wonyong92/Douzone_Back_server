package com.example.bootproject.system.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.example.bootproject.ServletInitializer.DELETE_PROCESS_LIST;


@Slf4j
@Component
public class DeleteQueueInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        List<String> processingList =DELETE_PROCESS_LIST;
        String[] targetRaw = request.getServletPath().split("/");
        String target = targetRaw[targetRaw.length-1];
        log.info("삭제 큐 동작 target {}", target);
        if (processingList.contains(target)) {
            log.info("삭제 큐에 현재 대상 정보가 작업 중에 있음 {} ", processingList);
            response.setStatus(HttpStatus.CONFLICT.value());
            return false;
        }
        log.info("삭제 대상 데이터 lock 설정 {}", target);
        processingList.add(target);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        List<String> processingList = DELETE_PROCESS_LIST;
        String[] targetRaw = request.getServletPath().split("/");
        String target = targetRaw[targetRaw.length-1];
        log.info("삭제 큐에 현재 진행 중인 작업 제거 : target = {}, queue = {} ", target, processingList);
        // 작업 완료 후 작업 큐에서 현재 요청 정보를 제거
        processingList.remove(target);
        log.info("삭제 큐에서 락 제거 후 : target = {}, queue = {} ", target, processingList);
    }
}
