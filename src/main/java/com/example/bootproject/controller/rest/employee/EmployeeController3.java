//package com.example.bootproject.controller.rest.employee;
//
//import com.example.bootproject.entity.Employee;
//import com.example.bootproject.service.service3.api.*;
//import com.example.bootproject.system.util.IpAnalyzer;
//import com.example.bootproject.vo.vo1.request.LoginRequestDto;
//import com.example.bootproject.vo.vo1.request.appeal.AppealRequestDto;
//import com.example.bootproject.vo.vo1.request.employee.EmployeeInformationUpdateDto;
//import com.example.bootproject.vo.vo1.request.vacation.VacationRequestDto;
//import com.example.bootproject.vo.vo1.response.appeal.AppealRequestResponseDto;
//import com.example.bootproject.vo.vo1.response.employee.EmployeeResponseDto;
//import com.example.bootproject.vo.vo1.response.login.LoginResponseDto;
//import com.example.bootproject.vo.vo1.response.logout.LogoutResponseDto;
//import com.example.bootproject.vo.vo1.response.vacation.VacationRequestResponseDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//public class EmployeeController3 {
//
//    private final LoginService loginService;
//    private final LogoutService logoutService;
//    private final VacationService vacationService;
//    private final AppealService appealService;
//    private final EmployeeService employeeService;
//
//    @GetMapping("/employee/information")
//    public ResponseEntity<Employee> getInformationOfMine(HttpServletRequest req) {
//        HttpSession session = req.getSession(false);
//        if (session == null) {
//            log.info("권한이 없는 사용자의 접근 발생 - 로그인 정보가 없습니다");
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        if (session.getAttribute("admin") != null) {
//            log.info("admin에서는 지원하지 않는 기능 : 사원 정보 조회");
//            return ResponseEntity.badRequest().build();
//        }
//
//        String loginId = checkLoginId(req);
//        Employee result = employeeService.findEmployeeInfoById(loginId);
//        if (result != null) {
//            return ResponseEntity.ok(result);
//        }
//        log.info("조회 결과 없음 혹은 실패");
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//    }
//
//    @PostMapping("/employee/information")
//    public ResponseEntity<EmployeeResponseDto> modifyEmployeeInformationOfMine(HttpServletRequest req, @ModelAttribute EmployeeInformationUpdateDto dto) {
//        HttpSession session = req.getSession(false);
//        if (session == null) {
//            log.info("권한이 없는 사용자의 접근 발생 - 로그인 정보가 없습니다");
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        if (session.getAttribute("admin") != null) {
//            log.info("admin에서는 지원하지 않는 기능 : 사원 정보 조회");
//            return ResponseEntity.badRequest().build();
//        }
//
//        String loginId = checkLoginId(req);
//        EmployeeResponseDto result = employeeService.updateInformation(loginId, dto);
//        if (result != null) {
//            return ResponseEntity.ok(result);
//        }
//        log.info("조회 결과 없음 혹은 실패");
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//    }
//
//    @PostMapping("/login")
//    /*TODO : loginRequestDto validation 체크 필요*/ public ResponseEntity<LoginResponseDto> login(LoginRequestDto dto, HttpServletRequest req) {
//
//        dto.setIp(dto.getIp() == null ? IpAnalyzer.getClientIp(req) : dto.getIp());
//        log.info("LoginRequestDto dto : {}", dto);
//        LoginResponseDto loginResult = null;
//        loginResult = loginService.sessionLogin(dto, req);
//        log.info("session login 결과 : {}", loginResult);
//        if (loginResult != null) {
//            log.info("session login sessionId : {}", req.getSession(false).getId());
//            return ResponseEntity.ok(loginResult);
//        } else {
//            loginResult = loginService.formLogin(dto, req);
//            if (loginResult != null) {
//                log.info("form login request : {}", dto);
//                return ResponseEntity.ok(loginResult);
//            }
//            return ResponseEntity.badRequest().build();
//        }
//        /*
//         * form 로그인 요청 - 로그인 request DTO 생성
//         * 세션 로그인 검사 - 세션 로그인 서비스 생성 및 session 객체 전달
//         * 세션 로그인 실패시 로그인 서비스 호출 - dto 전달
//         * 서비스 응답 - 로그인 response Dto 전달. null 검사로 로그인 결과 확인
//         * 정상 로그인 결과 : 로그인 아이디,ip, 로그인 시간 전달
//         * ip 변경 발생 로그인 결과 : 로그인 아이디, 기존 로그인 ip 전달
//         * */
//    }
//
//    @GetMapping("/logout")
//    public ResponseEntity logout() {
//        LogoutResponseDto dto = logoutService.logout();
//        if (dto != null) {
//            return ResponseEntity.ok(dto);
//        }
//        return ResponseEntity.badRequest().build();
//    }
//
//    @GetMapping("/employee/vacation/requests")
//    public String getRequestVacationInformationOfAll(@RequestParam(name = "year") int year, @RequestParam(name = "month") int month, @RequestParam(name = "day") int day) {
//        return "getRequestVacationInformationOfAll";
//    }
//
//    @PostMapping("/employee/appeal")
//    public ResponseEntity<AppealRequestResponseDto> makeAppealRequest(@ModelAttribute AppealRequestDto dto) {
//
//        log.info("정상 작업 진행");
//        AppealRequestResponseDto result = null;
//        try {
//            result = appealService.makeAppealRequest(dto);
//        } catch (Exception e) {
//            log.info("연차 요청 수행 도중 에러 발생");
//            e.printStackTrace();
//        }
//        if (result != null) {
//            return ResponseEntity.ok(result);
//        }
//        return ResponseEntity.badRequest().build();
//    }
//
//    @PostMapping("/employee/vacation")
//    public ResponseEntity<VacationRequestResponseDto> requestVacation(@ModelAttribute VacationRequestDto dto, @SessionAttribute(name = "loginId") String employeeId) {
//        log.info("정상 작업 진행");
//        VacationRequestResponseDto result = null;
//        if (dto.getEmployeeId() == null) {
//            dto.setEmployeeId(employeeId);
//        } else if (!dto.getEmployeeId().equals(employeeId)) {
//            return ResponseEntity.badRequest().build();
//        }
//        try {
//            result = vacationService.makeVacationRequest(dto);
//        } catch (Exception e) {
//            log.info("연차 요청 수행 도중 에러 발생");
//            e.printStackTrace();
//        }
//        if (result != null) {
//            return ResponseEntity.ok(result);
//        }
//        return ResponseEntity.badRequest().build();
//    }
//
//    private String checkLoginId(HttpServletRequest req) {
//        HttpSession session = req.getSession(false);
//        if (session != null) {
//            String loginId = session.getAttribute("loginId") != null ? (String) session.getAttribute("loginId") : null;
//            String sessionIp = session.getAttribute("ip") != null ? (String) session.getAttribute("ip") : null;
//            if (loginId != null && sessionIp != null) {
//                if (sessionIp.equals(IpAnalyzer.getClientIp(req))) {
//                    log.info("로그인 정보 확인 완료 loginId {}", loginId);
//                    return loginId;
//                }
//                log.info("login 정보 확인 완료 but IP 변경 발생 session : {} client real IP : {} ", sessionIp, IpAnalyzer.getClientIp(req));
//            }
//            log.info("세선에서 로그인 정보를 확인하지 못했습니다 loginId : {} session IP : {}", loginId, sessionIp);
//        }
//        return null;
//    }
//}
