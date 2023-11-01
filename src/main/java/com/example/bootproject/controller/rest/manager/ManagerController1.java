package com.example.bootproject.controller.rest.manager;
import com.example.bootproject.service.service1.ManagerService1;
import com.example.bootproject.vo.vo1.request.AttendanceApprovalRequestDto;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/manager")
@Slf4j
@RequiredArgsConstructor
public class ManagerController1 {

    private final ManagerService1 managerService1;

    //정규출퇴근시간 설정
    @PostMapping("/adjustment")
    public ResponseEntity<RegularTimeAdjustmentHistoryDto> setWorkTime(RegularTimeAdjustmentHistoryDto dto, HttpServletRequest httpServletRequest,
                                                                       @RequestBody RegularTimeAdjustmentHistoryDto request) {
        log.info("RegularTimeAdjustmentHistroyDto dto{}:",dto);
        if (!ManagerCheckApi()) {
            // 권한이 없다면 403 Forbidden 응답을 반환합니다.
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        HttpSession session = httpServletRequest.getSession();

        String employeeId = "emp01";

            RegularTimeAdjustmentHistoryDto result = managerService1.insertRegularTimeAdjustmentHistory(request, employeeId);
        if (result!=null) {
            log.info("성공적으로 데이터를 받았습니다: {}", result);
            return ResponseEntity.ok(result);
        } else {
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


    public boolean ManagerCheckApi(){
        return true;
    }

    public boolean employeeValidation(String employeeId){
        return employeeId.matches("^[0-9]*$");
    }



}