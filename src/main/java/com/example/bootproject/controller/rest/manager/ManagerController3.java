package com.example.bootproject.controller.rest.manager;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.repository.mapper3.employee.EmployeeMapper;
import com.example.bootproject.service.service3.api.AppealService;
import com.example.bootproject.service.service3.api.EmployeeService;
import com.example.bootproject.service.service3.api.ManagerService;
import com.example.bootproject.service.service3.api.VacationService;
import com.example.bootproject.system.util.IpAnalyzer;
import com.example.bootproject.vo.vo3.request.appeal.AppealProcessRequestDto;
import com.example.bootproject.vo.vo3.request.vacation.VacationAdjustRequestDto;
import com.example.bootproject.vo.vo3.request.vacation.VacationProcessRequestDto;
import com.example.bootproject.vo.vo3.response.Page;
import com.example.bootproject.vo.vo3.response.appeal.AppealRequestResponseDto;
import com.example.bootproject.vo.vo3.response.employee.EmployeeResponseDto;
import com.example.bootproject.vo.vo3.response.vacation.VacationAdjustResponseDto;
import com.example.bootproject.vo.vo3.response.vacation.VacationRequestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.example.bootproject.system.StaticString.VACATION_REQUEST_STATE_REJECTED;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ManagerController3 {
    private final VacationService vacationService;
    private final AppealService appealService;
    private final EmployeeService employeeService;
    private final ManagerService managerService;

    private int validationPageNum(String page) { //요청 받은 페이지에 대한 validation check
        try {
            int pageNum = Integer.parseInt(page);
            if (pageNum < 1) {
                return 1;
            }
            return pageNum;
        } catch (Exception e) {
            log.info("잘못된 페이지 번호 입력 => 1 페이지로 자동 설정");
            return 1;
        }
    }

    public String validationSort(String sort) {
        if (sort.matches("^[a-zA-Z_]+$")) {
            return sort;
        }
        return "";
    }

    public String validationDesc(String desc) {
        if (desc.matches("^(desc|DESC|)$")) {
            return desc;
        }
        return "";
    }

    @GetMapping("/manager/employees")
    public ResponseEntity<Page<List<EmployeeResponseDto>>> getEmployeeList(HttpServletRequest req, @RequestParam(name = "page", defaultValue = "1") String page, @RequestParam(name = "sort", defaultValue = "''") String sort, @RequestParam(name = "desc", defaultValue = "") String desc) {
        if (isManager(req)) {
            int currentPage = validationPageNum(page);
            String sortColumn = validationSort(sort);
            String descCheck = validationDesc(desc);
            log.info("get Employee list - validation result : {} {} {}", currentPage, sortColumn, descCheck);

            Page<List<EmployeeResponseDto>> result = managerService.getEmployeeList(currentPage, sortColumn, descCheck);
            log.info("getEmpInfo result : {}", result);
            if (result.getData().isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(result); // 200 OK
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 ERROR
    }

    @PostMapping("/manager/vacation/process")
    public ResponseEntity<VacationRequestResponseDto> processVacationRequest(@ModelAttribute VacationProcessRequestDto dto, HttpServletRequest req) {
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
    public ResponseEntity<VacationAdjustResponseDto> modifyVacationQuantityOfEmployee(@PathVariable(name = "employee_id") String employeeId, @ModelAttribute VacationAdjustRequestDto dto, HttpServletRequest req) {
//        - 컨트롤러
//                - form - post로 데이터 전달
//        - vacationAdjustRequestDto
//                - 저장 대상 employee_id(관리자 자신의 아이디와 일치하면 안된다)
//        - adjust_quantity
//                - reason
//                - adjust_type
//                - 로그인 유무, manager 유무 확인 및 현재 로그인된 아이디와 동일한 타겟 employee_id 인지 확인
//                - forbidden 응답
//                - adjust_quantity가 적절한 범위를 벗어나는 경우, reason이 누락된 경우, 연차 종류가 이상한 데이터가 들어오는 경우(상수 리스트에서 검색?, DB에서 직접 검색?)
//                - badRequest 응답
//                - 서비스로 dto 전달
//        - 서비스
//                - 분기점 1 : 타겟 employee_id로 사원을 찾을 수 없는 경우
//                - badRequest 응답을 위한 응답
//                - 분기점 1 : 저장 대상 사원을 찾은 경우
//                - vacation_adjusted_history에 데이터 insert
//        - generated key를 이용하여 재조회 후 vacationAdjustResponseDto에 담아서 응답 전달
        if (isManager(req) && req.getSession(false) != null && !req.getSession(false).getAttribute("loginId").equals(employeeId)) {
            try {
                int vacationModifyQuantity = Integer.parseInt(dto.getAdjustQuantity());
                if (dto.getReason() == null || dto.getReason().trim().equals("")) {
                    return ResponseEntity.badRequest().build();
                }
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
            VacationAdjustResponseDto result = vacationService.modifyVacationOfEmployee(dto, employeeId);
            return ResponseEntity.ok(result);
        } else {
            if (req.getSession(false).getAttribute("loginId").equals(employeeId)) {
                log.info("자신의 연차 정보에 접근 발생");
            } else {
                log.info("로그인 정보를 확인하세요");
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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
        if (session.getAttribute("manager") != null && (boolean) session.getAttribute("manager")) {
            return true;
        }
        log.info("ip 변경 발생");
        return false;
    }

    @GetMapping("/manager/information/{employee_id}")
    public ResponseEntity<Employee> getInformationOfEmployee(@PathVariable(name = "employee_id") String employeeId, HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("manager") == null || !(boolean) session.getAttribute("manager")) {
            log.info("권한이 없는 사용자의 접근 발생 - 로그인 정보가 없거나 근태관리자가 아닙니다");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (session.getAttribute("admin") != null) {
            log.info("admin에서는 지원하지 않는 기능 : 사원 정보 조회");
            return ResponseEntity.badRequest().build();
        }

        Employee result = employeeService.findEmployeeInfoById(employeeId);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        log.info("조회 결과 없음 혹은 실패");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
