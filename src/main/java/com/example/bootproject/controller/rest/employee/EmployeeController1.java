package com.example.bootproject.controller.rest.employee;
import com.example.bootproject.service.service1.EmployeeService1;

import com.example.bootproject.vo.vo1.page.Page;
import com.example.bootproject.vo.vo1.request.AttendanceInfoEndRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoStartRequestDto;
import com.example.bootproject.vo.vo1.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/employee")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController1 {

    private final EmployeeService1 employeeService1;


    @PostMapping("/attendance")
    public ResponseEntity<AttendanceInfoResponseDto> makeAttendanceInfo(AttendanceInfoStartRequestDto requestDto) {

        String employeeId = "emp01";

        if(!employeeValidation(employeeId)){
            log.info("employeeId 유효성 검사 실패: " + employeeId);
            return ResponseEntity.badRequest().build();
        }


        AttendanceInfoResponseDto responseDto = employeeService1.makeStartResponse(requestDto, employeeId);
        if (responseDto.getMessage() != null) {
            // 오류 메시지가 있는 경우, 클라이언트에게 메시지를 포함하여 응답
            return ResponseEntity.badRequest().body(responseDto);
        }

        if (responseDto == null) {
            log.info("반환값이 null이다: " + employeeId);
            return ResponseEntity.badRequest().build();
        }




        return ResponseEntity.ok(responseDto);
    }

/*
  - 사원 ID의 유효성을 검증한다 처리중 null이 반환되면 BadRequest 응답을 반환한다
  - 요청된 DTO가 null이거나 처리 중 null이 반환되면 BadRequest 응답을 반환한다.
  - 사원 ID가 데이터베이스에 없다면 BadRequest 응답을 반환한다.
  - 사원 ID 유효성 검사를 통과하고 출근 정보가 성공적으로 생성되면, OK 응답과 함께 출근 정보 DTO를 반환한다.
*/



    //톼근처리
    @PostMapping("/leave")
    public ResponseEntity<AttendanceInfoResponseDto> makeLeaveInformation(AttendanceInfoEndRequestDto requestDto) {


        String employeeId = "emp01";

        if(!employeeValidation(employeeId)){
            log.info("employeeId 유효성 검사 실패: " + employeeId);
            return ResponseEntity.badRequest().build();
        }



        AttendanceInfoResponseDto responseDto = employeeService1.makeEndResponse(requestDto, employeeId);




        if (responseDto == null) {
            log.info("사원 ID에 대한 출근 기록 실패: " + employeeId);
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.ok(responseDto);
    }

/*
  - 사원 ID의 유효성을 검증한다,사원id의 형식이 맞지않으면 BadRequest을 반환한다
  - 요청된 DTO의 내용이 올바르지 않으면, 즉 null일 때는 BadRequest를 반환한다.
  - 사원 ID가 시스템에 등록되어 있지 않다면, BadRequest를 반환한다.
  - 서비스 계층을 통해 퇴근 처리를 하고, 그 결과를 AttendanceInfoResponseDto에 담아 반환한다.
  - 처리 과정에서 어떤 이유로든 실패한다면, 로그를 남기고 BadRequest를 반환한다.
*/






    //년월일 자신의 근태정보조회 년월만 입력하면 년월만 입력데이터 적용되게 구현
    @GetMapping("/attendance_info/")
    public ResponseEntity<Page<List<AttendanceInfoResponseDto>>> getAttendanceInfoOfEmployeeByDay(
                                                                                        @RequestParam("year") int year,
                                                                                        @RequestParam("month") int month,
                                                                                        @RequestParam(value = "day", required = false) Integer day,
                                                                                        @RequestParam(name = "page", defaultValue = "1") int page,
                                                                                        @RequestParam(name="sort" ,defaultValue = "attendance_date")String sort,
                                                                                        @RequestParam(name = "desc",defaultValue = "DESC")String desc) {

        String employeeId = "emp01"; // 실제 어플리케이션에서는 세션에서 가져오거나 인증 메커니즘을 사용해야 함

        // 입력된 날짜가 유효한지 검증
        if (day != null && !isValidDate(year, month, day)) {
            log.info("유효하지 않은 날짜: year={}, month={}, day={}", year, month, day);
            return ResponseEntity.badRequest().build();
        }

        // 사원 ID 유효성 검사
        if (!employeeValidation(employeeId)) {
            return ResponseEntity.badRequest().build();
        }

        // 근태 정보 조회
        Page<List<AttendanceInfoResponseDto>> attendanceInfo;
        if (day != null) {
            // 날짜를 기준으로 근태 정보 조회
            LocalDate attendanceDate = LocalDate.of(year, month, day);
            attendanceInfo = employeeService1.getAttendanceByDateAndEmployee(attendanceDate, employeeId,sort,desc,page);
        } else {
            // 월을 기준으로 근태 정보 조회
            LocalDate attendanceDate = LocalDate.of(year, month ,1);
            attendanceInfo = employeeService1.getAttendanceByMonthAndEmployee(year,month,employeeId,page,sort,desc);
        }

        // 조회된 근태 정보가 없을 경우
        if (attendanceInfo == null) {
            log.info("해당 사원의 근태 기록이 없습니다: {}", employeeId);
            return ResponseEntity.noContent().build();
        }

        // 근태 정보 반환
        return ResponseEntity.ok(attendanceInfo);
    }

/*
  - 세션에서 사원 ID를 가져온다. 현재는 'emp01'로 하드코딩 되어 있음.
  - 날짜 데이터 형식이 올바르지 않으면 오류 코드를 반환한다.
  - 올바른 데이터가 들어오면 로그를 남긴다.
  - LocalDate를 사용하여 입력된 날짜의 유효성을 검증한다.
  - 사원 ID의 유효성을 검증한다.
  - 모든 조건이 성공하면 200 응답 코드와 함께 근태 정보를 반환한다.
  - 일자가 주어지면 해당 날짜에 대한 근태 정보를 조회한다.
  - 일자가 주어지지 않으면 해당 월의 근태 정보를 조회한다.
  - 조회된 근태 정보 리스트를 반환한다.
*/


    //자신의 근태 승인요청
    @PostMapping("/approve/{attendanceInfoId}")
    public ResponseEntity<AttendanceApprovalResponseDto> makeApproveRequest(@PathVariable("attendanceInfoId") Long attendanceInfoId) {
        String employeeId = "emp01";



        if (!employeeValidation(employeeId)) {
            log.info("유효하지 않은 사원 ID입니다: {}", employeeId);
            return ResponseEntity.badRequest().build();
        }


        if (attendanceInfoId == null || employeeId == null) {
            log.info("근태 정보 ID 또는 사원 ID가 누락되었습니다.");
            return ResponseEntity.badRequest().build();
        }


        if (!employeeValidation(employeeId)) {
            log.info("사원 ID 유효성 검사에 실패하였습니다: {}", employeeId);
            return ResponseEntity.badRequest().build();
        }


        AttendanceApprovalResponseDto approvalResponseDto = employeeService1.approveAttendance(employeeId, attendanceInfoId);
        log.info("승인 요청 결과: {}", approvalResponseDto);
        return ResponseEntity.ok(approvalResponseDto);
    }
/*
  - 쿼리 파라미터로 attendanceInfoId를 받아온다.
  - 세션 또는 인증 메커니즘으로부터 employeeId를 가져온다.
  - 만약 사원 ID나 근태 정보 ID가 전달되지 않으면 오류 코드를 반환한다.
  - 데이터가 전달되면 로그에 해당 데이터를 기록한다.
  - 사원 ID가 유효한지 검사한다.
  - 모든 조건을 만족시킨 후에는 승인 처리 결과를 AttendanceApprovalResponseDto로 반환한다.
*/




    //자신의 근태승인내역
    @GetMapping("/approve")
    public ResponseEntity<Page<List<AttendanceApprovalUpdateResponseDto>>> getHistoryOfApproveOfMine(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "sort", defaultValue = "attendance_approval_date") String sort,
            @RequestParam(name = "desc", defaultValue = "DESC") String desc) {

        String employeeId = "emp01"; // 이 부분은 실제로는 보안 컨텍스트에서 동적으로 가져와야 합니다.




        // 직원 ID 검증
        if (!employeeValidation(employeeId)) {
            log.error("잘못된 직원 ID: {}", employeeId);
            return ResponseEntity.badRequest().build();
        }

        // 페이지 번호 검증
        if (page < 1) {
            log.error("잘못된 페이지 번호: {}", page);
            return ResponseEntity.badRequest().build();
        }

        // 정렬 필드 검증
        if (!(sort.equals("attendance_approval_date") || sort.equals("anotherField"))) {
            log.error("잘못된 정렬 필드: {}", sort);
            return ResponseEntity.badRequest().build();
        }

        // 정렬 방향 검증
        if (!(desc.equalsIgnoreCase("ASC") || desc.equalsIgnoreCase("DESC"))) {
            log.error("잘못된 정렬 방향: {}", desc);
            return ResponseEntity.badRequest().build();
        }

        try {
            // 승인 내역 검색 시도
            Page<List<AttendanceApprovalUpdateResponseDto>> approvalPage =
                    employeeService1.getApprovalInfoByEmployeeId(employeeId, page, sort, desc);

            // 데이터가 없을 경우
            if (approvalPage == null || approvalPage.getData().isEmpty()) {
                log.info("승인 내역이 없습니다.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            // 성공적으로 처리되면 데이터와 함께 OK 상태 코드 반환
            return ResponseEntity.ok(approvalPage);
        } catch (Exception e) {
            // 오류 로깅
            log.error("승인 내역을 검색하는 동안 오류가 발생했습니다: {}", e.getMessage());
            // 서버 내부 오류로 인한 요청 실패
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
    @GetMapping("/appeal/requests/")
    public ResponseEntity<Page<List<AttendanceAppealMediateResponseDto>>> getAppealHistoryOfEmployee(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "sort", defaultValue = "attendance_appeal_request_time") String sort,
            @RequestParam(name = "desc", defaultValue = "DESC") String desc) {

        String employeeId = "emp01"; // Assume this is retrieved from session or security context

        // 직원 ID 검증
        if (!employeeValidation(employeeId)) {
            log.error("잘못된 직원 ID: {}", employeeId);
            return ResponseEntity.badRequest().build();
        }

        // 'page' 파라미터 유효성 검증
        if (page < 1) {
            log.error("잘못된 페이지 번호: {}", page);
            return ResponseEntity.badRequest().build();
        }

        // 'sort' 파라미터 유효성 검증
        if (!(sort.equals("attendance_approval_date") || sort.equals("anotherField"))) {
            log.error("잘못된 정렬 필드: {}", sort);
            return ResponseEntity.badRequest().build();
        }

        // 'desc' 파라미터 유효성 검증
        if (!(desc.equalsIgnoreCase("ASC") || desc.equalsIgnoreCase("DESC"))) {
            log.error("잘못된 정렬 방향: {}", desc);
            return ResponseEntity.badRequest().build();
        }

        try {
            // 승인 내역 검색 시도
            Page<List<AttendanceAppealMediateResponseDto>> appealPage =
                    employeeService1.findAttendanceInfoByMine(employeeId, page, sort, desc);

            // 데이터가 없을 경우
            if (appealPage == null){
                log.info("항소 내역이 없습니다.");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            // 성공적으로 처리되면 데이터와 함께 OK 상태 코드 반환
            return ResponseEntity.ok(appealPage);
        } catch (Exception e) {
            // 오류 로깅
            log.error("항소 내역을 검색하는 동안 오류가 발생했습니다: {}", e.getMessage());
            // 서버 내부 오류로 인한 요청 실패
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


     /*
    - 세션에서 사원 ID를 추출한다. (현재 코드는 임시로 고정값 'emp01'을 사용하고 있다)
    - 사원 ID가 넘어오지 않을 경우, 로그를 남기고 204 No Content 응답을 반환한다.
    - 사원 ID에 대한 유효성 검사를 수행한다.
    - 유효성 검사에 실패할 경우 page,sort,desc 로그를 남기고 400 Bad Request 응답을 반환한다.
    - 사원 ID에 대한 근태 승인 이력을 조회한다.
    - 조회된 승인 이력이 없을 경우, 로그를 남기고 204 No Content 응답을 반환한다.
    - 조회에 성공한 경우, 승인 이력 목록을 담아 200 OK 응답과 함께 반환한다.
    */


    @GetMapping("/search")
    public ResponseEntity<List<EmployeeSearchResponseDto>> searchEmployeeByIdOrNumber(@RequestParam String searchParameter) {

        List<EmployeeSearchResponseDto> searchResults = employeeService1.searchByEmployeeIdOrName(searchParameter);

        if (searchResults.isEmpty()) {
            log.info("검색결과가 존재하지않습니다");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(searchResults);


    }
    /*
     * 사원을 ID나 이름으로 검색하는 엔드포인트.
     *
     * - 검색 파라미터가 유효하지 않을 경우 (null이거나 공백만 있는 경우)
     *   -> 클라이언트에게 400 Bad Request 응답을 반환한다.
     *
     * - 검색 결과가 없을 경우
     *   로그 검색결과가 존재하지않습니다
     *   -> 클라이언트에게 404 Not Found 응답을 반환한다.
     *
     * - 검색 결과가 있을 경우
     *   -> 클라이언트에게 200 OK 응답과 함께 검색된 사원 목록을 반환한다.
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











