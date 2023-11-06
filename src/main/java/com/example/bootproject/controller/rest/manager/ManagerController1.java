package com.example.bootproject.controller.rest.manager;
import com.example.bootproject.service.service1.EmployeeService1;
import com.example.bootproject.service.service1.ManagerService1;
import com.example.bootproject.vo.vo1.page.Page;
import com.example.bootproject.vo.vo1.request.AttendanceApprovalUpdateRequestDto;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryRequestDto;
import com.example.bootproject.vo.vo1.response.*;
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

         log.info("RegularTimeAdjustmentHistoryDto dto{}:", dto);
         if (ManagerCheckApi()) {
             // 권한이 없으면 403 금지 응답을 반환합니다.
             return new ResponseEntity<>(HttpStatus.FORBIDDEN);
         }

        log.info("요청 내용{}", dto);

        String employeeId = "emp01"; // 이는 예시입니다. 실제 구현에서는 세션 또는 요청에서 직원 ID를 받아야 합니다.

        RegularTimeAdjustmentHistoryResponseDto responseDto = managerService1.insertRegularTimeAdjustmentHistory(dto, employeeId);
        if (responseDto != null) {
            log.info("데이터가 성공적으로 처리되었습니다: {}", responseDto);
            return ResponseEntity.ok(responseDto);
        } else {
            log.info("처리할 수 없는 요청입니다: {}", responseDto);
            return ResponseEntity.badRequest().build();
        }
    }

    /*
    세션에서 employeeId 가져온다 지금은 하드코딩
    사원id데이터 형식이 이상하게 넘어올경우 오류코드
    데이터가 제데로 들어오면 log남김
    dto반환데이터가 null이면 400오로 아니면 200응답
    모든 조건 성공시 RegularTimeAdjustmentHistoryDto 출력
    TODO 데이터에대한 VALIDATIONCHECK는 컨트롤러에서
    */





    //타사원에 대한 근태 이상 승인 내역
    @GetMapping("/approve/{employeeId}")
    public ResponseEntity<Page<List<AttendanceApprovalUpdateResponseDto>>> getHistoryOfApproveOfMine(
            @PathVariable("employeeId") String employeeId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "sort", defaultValue = "attendance_approval_date") String sort,
            @RequestParam(name = "desc", defaultValue = "DESC") String desc) {

        // 'employeeId'가 세션 또는 보안 컨텍스트에서 검색된 것으로 가정합니다.

        if (ManagerCheckApi()) {
            // 권한이 없으면 403 금지 응답을 반환합니다.
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }


        // 'page' 매개변수 유효성 검증
        if (page < 1) {
            log.error("유효하지 않은 페이지 번호: {}", page);
            // 예외를 던지는 대신 에러를 로깅하고 계속 진행합니다.
        }

        // 'sort' 매개변수 유효성 검증
        if (!(sort.equals("attendance_approval_date") || sort.equals("anotherField"))) {
            log.error("유효하지 않은 정렬 필드: {}", sort);
            // 예외를 던지는 대신 에러를 로깅하고 계속 진행합니다.
        }

        // 'desc' 매개변수 유효성 검증
        if (!(desc.equalsIgnoreCase("ASC") || desc.equalsIgnoreCase("DESC"))) {
            log.error("유효하지 않은 정렬 방향: {}", desc);
            // 예외를 던지는 대신 에러를 로깅하고 계속 진행합니다.
        }

        // 예외를 던지지 않고 작업을 계속 진행합니다.
        Page<List<AttendanceApprovalUpdateResponseDto>> approvalPage =
                managerService1.managerGetApprovalInfoByEmployeeId(employeeId, page, sort, desc);

        // 결과를 반환합니다.
        return ResponseEntity.ok(approvalPage);
    }

    //TODO 사원id가 존재하는지에대해서 본다 나중에생각


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
    @GetMapping("/attendance_info/{employeeId}")
    public ResponseEntity<Page<List<AttendanceInfoResponseDto>>> getAttendanceInfoOfMineByDay(
            @PathVariable("employeeId")String employeeId,
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            @RequestParam(value = "day", required = false) Integer day,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name="sort" ,defaultValue = "attendance_date")String sort,
            @RequestParam(name = "desc",defaultValue = "DESC")String desc) {

        // 세션에서 사원 ID 가져오기. 현재는 하드코딩된 ID 'emp01' 사용 중
        // HttpSession session = request.getSession();
        // String employeeId = (String) session.getAttribute("employeeId");

        // 입력된 날짜가 유효한지 검증
        if (day != null && !isValidDate(year, month, day)) {
            log.info("유효하지 않은 날짜: year={}, month={}, day={}", year, month, day);
            return ResponseEntity.badRequest().build();
        }

        // 사원 ID 유효성 검사
//        if (!employeeValidation(employeeId)) {
//            return ResponseEntity.badRequest().build();
//        }

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



    @GetMapping("/appeal/requests/{employeeId}")
    public ResponseEntity<Page<List<AttendanceAppealMediateResponseDto>>> getAppealHistoryOfEmployee(
            @PathVariable("employeeId") String employeeId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "sort", defaultValue = "attendance_appeal_request_time") String sort,
            @RequestParam(name = "desc", defaultValue = "DESC") String desc) {

        // 세션 또는 보안 컨텍스트에서 'employeeId'가 검색되었다고 가정합니다.

        // 'page' 파라미터 유효성 검증
        if (page < 1) {
            log.error("잘못된 페이지 번호: {}", page);
            // 예외를 던지는 대신 오류를 로깅하고 계속 진행합니다.
        }

        // 'sort' 파라미터 유효성 검증
        if (!(sort.equals("attendance_approval_date") || sort.equals("anotherField"))) {
            log.error("잘못된 정렬 필드: {}", sort);
            // 예외를 던지는 대신 오류를 로깅하고 계속 진행합니다.
        }

        // 'desc' 파라미터 유효성 검증
        if (!(desc.equalsIgnoreCase("ASC") || desc.equalsIgnoreCase("DESC"))) {
            log.error("잘못된 정렬 방향: {}", desc);
            // 예외를 던지는 대신 오류를 로깅하고 계속 진행합니다.
        }

        // 예외를 던지지 않고 연산을 계속 진행합니다.
        Page<List<AttendanceAppealMediateResponseDto>> appealPage =
                managerService1.managerfindAttendanceInfoByMine(employeeId, page, sort, desc);

        // 결과를 반환합니다.
        return ResponseEntity.ok(appealPage);
    }

    /*

    - 쿼리파라미터로 ID를 추출한다.
    - 사원 ID가 넘어오지 않을 경우, 로그를 남기고 204 No Content 응답을 반환한다.
    - 사원 ID에 대한 유효성 검사를 수행한다.
    - 유효성 검사에 실패할 경우, 로그를 남기고 400 Bad Request 응답을 반환한다.
    - 사원 ID에 대한 근태 승인 이력을 조회한다.
    - 조회된 승인 이력이 없을 경우, 로그를 남기고 204 No Content 응답을 반환한다.
    - 조회에 성공한 경우, 승인 이력 목록을 담아 200 OK 응답과 함께 반환한다.
    */





    public boolean ManagerCheckApi(){
        return true;
    }

    public boolean employeeValidation(String employeeId){
        return employeeId.matches("^[0-9]*$");
    }



}