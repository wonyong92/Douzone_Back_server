package com.example.bootproject.controller.rest.manager;
import com.example.bootproject.service.service1.EmployeeService1;
import com.example.bootproject.service.service1.ManagerService1;
import com.example.bootproject.vo.vo1.request.AttendanceApprovalRequestDto;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryRequestDto;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import com.example.bootproject.vo.vo1.response.RegularTimeAdjustmentHistoryResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static com.example.bootproject.controller.rest.employee.EmployeeController1.isValidDate;


@RestController
@RequestMapping("/manager")
@Slf4j
@RequiredArgsConstructor
public class ManagerController1 {

    private final ManagerService1 managerService1;

    private final EmployeeService1 employeeService1;

    //정규출퇴근시간 설정
    @PostMapping("/adjustment")
    public ResponseEntity<RegularTimeAdjustmentHistoryResponseDto> setWorkTime(
            @ModelAttribute RegularTimeAdjustmentHistoryRequestDto dto) {

//        log.info("RegularTimeAdjustmentHistroyDto dto{}:",dto);
//        if (ManagerCheckApi()) {
//            // 권한이 없다면 403 Forbidden 응답을 반환합니다.
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }

        log.info("request{}",dto);

        String employeeId = "emp01";

            RegularTimeAdjustmentHistoryResponseDto responseDto = managerService1.insertRegularTimeAdjustmentHistory(dto, employeeId);
        if (responseDto!=null) {
            log.info("성공적으로 데이터를 받았습니다: {}", responseDto);
            return ResponseEntity.ok(responseDto);
        } else {
            log.info("안좋은 데이터를 받았습니다: {}", responseDto);
            return ResponseEntity.badRequest().build();
        }

    }

    /*
    세션에서 employeeId 가져온다 지금은 하드코딩
    사원id데이터 형식이 이상하게 넘어올경우 오류코드
    데이터가 제데로 들어오면 log남김
    dto반환데이터가 null이면 400오로 아니면 200응답
    모든 조건 성공시 RegularTimeAdjustmentHistoryDto 출력
    */





    //타사원에 대한 근태 이상 승인 내역
    @GetMapping("/approve/{employeeId}")
    public ResponseEntity<List<AttendanceApprovalRequestDto>> getApprovalInfo(
            @PathVariable String employeeId) {

        if (!ManagerCheckApi()) {
            log.info("Access denied: User does not have manager privileges.");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (employeeId == null) {
            log.info("EmployeeId is missing.");
            return ResponseEntity.badRequest().build();
        }

        if (!employeeValidation(employeeId)) {
            log.info("Invalid employeeId format: " + employeeId);
            return ResponseEntity.badRequest().build();
        }

        List<AttendanceApprovalRequestDto> approvalInfo = managerService1.getAttendanceApprovalInfoDto(employeeId);
        if (approvalInfo.isEmpty()) {
            log.info("No approval history found for employeeId: " + employeeId);
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(approvalInfo, HttpStatus.OK);
    }


    /*
    Manager권환처리
    세션에서 employeeId 가져온다 지금은 하드코딩중
    사원id데이터 형식이 이상하게 넘어올경우 오류코드
    데이터가 제데로 들어오면 log남김
    날짜에 대한 데이터 자바 LocalDate을 사용하여 날짜 정보가 맞는지 validation체크
    사원id validation체크
    모든 조건 성공시 200 응답코드
    일데이터가 있는경우 일 경우 검색
    일데이터가 없으면 월로 검색
    만약 테이블에 데이터가 없으면 204요청
    */


    //타사원근태정보조회년월일
    @GetMapping("/attendance_info/{employee_id}/")
    public ResponseEntity<List<AttendanceInfoResponseDto>> getAttendanceInfoOfEmployeeByDay(
            @PathVariable("employee_id") String employeeId,
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            @RequestParam(value = "day", required = false) Integer day) {

//        if (!employeeValidation(employeeId)) {
//            log.info("Invalid employee ID: " + employeeId);
//            return ResponseEntity.badRequest().build();
//        }

        if (day != null && !isValidDate(year, month, day)) {
            log.info("Invalid date: year={}, month={}, day={}", year, month, day);
            return ResponseEntity.badRequest().build();
        }

        List<AttendanceInfoResponseDto> attendanceInfo;


        if (day != null) {
            // 일 데이터가 있으면 해당 날짜로 검색
            LocalDate attendanceDate = LocalDate.of(year, month, day);
            attendanceInfo = employeeService1.getAttendanceByDateAndEmployee(attendanceDate, employeeId);
        } else {
            // 일 데이터가 없으면 해당 월로 검색
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate firstDayOfMonth = yearMonth.atDay(1);
            attendanceInfo = employeeService1.getAttendanceByMonthAndEmployee(year,month, employeeId);
        }

//        if (attendanceInfo.isEmpty()) {
//            log.info("No attendance records found for employeeId: " + employeeId);
//            return ResponseEntity.noContent().build();
//        }

        return ResponseEntity.ok(attendanceInfo);
    }

    //TODO: 모든 내역조회에 페이징네이션 적용

    public boolean ManagerCheckApi(){
        return true;
    }

    public boolean employeeValidation(String employeeId){
        return employeeId.matches("^[0-9]*$");
    }



}