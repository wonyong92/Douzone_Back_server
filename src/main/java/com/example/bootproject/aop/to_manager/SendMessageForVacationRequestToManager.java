package com.example.bootproject.aop.to_manager;

import com.example.bootproject.repository.mapper1.EmployeeMapper1;
import com.example.bootproject.repository.mapper3.notification.NotificationMapper;
import com.example.bootproject.vo.vo1.request.notification.SseEmitterWithEmployeeInformationDto;
import com.example.bootproject.vo.vo1.request.sse.SseMessageInsertDto;
import com.example.bootproject.vo.vo1.request.vacation.VacationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.bootproject.ServletInitializer.REQUEST_LIST;
import static com.example.bootproject.system.interceptor.SseBroadCastingInterceptorForManager.managerEmitters;
import static com.example.bootproject.system.util.ValidationChecker.getLoginIdOrNull;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SendMessageForVacationRequestToManager {

    private final EmployeeMapper1 employeeMapper1;
    private final NotificationMapper notificationMapper;

    @Around("execution(* com.example.bootproject.controller.rest.employee.EmployeeController.requestVacation(..)) && args(dto,employeeId, req)")
    public Object sendVacationRequestMessageToManager(ProceedingJoinPoint joinPoint, VacationRequestDto dto, String employeeId, HttpServletRequest req) throws Throwable {
        log.info("AOP SendMessageForVacationRequestToManager");
        Object result = joinPoint.proceed();
        // After advice (you can add more logic here if needed)
        // 요청이 수행되면 requestDto에 generatedKey가 저장된다
        Long requestId = dto.getVacationRequestKey();
        log.info("after proceed -> sse message : requestVacation - requestId {}", requestId);

        String loginId = getLoginIdOrNull(req);

        String message = "New Appeal Request from " + loginId;
        for (SseEmitterWithEmployeeInformationDto emitterDto : managerEmitters) {
            //모든 매니저 목록을 확인해야한다 -> 모든 매니저에게 메세지를 전달하고, 매니저는 자신의 메세지 목록만 확인 할 수 있어야 한다
            List<String> managerEmployeeIds = notificationMapper.getManagerId();
            for (String managerId : managerEmployeeIds) {
                SseMessageInsertDto insertDto = new SseMessageInsertDto(managerId, message, "vacation", String.valueOf(requestId));
                try {
                    if (emitterDto.getEmployeeNumber().equals(managerId) && emitterDto.getUserType().equals("manager") && !emitterDto.getEmployeeNumber().equals(loginId)) {
                        notificationMapper.addUnreadMsgOfManager(insertDto);
                        log.info("inserted msg : {}", insertDto);
                        emitterDto.getSseEmitter().send(SseEmitter.event().data(message).name("message"));
                    }
                } catch (Exception e) {
                    for (SseEmitterWithEmployeeInformationDto reRegisteredEmitterDto : managerEmitters) {
                        if (emitterDto.getEmployeeNumber().equals(managerId) && emitterDto.getEmployeeNumber().equals(reRegisteredEmitterDto.getEmployeeNumber())) {
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
        return result;
    }
}

