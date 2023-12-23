package com.example.bootproject.aop.to_employee;

import com.example.bootproject.repository.mapper1.ManagerMapper1;
import com.example.bootproject.repository.mapper3.notification.NotificationMapper;
import com.example.bootproject.repository.mapper3.vacation.VacationMapper;
import com.example.bootproject.vo.vo1.request.notification.SseEmitterWithEmployeeInformationDto;
import com.example.bootproject.vo.vo1.request.sse.SseMessageInsertDto;
import com.example.bootproject.vo.vo1.request.vacation.VacationProcessRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

import static com.example.bootproject.ServletInitializer.REQUEST_LIST;
import static com.example.bootproject.system.interceptor.SseBroadCastingInterceptorForEmployee.employeeEmitters;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SendMessageForProcessVacationRequestToEmployee {
    private final ManagerMapper1 managerMapper1;
    private final NotificationMapper notificationMapper;
    private final VacationMapper vacationMapper;

    @Around("execution(* com.example.bootproject.controller.rest.manager.ManagerController.processVacationRequest(..)) && args(dto, req)")
    public Object sendMessageForProcessVacationRequestMessageToEmployee(ProceedingJoinPoint joinPoint, VacationProcessRequestDto dto, HttpServletRequest req) throws Throwable {

        Object result = joinPoint.proceed();
        Long requestId = dto.getVacationRequestKey();
        log.info("target vacation request id = {}", requestId);
        if (requestId != null) {

            String employeeId = managerMapper1.getEmployeeIdAttendanceAppealByVacationRequestId(requestId);
            LocalDate startDate = vacationMapper.findByVacationRequestKey(dto.getVacationRequestKey()).getVacationStartDate();
            log.info("current employeeEmitters {}", employeeEmitters);
            log.info("Before processing vacation request. Employee ID: {}", employeeId);
            String message = "연차 신청이 처리 되었습니다. = " + startDate;
            SseMessageInsertDto insertDto = new SseMessageInsertDto(employeeId, message, "vacation", String.valueOf(requestId));

            for (SseEmitterWithEmployeeInformationDto emitterDto : employeeEmitters) {
                try {
                    if (emitterDto.getEmployeeNumber().equals(employeeId)) {

                        log.info("find employee sseEmitter. will send event");

                        log.info("request insert msg {}", insertDto);


                        emitterDto.getSseEmitter().send(SseEmitter.event().data(message).name("message").id(String.valueOf(insertDto.getMessageId())));
                        break;
                    }
                } catch (Exception e) {
                    for (SseEmitterWithEmployeeInformationDto reRegisteredEmitterDto : employeeEmitters) {
                        if (employeeId.equals(reRegisteredEmitterDto.getEmployeeNumber())) {
                            log.info("타임아웃으로 메세지 전송 실패. 재전송 수행!");
                            if (reRegisteredEmitterDto.getEmployeeNumber().equals(employeeId)) {
                                log.info("find employee sseEmitter. will send event");

                            }
                        }
                        try {
                            emitterDto.getSseEmitter().send(SseEmitter.event().data(message).name("message").id(String.valueOf(insertDto.getMessageId())));
                        } catch (Exception e1) {
                            log.info("메세지 전송 실패.");
                        }
                        break;
                    }
                    break;
                }
            }
            {
                notificationMapper.addUnreadMsgOfEmployee(insertDto);
                log.info("inserted msg : {}", insertDto);
                HttpSession session = req.getSession();
                String loginId = (String) session.getAttribute("loginId");
                List<String> processingList = REQUEST_LIST.get(req.getRequestURI());
                log.info("작업 큐에 현재 진행 중인 작업 제거 : loginId = {}, queue = {} ", loginId, processingList);
                // 작업 완료 후 작업 큐에서 현재 요청 정보를 제거
                processingList.remove(loginId);
                log.info("작업 제거 후 : loginId = {}, queue = {} ", loginId, processingList);
            }
        }
        return result;
    }
}

