package com.example.bootproject.system.interceptor;

import com.example.bootproject.system.util.IpAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.example.bootproject.ServletInitializer.REQUEST_LIST;


@Slf4j
@Component
public class WorkingQueueInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String loginId = (String) session.getAttribute("loginId");
        String ip = (String) session.getAttribute("ip");

        log.info("요청 : {}", request.getRequestURI());

        List<String> processingList = REQUEST_LIST.get(request.getRequestURI());

        if (loginId == null) {
            log.info("로그인 정보 없는 요청 발생");
            response.setStatus(403);
            return false;
        }

        if (!ip.equals(IpAnalyzer.getClientIp(request))) {
            log.info("ip 변경 발생 session : {} client : [] ", ip, IpAnalyzer.getClientIp(request));
            response.setStatus(403);
            return false;
        }
        if (processingList.contains(loginId)) {
            log.info("작업 큐에 현재 진행 중인 작업 있음 {} ",processingList);
            response.setStatus(HttpStatus.CONFLICT.value());
            return false;
        }

        processingList.add(loginId);

        return true;
    }

//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HttpSession session = request.getSession();
//        String loginId = (String) session.getAttribute("loginId");
//        List<String> processingList = REQUEST_LIST.get(request.getRequestURI());
//        log.info("작업 큐에 현재 진행 중인 작업 제거 : loginId = {}, queue = {} ", loginId, processingList);
//        // 작업 완료 후 작업 큐에서 현재 요청 정보를 제거
//        processingList.remove(loginId);
//        log.info("작업 제거 후 : loginId = {}, queue = {} ", loginId, processingList);
//    }
}
