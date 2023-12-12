package com.example.bootproject.aop.to_manager;

import com.example.bootproject.repository.mapper1.EmployeeMapper1;
import com.example.bootproject.repository.mapper1.ManagerMapper1;
import com.example.bootproject.repository.mapper3.notification.NotificationMapper;
import com.example.bootproject.vo.vo1.request.appeal.AppealRequestDto;
import com.example.bootproject.vo.vo1.request.notification.SseEmitterWithEmployeeInformationDto;
import com.example.bootproject.vo.vo1.request.sse.SseMessageInsertDto;
import com.example.bootproject.vo.vo1.response.appeal.AppealRequestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.example.bootproject.ServletInitializer.REQUEST_LIST;
import static com.example.bootproject.system.interceptor.SseBroadCastingInterceptorForManager.managerEmitters;
import static com.example.bootproject.system.util.ValidationChecker.getLoginIdOrNull;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SendMessageForAppealRequestToManager {
    private final ManagerMapper1 managerMapper1;
    private final NotificationMapper notificationMapper;
    private final EmployeeMapper1 employeeMapper1;

    @Around("execution(* com.example.bootproject.controller.rest.employee.EmployeeController.makeAppealRequest(..)) && args(dto, req)")
    public Object sendAppealRequestMessageToManager(ProceedingJoinPoint joinPoint, AppealRequestDto dto, HttpServletRequest req) throws Throwable {
        String loginId = getLoginIdOrNull(req);
        ResponseEntity<AppealRequestResponseDto> result = (ResponseEntity<AppealRequestResponseDto>) joinPoint.proceed();
        Long requestId = dto.getAttendanceAppealRequestId();
        log.info("appeal request occur - reuqestId {}", requestId);

        if (result.getStatusCode() == HttpStatus.OK) {
            String message = "New Appeal Request from " + loginId;
            for (SseEmitterWithEmployeeInformationDto emitterDto : managerEmitters) {
                //모든 매니저 목록을 확인해야한다 -> 모든 매니저에게 메세지를 전달하고, 매니저는 자신의 메세지 목록만 확인 할 수 있어야 한다
                List<String> managerEmployeeIds = notificationMapper.getManagerId();
                for (String managerId : managerEmployeeIds) {
                    SseMessageInsertDto insertDto = new SseMessageInsertDto(managerId, message, "Appeal", String.valueOf(requestId));
                    try {
                        if (emitterDto.getUserType().equals("manager") && !emitterDto.getEmployeeNumber().equals(loginId)) {
                            notificationMapper.addUnreadMsgOfManager(insertDto);
                            log.info("inserted msg : {}", insertDto);
                            emitterDto.getSseEmitter().send(SseEmitter.event().data(message).name("message").id(String.valueOf(insertDto.getMessageId())));
                        }
                    } catch (Exception e) {
                        for (SseEmitterWithEmployeeInformationDto reRegisteredEmitterDto : managerEmitters) {
                            if (emitterDto.getEmployeeNumber().equals(reRegisteredEmitterDto.getEmployeeNumber())) {
                                log.info("타임아웃으로 인해 재전송");
                                reRegisteredEmitterDto.getSseEmitter().send(SseEmitter.event().data(message).name("message").id(String.valueOf(insertDto.getMessageId())));
                            }
                        }
                    }finally{
                        List<String> processingList = REQUEST_LIST.get(req.getRequestURI());
                        log.info("작업 큐에 현재 진행 중인 작업 제거 : loginId = {}, queue = {} ", loginId, processingList);
                        // 작업 완료 후 작업 큐에서 현재 요청 정보를 제거
                        processingList.remove(loginId);
                        log.info("작업 제거 후 : loginId = {}, queue = {} ", loginId, processingList);
                    }
                }
            }
        }
        return result;
    }
}

