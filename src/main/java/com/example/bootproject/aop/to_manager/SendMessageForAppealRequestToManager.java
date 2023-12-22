package com.example.bootproject.aop.to_manager;

import com.example.bootproject.repository.mapper1.EmployeeMapper1;
import com.example.bootproject.repository.mapper1.ManagerMapper1;
import com.example.bootproject.repository.mapper3.notification.NotificationMapper;
import com.example.bootproject.vo.vo1.request.appeal.AppealRequestDto;
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
import java.util.ArrayList;
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
        log.info("AOP sendAppealRequestMessageToManager");
        String loginId = getLoginIdOrNull(req);
        ResponseEntity<AppealRequestResponseDto> result = (ResponseEntity<AppealRequestResponseDto>) joinPoint.proceed();
        Long requestId = dto.getAttendanceAppealRequestId();
        log.info("appeal request occur - reuqestId {}", requestId);

        if (result.getStatusCode() == HttpStatus.OK) {
            String message = "New Appeal Request from " + loginId;
            //일단 DB에 메세지는 저장하고,
            List<String> managerEmployeeIds = notificationMapper.getManagerId();
            List<SseMessageInsertDto> insertDataList = new ArrayList<>();
            for (String managerId : managerEmployeeIds) {
                SseMessageInsertDto insertDto = new SseMessageInsertDto(managerId, message, "Appeal", String.valueOf(requestId));

                log.info("inserted msg : {}", insertDto);
                managerEmitters.stream().filter(managerEmitter -> managerEmitter.getEmployeeNumber().equals(managerId) && !managerEmitter.getEmployeeNumber().equals(dto.getEmployeeId())).findFirst().ifPresent((manager) -> {
                    try {
                        manager.getSseEmitter().send(SseEmitter.event().data(insertDto.getMessage()).name("message").id(String.valueOf(insertDto.getMessageId())));
                    } catch (Exception e) {
                        log.info("emitter가 등록되지 않아 {} 로 메세지 전송 실패", manager.getEmployeeNumber());
//                        throw new RuntimeException(e);
                    }
                    notificationMapper.addUnreadMsgOfManager(insertDto);
                });

            }
            //모든 접속한 매니저에 대해 메세지 전송

            List<String> processingList = REQUEST_LIST.get(req.getRequestURI());

            // 작업 완료 후 작업 큐에서 현재 요청 정보를 제거
            if (processingList.indexOf(loginId) != -1) {
                log.info("작업 큐에 현재 진행 중인 작업 제거 : loginId = {}, queue = {} ", loginId, processingList);
                processingList.remove(loginId);
                log.info("작업 제거 후 : loginId = {}, queue = {} ", loginId, processingList);
            } else {
                log.info("작업 큐에 현재 유저가 진행 중인 작업이 없습니다. 제거를 진행하지 않습니다");
            }
        }
        return result;
    }
}
