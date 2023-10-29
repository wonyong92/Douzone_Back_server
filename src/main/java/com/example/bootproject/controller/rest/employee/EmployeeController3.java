package com.example.bootproject.controller.rest.employee;

import com.example.bootproject.service.service3.api.LoginService;
import com.example.bootproject.service.service3.api.LogoutService;
import com.example.bootproject.system.util.IpAnalyzer;
import com.example.bootproject.vo.vo3.request.LoginRequestDto;
import com.example.bootproject.vo.vo3.response.login.LoginResponseDto;
import com.example.bootproject.vo.vo3.response.logout.LogoutResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmployeeController3 {

    private final LoginService loginService;
    private final LogoutService logoutService;
    @GetMapping("/employee/information/{employee_id}")
    public String getInformationOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        return "getInformationOfEmployee";
    }

    @GetMapping("/employee/information")
    public String getInformationOfMine() {
        return "getInformationOfMine";
    }

    @PostMapping("/employee/information")
    public String modifyEmployeeInformationOfMine() {
        return "modifyEmployeeInformationOfMine";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto dto, HttpServletRequest req) {

        dto.setIp(dto.getIp()==null?IpAnalyzer.getClientIp(req):dto.getIp());
        log.info("LoginRequestDto dto : {}",dto);
        LoginResponseDto loginResult = null;
        loginResult = loginService.sessionLogin(dto);
        log.info("session login 결과 : {}",loginResult);
         if(loginResult!=null){
             log.info("session login sessionId : {}",req.getSession().getId());
             return ResponseEntity.ok(loginResult);
         }
         else{
             loginResult = loginService.formLogin(dto);
             if(loginResult!=null){
                 log.info("form login request : {}",dto);
                 return ResponseEntity.ok(loginResult);
             }
             return ResponseEntity.badRequest().build();
         }
        /*
        * form 로그인 요청 - 로그인 request DTO 생성
        * 세션 로그인 검사 - 세션 로그인 서비스 생성 및 session 객체 전달
        * 세션 로그인 실패시 로그인 서비스 호출 - dto 전달
        * 서비스 응답 - 로그인 response Dto 전달. null 검사로 로그인 결과 확인
        * 정상 로그인 결과 : 로그인 아이디,ip, 로그인 시간 전달
        * ip 변경 발생 로그인 결과 : 로그인 아이디, 기존 로그인 ip 전달
        * */
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        LogoutResponseDto dto = logoutService.logout();
        if(dto != null){
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.badRequest()
                .build();
    }

    @GetMapping("/employee/vacation/requests")
    public String getRequestVacationInformationOfAll(@RequestParam(name = "year") int year, @RequestParam(name = "month") int month, @RequestParam(name = "day") int day) {
        return "getRequestVacationInformationOfAll";
    }

    @PostMapping("/employee/appeal")
    public String makeAppealRequest() {
        return "makeAppealRequest";
    }

}
