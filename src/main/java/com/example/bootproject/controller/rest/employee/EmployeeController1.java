package com.example.bootproject.controller.rest.employee;
import com.example.bootproject.service.service1.EmployeeService1;
import com.example.bootproject.vo.vo1.request.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/employee")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController1 {

    private final EmployeeService1 employeeService1;


    @PostMapping("/attendance")
    @Transactional
    public ResponseEntity<Object> makeAttendanceInfo() {
        String employeeId ="emp01";
        LocalDate attendanceDate = LocalDate.now();
        LocalDateTime existingStartTime = employeeService1.getStartTimeByEmployeeIdAndDate(employeeId, attendanceDate);
        LocalDateTime startTime = LocalDateTime.now();
        AttendanceInfoStartDto attendanceInfoStartDto = new AttendanceInfoStartDto();
        attendanceInfoStartDto.setEmployeeId(employeeId);
        attendanceInfoStartDto.setAttendanceDate(attendanceDate);
        attendanceInfoStartDto.setStartTime(startTime);

        if (existingStartTime != null) {
            log.info("출근시간이 있습니다");
            return ResponseEntity.badRequest().body("출근시간이 있습니다");
        }
        int result = employeeService1.startTime(attendanceInfoStartDto);

        if (result == 0) {
            log.info("출근 데이터가 안담김");
            return ResponseEntity.badRequest().body("출근 데이터가 안담김");
        }

        return ResponseEntity.ok(attendanceInfoStartDto);
    }

    /*
    세션에서 employeeId 가져온다 지금은 하드코딩중
     existingStartTime에 출근시간을 조회하는 데이터를 담아옵니다
     만약 출근시간이 있으면  badrequest와 할께 출근시간이있습니다 body에담아서반환
     dto가 데이터가 없으면  badrequest와 할께 데이터가 안담김을 body에 담아서 반환한다
    모든요청이 정상적이면 AttendanceEndDto 내용을 body에 사원id 퇴근시간 현재날짜를 반한환다
    */


    @PostMapping("/leave")
    @Transactional
    public ResponseEntity<Object>makeLeaveInformation(){
        String employeeId ="emp01";
        LocalDate attendanceDate = LocalDate.now();
        LocalDateTime endTime = LocalDateTime.now();
        AttendanceInfoEndDto attendanceInfoEndDto = new AttendanceInfoEndDto();
        attendanceInfoEndDto.setEmployeeId(employeeId);
        attendanceInfoEndDto.setAttendanceDate(attendanceDate);
        attendanceInfoEndDto.setEndTime(endTime);

        //출근시간 유무확인
        LocalDateTime existingStartTime = employeeService1.getStartTimeByEmployeeIdAndDate(employeeId, attendanceDate);
        if (existingStartTime == null) {
            log.info("출근정보가 없습니다");
            return ResponseEntity.badRequest().body("출근정보가없습니다");
        }

        //퇴근시간 유무확인
        LocalDateTime existingEndTime = employeeService1.getEndTimeByEmployeeIdAndDate(employeeId,attendanceDate);
        if (existingEndTime != null) {
            log.info("퇴근시간이 있습니다");
            return ResponseEntity.badRequest().body("퇴근시간이있습니다");
        }
        int result = employeeService1.endTime(attendanceInfoEndDto);

        if (result == 0) {
            log.info("퇴근 정보 데이터가 안담김");
            return ResponseEntity.badRequest().body("퇴근 정보 데이터가 안담겼습니다");
        }

        return ResponseEntity.ok(attendanceInfoEndDto);
    }

    /*
    세션에서 employeeId 가져온다 지금은 하드코딩중
     existingstartTime에 출근시간을 조회하는 데이터를 담아옵니다
     만약 출근시간이 없으면  badrequest와 할께 출근시간이없습니다 body에담아서반환
     existingEndTime에 퇴근시간을 조회하는 데이터를 담아옵니다
     만약 퇴근시간이 있으면  badrequest와 할께 퇴근시간이있습니다 body에담아서반환
     dto가 데이터가 없으면  badrequest와 할께 데이터가 안담김을 body에 담아서 반환한다
    모든요청이 정상적이면 AttendanceEndDto 내용을 body에 사원id 퇴근시간요청내용을 반한환다
    */





    //년월일 타사원정보검색 년월만 입력하면 년월만 입력데이터 적용되게 구현//manager로 넘길메서드
    @GetMapping("/attendance_info/{employee_id}/")
    public ResponseEntity<List<AttendanceInfoDto>> getAttendanceInfoOfEmployeeByDay(
            @PathVariable("employee_id") String employeeId,
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            @RequestParam(value = "day", required = false) Integer day) {

        if (!employeeValidation(employeeId)) {
            log.info("Invalid employee ID: " + employeeId);
            return ResponseEntity.badRequest().build();
        }

        if (day != null && !isValidDate(year, month, day)) {
            log.info("Invalid date: year={}, month={}, day={}", year, month, day);
            return ResponseEntity.badRequest().build();
        }

        List<AttendanceInfoDto> attendanceInfo;

        if (day != null) {
            // 일 데이터가 있으면 해당 날짜로 검색
            LocalDate attendanceDate = LocalDate.of(year, month, day);
            attendanceInfo = employeeService1.getAttendanceByDateAndEmployee(attendanceDate, employeeId);
        } else {
            // 일 데이터가 없으면 해당 월로 검색
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);
            attendanceInfo = employeeService1.getAttendanceByMonthAndEmployee(startDate, endDate, employeeId);
        }

        if (attendanceInfo.isEmpty()) {
            log.info("No attendance records found for employeeId: " + employeeId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(attendanceInfo);
    }

     //TODO: 모든 내역조회에 페이징네이션 적용


    //년월일 자신의 근태정보조회 년월만 입력하면 년월만 입력데이터 적용되게 구현
    @GetMapping("/attendance_info/")
    public ResponseEntity<List<AttendanceInfoDto>> getAttendanceInfoOfMineByDay(HttpServletRequest request,
                                                                                @RequestParam("year") int year,
                                                                                @RequestParam("month") int month,
                                                                                @RequestParam(value = "day", required = false) Integer day) {

        HttpSession session = request.getSession();
        // String employeeId = (String) session.getAttribute("employeeId");

        if (day != null && !isValidDate(year, month, day)) {
            log.info("Invalid date: year={}, month={}, day={}", year, month, day);
            return ResponseEntity.badRequest().build();
        }

        String tempEmployeeId = "emp01";

        if (!employeeValidation(tempEmployeeId)) {
            return ResponseEntity.badRequest().build();
        }

        List<AttendanceInfoDto> attendanceInfo;

        if (day != null) {
            LocalDate attendanceDate = LocalDate.of(year, month, day);
            attendanceInfo = employeeService1.getAttendanceByDateAndEmployee(attendanceDate, tempEmployeeId);
        } else {
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);
            attendanceInfo = employeeService1.getAttendanceByMonthAndEmployee(startDate, endDate, tempEmployeeId);
        }

        if (attendanceInfo.isEmpty()) {
            log.info("No attendance records found for employeeId: {}", tempEmployeeId);
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
    public ResponseEntity<AttendanceApprovalDto> makeApproveRequest(HttpServletRequest request) {
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

        AttendanceApprovalDto attendanceApprovalDto = employeeService1.approveAttendance(attendanceInfoId, employeeId);
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
    public ResponseEntity<List<AttendanceApprovalDto>> getHistoryOfApproveOfMine(HttpServletRequest request){
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

        List<AttendanceApprovalDto> approvalInfoDtos = employeeService1.findApprovalInfoByMine(employeeId);

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











