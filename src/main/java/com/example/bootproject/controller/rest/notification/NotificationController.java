package com.example.bootproject.controller.rest.notification;

import com.example.bootproject.service.service3.api.NotificationService;
import com.example.bootproject.vo.vo1.request.notification.SseEmitterWithEmployeeInformationDto;
import com.example.bootproject.vo.vo1.response.Page;
import com.example.bootproject.vo.vo1.response.notification.NotificationMessageResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static com.example.bootproject.system.interceptor.SseBroadCastingInterceptorForEmployee.employeeEmitters;
import static com.example.bootproject.system.interceptor.SseBroadCastingInterceptorForManager.managerEmitters;
import static com.example.bootproject.system.util.ValidationChecker.getLoginIdOrNull;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;
    @Value("${sse.timeout}")
    Long timeout;
    @GetMapping("/register")
    public SseEmitter registerEmployeeSseEmitter(@ModelAttribute SseEmitterWithEmployeeInformationDto dto, HttpServletResponse resp, HttpServletRequest req) throws JsonProcessingException {
        log.info("registered dto : {}", dto);
        SseEmitter emitter = new SseEmitter(timeout);
        dto.setSseEmitter(emitter);
        boolean find = false;
        for (SseEmitterWithEmployeeInformationDto saved : employeeEmitters) {
            try {
                if (saved.getEmployeeNumber().equals(dto.getEmployeeNumber())) {
                    log.info("다시 employee register {} 발생 -> 새로운 emitter로 교체", dto.getEmployeeNumber());
                    saved.setSseEmitter(emitter);
                    find = true;
                    break;
                }
            } catch (Exception e) {
                // 예외 처리 로직 추가
            }
        }
        if (!find) {
            employeeEmitters.add(dto);
            log.info("employee emitter 등록 {}", dto);
        }

        String loginId = getLoginIdOrNull(req);
        if (loginId != null) {
            Page<List<NotificationMessageResponseDto>> result = notificationService.getPagedUnreadMessageListOfEmployee(loginId, 1);
            resp.addHeader("unread_data", objectMapper.writeValueAsString(result));
        }
        return emitter;
    }

    @GetMapping("/register/manager")
    public SseEmitter registerMangerSseEmitter(@ModelAttribute SseEmitterWithEmployeeInformationDto dto, HttpServletResponse resp, HttpServletRequest req) throws JsonProcessingException {
        log.info("registered dto : {}", dto);
        SseEmitter emitter = new SseEmitter(timeout);
        dto.setSseEmitter(emitter);
        boolean find = false;
        for (SseEmitterWithEmployeeInformationDto saved : managerEmitters) {
            try {
                if (saved.getEmployeeNumber().equals(dto.getEmployeeNumber())) {
                    log.info("다시 register 발생 -> 새로운 emitter로 교체");
                    saved.setSseEmitter(emitter);
                    find = true;
                    break;
                }
            } catch (Exception e) {
                // 예외 처리 로직 추가
            }
        }
        if (!find) {
            managerEmitters.add(dto);
            log.info("emitter 등록 {}", dto);
        }

        String loginId = getLoginIdOrNull(req);
        if (loginId != null) {
            Page<List<NotificationMessageResponseDto>> result = notificationService.getPagedUnreadMessageListOfEmployee(loginId, 1);
            resp.addHeader("unread_data", objectMapper.writeValueAsString(result));
        }
        return emitter;
    }


    @GetMapping("/sendToEmployee/{employeeNumber}")
    public String sendToOneEmployee(@PathVariable String employeeNumber) {
        log.info("message to one employee {}", employeeNumber);
        for (SseEmitterWithEmployeeInformationDto dto : employeeEmitters) {
            try {
                if (dto.getEmployeeNumber().equals(employeeNumber))
                    dto.getSseEmitter().send(SseEmitter.event().data("Hello from controller").name("message"));
            } catch (Exception e) {
                // 예외 처리 로직 추가
            }
        }
        return "Messages sent to all connected clients.";
    }

    @GetMapping("/sendToManager")
    public String sendToAllManager() {
        log.info("broadcast to all manager");
        for (SseEmitterWithEmployeeInformationDto dto : employeeEmitters) {
            try {
                if (dto.getUserType().equals("manager"))
                    dto.getSseEmitter().send(SseEmitter.event().data("Hello from controller").name("message"));
            } catch (Exception e) {
                // 예외 처리 로직 추가
            }
        }
        return "Messages sent to all connected clients.";
    }

    @GetMapping("/unread/{employeeNumber}")
    public ResponseEntity<Page<List<NotificationMessageResponseDto>>> getUnreadMessageOfEmployee(@PathVariable String employeeNumber, @RequestParam(required = false, defaultValue = "1") Integer page) {
        Page<List<NotificationMessageResponseDto>> result = notificationService.getPagedUnreadMessageListOfEmployee(employeeNumber, page);
        log.info("unread msg of {} {} ", employeeNumber, result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/unread/manager/{employeeNumber}")
    public ResponseEntity<Page<List<NotificationMessageResponseDto>>> getUnreadMessageOfManager(@PathVariable String employeeNumber, @RequestParam(required = false, defaultValue = "1") Integer page) {
        Page<List<NotificationMessageResponseDto>> result = notificationService.getPagedUnreadMessageListOfManager(employeeNumber, page);
        log.info("unread msg of {} {} ", employeeNumber, result);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/notification/employee/unread")
    public ResponseEntity<Page<List<NotificationMessageResponseDto>>> getPagedUnreadMessageofEmployee(int page, HttpServletRequest req) {
        String loginId = getLoginIdOrNull(req);
        if (loginId != null) {
            Page<List<NotificationMessageResponseDto>> result = notificationService.getPagedUnreadMessageListOfEmployee(loginId, page);
            return ResponseEntity.ok(result);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/notification/employee/count/unread")
    public ResponseEntity<Integer> countUnreadMessageOfEmployee(HttpServletRequest req) {
        String loginId = getLoginIdOrNull(req);
        if (loginId != null) {
            int count = notificationService.countUnreadMsgOfEmployee(loginId);
            return ResponseEntity.ok(count);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/notification/employee/read/{messageId}")
    public ResponseEntity<Map<String,Integer>> changeUnreadToReadEmployeeMessage(@PathVariable Long messageId, HttpServletRequest req){
        String loginId = getLoginIdOrNull(req);
        log.info("{} {}","changeUnreadToReadEmployeeMessage",loginId);
        if(loginId != null){
            boolean result = notificationService.changeUnreadToReadEmployeeMessage(loginId,messageId);
            Integer totalCountAfterRead = notificationService.countUnreadMsgOfEmployee(loginId);
            log.info("{} {}","totalCountAfterRead",totalCountAfterRead);
            return result ? ResponseEntity.ok(Map.of("totalCount",totalCountAfterRead)):ResponseEntity.badRequest().build();
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/notification/manager/read/{messageId}")
    public ResponseEntity<Map<String,Integer>> changeUnreadToReadManagerMessage(@PathVariable Long messageId, HttpServletRequest req){
        String loginId = getLoginIdOrNull(req);
        log.info("{} {}","changeUnreadToReadManagerMessage",loginId);
        if(loginId != null){
            boolean result = notificationService.changeUnreadToReadManagerMessage(loginId,messageId);
            Integer totalCountAfterRead = notificationService.countUnreadMsgOfManager(loginId);
            log.info("{} {}","totalCountAfterRead",totalCountAfterRead);
            return result ? ResponseEntity.ok(Map.of("totalCount",totalCountAfterRead)):ResponseEntity.badRequest().build();
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

}
