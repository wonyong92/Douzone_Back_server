package com.example.bootproject.controller.rest.manager;

import com.example.bootproject.service.service3.api.AppealService;
import com.example.bootproject.service.service3.api.VacationService;
import com.example.bootproject.system.util.IpAnalyzer;
import com.example.bootproject.vo.vo3.request.appeal.AppealProcessRequestDto;
import com.example.bootproject.vo.vo3.request.vacation.VacationProcessRequestDto;
import com.example.bootproject.vo.vo3.response.appeal.AppealRequestResponseDto;
import com.example.bootproject.vo.vo3.response.vacation.VacationRequestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static com.example.bootproject.ServletInitializer.REQUEST_LIST;
import static com.example.bootproject.system.StaticString.VACATION_REQUEST_STATE_REJECTED;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ManagerController3 {
    private final VacationService vacationService;
    private final AppealService appealService;

    @GetMapping("/manager/employees")
    public String getEmployeeList() {
        return "getEmployeeList";
    }

    @PostMapping("/manager/vacation/process")
    public ResponseEntity<VacationRequestResponseDto> processVacationRequest(@ModelAttribute VacationProcessRequestDto dto, HttpServletRequest req) {

//        - 동시에 한명의 근태 담당자 만 처리
//        1. 근태 담당자 권한 확인
//        2. form을 통해 연차 요청id, 처리 데이터(승인.반려 여부, 사유)를 가져온다

        if (isManager(req)) {
            String status = dto.getVacationRequestStateCategoryKey();
            if (status.equals(VACATION_REQUEST_STATE_REJECTED)) {
                if (dto.getReasonForRejection().equals(null) || dto.getReasonForRejection().trim().equals("")) {
                    //거절 상태에 대해 사유가 누락된 경우
                    log.info("거절 사유가 누락됨");
                    return ResponseEntity.badRequest().build();
                }
            }
            dto.setEmployeeId((String) req.getSession().getAttribute("loginId"));
            VacationRequestResponseDto result = vacationService.processVacationRequest(dto);
            if (result == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
        } else {
            log.info("현재 로그인 유저는 관리자가 아닙니다");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/manager/vacation/modify/{employee_id}")
    public String modifyVacationQuantityOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        return "modifyVacationQuantityOfEmployee";
    }

    @PostMapping("/manager/appeal/process") //39
    public ResponseEntity<AppealRequestResponseDto> processAppealRequest(@ModelAttribute AppealProcessRequestDto dto, HttpServletRequest req) {


        if (isManager(req)) {
            String status = dto.getStatus();
            if (status.equals(VACATION_REQUEST_STATE_REJECTED)) {
                if (dto.getReasonForRejection().equals(null) || dto.getReasonForRejection().trim().equals("")) {
                    //거절 상태에 대해 사유가 누락된 경우
                    log.info("거절 사유가 누락됨");
                    return ResponseEntity.badRequest().build();
                }
            }
            dto.setEmployeeId((String) req.getSession().getAttribute("loginId"));
            AppealRequestResponseDto result = appealService.processAppealRequest(dto);
            if (result == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
        } else {
            log.info("현재 로그인 유저는 관리자가 아닙니다");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    //TODO : 매니저 권한 확인 로직 작성 필요
    private boolean isManager(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String loginId = (String) session.getAttribute("loginId");
        String ip = (String) session.getAttribute("ip");

        if (loginId == null) {
            log.info("로그인 정보 없는 요청 발생");
            return false;
        }

        if (!ip.equals(IpAnalyzer.getClientIp(req))) {
            log.info("ip 변경 발생");
            return false;
        }

        return (boolean) session.getAttribute("manager");
    }


}
