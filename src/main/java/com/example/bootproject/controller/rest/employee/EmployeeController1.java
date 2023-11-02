package com.example.bootproject.controller.rest.employee;
import com.example.bootproject.service.service1.EmployeeService1;

import com.example.bootproject.vo.vo1.request.AttendanceApprovalRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoEndRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoStartRequestDto;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;


@RestController
@RequestMapping("/employee")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController1 {

    private final EmployeeService1 employeeService1;


    //출근처리
        @PostMapping("/attendance")
            public ResponseEntity<AttendanceInfoResponseDto> makeAttendanceInfo(AttendanceInfoStartRequestDto requestDto) {
            // 직접 할당한 더미 데이터
            String employeeId = "emp03";
            AttendanceInfoResponseDto responseDto = employeeService1.makeStartResponse(requestDto, employeeId);

            // 결과에 따라 응답을 반환한다
            if (responseDto == null) {
                log.info("반환값이 null이다: " + employeeId);
                return ResponseEntity.badRequest().build();
            }

            if(!employeeValidation(employeeId)){
                log.info("employeeId not collect validationcheck" + employeeId);
                return ResponseEntity.badRequest().build();
            }//로그인에서



            return ResponseEntity.ok(responseDto);
        }
/*
세션에 employeeId validation체크
세션에 DTO의 내용이 null값이때 badrequest
 */



    //톼근처리
    @PostMapping("/leave")
    public ResponseEntity<AttendanceInfoResponseDto> makeLeaveInformation(AttendanceInfoEndRequestDto requestDto){

        String employeeId = "emp03";

        if(!employeeValidation(employeeId)){
            log.info("not collect validationcheck" + employeeId);
            return ResponseEntity.badRequest().build();
        }

      AttendanceInfoResponseDto responseDto = employeeService1.makeEndResponse(requestDto,employeeId);

        if (responseDto == null) {
            log.info("Failed to record attendance for employeeId: " + employeeId);
            return ResponseEntity.badRequest().build();
        }



        return ResponseEntity.ok(responseDto);
    }
/*
세션에 employeeId validation체크
세션에 DTO의 내용이 null값이때 badrequest
 */







    //년월일 자신의 근태정보조회 년월만 입력하면 년월만 입력데이터 적용되게 구현
    @GetMapping("/attendance_info/")
    public ResponseEntity<List<AttendanceInfoResponseDto>> getAttendanceInfoOfMineByDay(HttpServletRequest request,
                                                                                        @RequestParam("year") int year,
                                                                                        @RequestParam("month") int month,
                                                                                        @RequestParam(value = "day", required = false) Integer day) {

        HttpSession session = request.getSession();
        // String employeeId = (String) session.getAttribute("employeeId");

        if (day != null && !isValidDate(year, month, day)) {
            log.info("Invalid date: year={}, month={}, day={}", year, month, day);
            return ResponseEntity.badRequest().build();
        }

        String employeeId = "emp01";

        if (!employeeValidation(employeeId)) {
            return ResponseEntity.badRequest().build();
        }

        List<AttendanceInfoResponseDto> attendanceInfo;

        if (day != null) {
            LocalDate attendanceDate = LocalDate.of(year, month, day);
            attendanceInfo = employeeService1.getAttendanceByDateAndEmployee(attendanceDate, employeeId);
        } else {
            LocalDate attendanceDate = LocalDate.of(year, month ,1);
            attendanceInfo = employeeService1.getAttendanceByMonthAndEmployee(year,month, employeeId);
        }

        if (attendanceInfo.isEmpty()) {
            log.info("No attendance records found for employeeId: {}", employeeId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(attendanceInfo);
    }

    /*
    세션에서 employeeId 가져온다 지금은 하드코딩중
    사원id데이터 형식이 이상하게 넘어올경우 오류코드
    데이터가 제데로 들어오면 log남김
    날짜에 대한 데이터 자바 LocalDate을 사용하여 날짜 정보가 맞는지 validation체크
    사원id validation체크
    모든 조건 성공시 200 응답코드
    일데이터가 있는경우 일 경우 검색
    일데이터가 없으면 월로 검색
    attendanceinfo데이터를 넘긴다
    */


    //자신의 근태 승인요청
    @PostMapping("/approve")
    public ResponseEntity<AttendanceApprovalRequestDto> makeApproveRequest(HttpServletRequest request) {
        Long attendanceInfoId = Long.valueOf("1");
        String employeeId = "emp01";

        if (attendanceInfoId == null || employeeId == null) {
            log.info("AttendanceInfoId or EmployeeId is missing.");
            return ResponseEntity.badRequest().build();
        }
        log.info("Validating employee: {}", attendanceInfoId);
        log.info("Validating employee: {}", employeeId);

        if (!employeeValidation(employeeId)) {
            log.info("Not collect validation check {}", employeeId);
            return ResponseEntity.badRequest().build();
        }

        AttendanceApprovalRequestDto attendanceApprovalDto = employeeService1.approveAttendance(attendanceInfoId, employeeId);
        return ResponseEntity.ok(attendanceApprovalDto);
    }

     /*
    세션에서 attendanceInfoId와 employeeId 가져오기
    사원id나 근태정보id가 안넘어올경우 오류코드
    데이터가 들어올시 log데이터가 넘어옴
    사원id validation체크
    모든 조건 달성시 attendanceApprovalDto 반환
     */




    //자신의 근태승인내역
    @GetMapping("/approves")
    public ResponseEntity<List<AttendanceApprovalRequestDto>> getHistoryOfApproveOfMine(HttpServletRequest request){
        HttpSession session = request.getSession();

        String employeeId= "emp01";

        if(employeeId == null){
            log.info("EmployeeId is missing");
            return ResponseEntity.noContent().build();
        }

        if(!employeeValidation(employeeId)){
            log.info("not collect validationcheck" + employeeId);
            return ResponseEntity.badRequest().build();
        }

        List<AttendanceApprovalRequestDto> approvalInfoDtos = employeeService1.findApprovalInfoByMine(employeeId);

        if (approvalInfoDtos.isEmpty()) {
            log.info("No approval history found for employeeId: " + employeeId);
            return ResponseEntity.noContent().build();
        }

        return  ResponseEntity.ok(approvalInfoDtos);
    }

    /*
    세션에서 attendanceInfoId와 employeeId 가져오기
    사원id나 근태정보id가 안넘어올경우 오류코드
    데이터가 들어올시 log데이터가 넘어옴
    사원id validation체크
    모든 조건 성공시 approvaInfoDtos을 출력한다
     */




// 사원id validation check
public boolean employeeValidation(String employeeId){
    return employeeId.matches("^emp[0-9]+$");
}



    //년월알 데이터 형식 맞는지에 validationcheck
    public static boolean isValidDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException e) {
            return false;
        }


    }

}











