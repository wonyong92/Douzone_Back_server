package com.example.bootproject.controller.rest.employee;
import com.example.bootproject.service.service1.EmployeeService1;

import com.example.bootproject.vo.vo1.request.AttendanceApprovalUpdateRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoEndRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoStartRequestDto;
import com.example.bootproject.vo.vo1.response.AttendanceAppealMediateResponseDto;
import com.example.bootproject.vo.vo1.response.AttendanceApprovalResponseDto;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.DateTimeException;
import java.time.LocalDate;
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
            // 직접 할당한 더 미 데이터
            String employeeId = "emp01";
            AttendanceInfoResponseDto responseDto = employeeService1.makeStartResponse(requestDto, employeeId);


//            if(!employeeValidation(employeeId)){
//                log.info("not collect validationcheck" + employeeId);
//                return ResponseEntity.badRequest().build();
//            }

            // 결과에 따라 응답을 반환한다
//            if (responseDto == null) {
//                log.info("반환값이 null이다: " + employeeId);
//                return ResponseEntity.badRequest().build();
//            }

//            if(!employeeValidation(employeeId)){
//                log.info("employeeId not collect validationcheck" + employeeId);
//                return ResponseEntity.badRequest().build();
//            }//로그인에서



            return ResponseEntity.ok(responseDto);
        }
/*
세션에 employeeId validation체크
세션에 DTO의 내용이 null값이때 badrequest
 */



    //톼근처리
    @PostMapping("/leave")
    public ResponseEntity<AttendanceInfoResponseDto> makeLeaveInformation(AttendanceInfoEndRequestDto requestDto){

        String employeeId = "emp01";
//
//        if(!employeeValidation(employeeId)){
//            log.info("not collect validationcheck" + employeeId);
//            return ResponseEntity.badRequest().build();
//        }

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
    @PostMapping("/approve/{attendanceInfoId}")
    public ResponseEntity<AttendanceApprovalResponseDto> makeApproveRequest(@PathVariable("attendanceInfoId") Long attendanceInfoId) {
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


        AttendanceApprovalResponseDto approvalResponseDto = employeeService1.approveAttendance( employeeId,attendanceInfoId);
        log.info(String.valueOf(approvalResponseDto));
        return ResponseEntity.ok(approvalResponseDto);
    }

     /*
     //todo string데이터가 들어오면 error처리 validation체크
     //todo 권환오류 null값 badrequest 확인해준다 db오류인지 exception
     // todo dto 에다가 데이터를 담아서 보낸다
     //todo 근태이상확인 컨트롤러에 넘겨줘야한다

    쿼리 파라미터로 attendanceInfoid를 찾는다
    세션에서 attendanceInfoId와 employeeId 가져오기
    사원id나 근태정보id가 안넘어올경우 오류코드
    데이터가 들어올시 log데이터가 넘어옴
    사원id validation체크
    모든 조건 달성시 attendanceApprovalDto 반환
     */




    //자신의 근태승인내역
    @GetMapping("/approves")
    public ResponseEntity<List<AttendanceApprovalUpdateRequestDto>> getHistoryOfApproveOfMine(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String employeeId = (String) session.getAttribute("EMPLOYEE_ID"); // 세션에서 사원 ID를 가져옵니다.

        // 사원 ID가 세션에 없는 경우, 로그를 남기고 No Content를 반환합니다.
        if (employeeId == null || employeeId.trim().isEmpty()) {
            log.info("사원 ID가 누락되었습니다.");
            return ResponseEntity.noContent().build();
        }

        // 사원 ID의 유효성을 검증합니다.
        if (!employeeValidation(employeeId)) {
            log.info("유효하지 않은 사원 ID입니다: {}", employeeId);
            return ResponseEntity.badRequest().build();
        }

        // 사원의 승인 이력을 조회합니다.
        List<AttendanceApprovalUpdateRequestDto> approvalInfoDtos = employeeService1.findApprovalInfoByMine(employeeId);

        // 조회된 승인 이력이 없는 경우, 로그를 남기고 No Content를 반환합니다.
        if (approvalInfoDtos == null || approvalInfoDtos.isEmpty()) {
            log.info("해당 사원의 승인 이력이 존재하지 않습니다: {}", employeeId);
            return ResponseEntity.noContent().build();
        }

        // 승인 이력이 있는 경우, 조회된 승인 이력 목록을 반환합니다.
        return ResponseEntity.ok(approvalInfoDtos);
    }





    /*
    - 세션에서 사원 ID를 추출한다. (현재 코드는 임시로 고정값 'emp01'을 사용하고 있다)
    - 사원 ID가 넘어오지 않을 경우, 로그를 남기고 204 No Content 응답을 반환한다.
    - 사원 ID에 대한 유효성 검사를 수행한다.
    - 유효성 검사에 실패할 경우, 로그를 남기고 400 Bad Request 응답을 반환한다.
    - 사원 ID에 대한 근태 승인 이력을 조회한다.
    - 조회된 승인 이력이 없을 경우, 로그를 남기고 204 No Content 응답을 반환한다.
    - 조회에 성공한 경우, 승인 이력 목록을 담아 200 OK 응답과 함께 반환한다.
*/


    //자신의근태조정내역
    @GetMapping("/appeal/requests")
    public ResponseEntity<AttendanceAppealMediateResponseDto> getAppealRequestHistoryOfMine() {

        String employeeId = "emp01";


//        if (employeeId == null) {
//            log.info("세션에서 사원 ID를 찾을 수 없습니다.");
//            return ResponseEntity.noContent().build();
//        }


        if (!employeeValidation(employeeId)) {
            log.info("유효하지 않은 사원 ID: {}", employeeId);
            return ResponseEntity.badRequest().build();
        }


        AttendanceAppealMediateResponseDto responseDto = employeeService1.findAttendanceInfoByMine(employeeId);


        if (responseDto == null) {
            log.info("해당 사원의 근태 이의 신청 이력이 없습니다: {}", employeeId);
            return ResponseEntity.noContent().build();
        }


        return ResponseEntity.ok(responseDto);
    }


     /*
    - 세션에서 사원 ID를 추출한다. (현재 코드는 임시로 고정값 'emp01'을 사용하고 있다)
    - 사원 ID가 넘어오지 않을 경우, 로그를 남기고 204 No Content 응답을 반환한다.
    - 사원 ID에 대한 유효성 검사를 수행한다.
    - 유효성 검사에 실패할 경우, 로그를 남기고 400 Bad Request 응답을 반환한다.
    - 사원 ID에 대한 근태 승인 이력을 조회한다.
    - 조회된 승인 이력이 없을 경우, 로그를 남기고 204 No Content 응답을 반환한다.
    - 조회에 성공한 경우, 승인 이력 목록을 담아 200 OK 응답과 함께 반환한다.
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











