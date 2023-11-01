package com.example.bootproject.controller.rest.manager;

import com.example.bootproject.service.service2.ManagerService2;
import com.example.bootproject.vo.vo2.response.EmployeeDto;

import com.example.bootproject.vo.vo2.response.SettingWorkTimeDto;

import com.example.bootproject.vo.vo2.response.VacationQuantitySettingDto;

import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ManagerController2 {
    private final ManagerService2 manService2;

    public boolean authCheckApi(){ //권한 확인 API
        //로그인 확인 포함 해야한다
        return true;
    }

    public boolean validationId(String employeeId){ // ID Validation 확인
        return employeeId.matches("^[0-9]*$"); // 숫자로 구성 되어 있는지 확인
    }

    public boolean validationDate(int year, int month, int day ){ //날짜 Validation 확인
        if (year<1 || month<1 ||month>12) // year가 양수가 아니거나, month가 1보다 작거나 12보다 클때 false
            return false;
        int[] lastDay = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //각 월별 마지막 날

        if(year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
            lastDay[1]=29; //윤년일 때는 2월의 마지막 날을 29일로 설정

        if(day <1 || lastDay[month-1]<day )
            return false; // day가 1보다 작거나 마지막날 보다 클 떄 false
        return true; // 이외의 경우는 true 반환
    }

    //전체 연차 요청 정보 조회 메서드
    /*TODO : 추후 권한 확인 추가*/
    /* TODO : 추후 페이지네이션 , 페이지네이션 validation 체크 추가 */
    @GetMapping("/manager/vacation/requests")
    public ResponseEntity<List<VacationRequestDto>> getRequestVacationInformationOfAll(@RequestParam(name = "year") int year, @RequestParam(name = "month") int month, @RequestParam(name = "day") int day) {
        if(authCheckApi()){
            if(!validationDate(year,month,day)){
                return ResponseEntity.badRequest().build(); //400 Bad Request
            }
            String date = String.format("%04d-%02d-%02d", year, month, day); // year-month-day 형태의 문자열로 변환
            List<VacationRequestDto> result = manService2.getAllVacationHistory(date);
            log.info("getAllVacationHistory result : {}",result);
            if(result.isEmpty()){
                return ResponseEntity.noContent().build(); // 204 No Content
            }
            return ResponseEntity.ok(result); // 200 OK
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 ERROR

        /*
        * 근태 담당자 권한 확인
        * 권한 확인 성공시 -> 받아온 날짜 데이터에 대한 validation check
        *               -> validation check가 성공 일 경우
        *                   받아온 날짜 데이터를 String 형의 'year-month-day' 형태로 변환
        *                   해당 날짜에 대한 전체 연차 요청 정보 반환 받음
        *                   반환 값이 비어있을 때 - 204 No Content 응답 반환
        *                   반환 값이 비어있지 않을 때 - 200 OK 응답 반환
        *               -> validation check가 실패 일 경우 400 Bad Request 반환
        * 권한 확인 실패시 ->  403 에러 반환
        * */
    }

    //타 사원의 연차 요청 정보 조회 메서드
    /*TODO : 추후 권한 확인 추가*/
    /* TODO : 추후 페이지네이션 , 페이지네이션 validation 체크 추가 */
    @GetMapping("/manager/vacation/requests/{employee_id}")
    public ResponseEntity<List<VacationRequestDto>> getRequestVacationInformationOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        if(authCheckApi()){
            if(!validationId(employeeId))
                return ResponseEntity.badRequest().build(); //400 Bad Request
            List<VacationRequestDto> result = manService2.getEmpReqVacationHistory(employeeId);
            log.info("getEmpReqVacationHistory result : {}",result);
            if(result.isEmpty())
                return ResponseEntity.noContent().build(); // 204 No Content
            return ResponseEntity.ok(result); //200 OK
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 ERROR
        /*
        * 근태 담당자 권한 확인
        * 권한 확인 성공 시 -> 경로변수로 받아온 id의 validation 확인
        *                -> validation 확인 성공시 타 사원의 연차 요청 정보 반환 받음
        *                   반환 값이 비어있을 때 - 204 No Content 응답 반환
        *                   반환 값이 비어있지 않을 때 - 200 OK 응답 반환
        *                -> validation 확인 실패 시 400 Bad Request 반환
        * 권한 확인 실패 시 -> 403 ERROR 반환
        * */
    }

    // 정규 출/퇴근 시간 설정 내역 확인 메서드
    /*TODO : 추후 권한 확인 추가*/
    /* TODO : 추후 페이지네이션 , 페이지네이션 validation 체크 추가 */
    @GetMapping("/manager/setting_history/work_time")
    public ResponseEntity<List<SettingWorkTimeDto>> settingWorkTime() {
        if(authCheckApi()) {
            List<SettingWorkTimeDto> result = manService2.getSettingWorkTime();
            log.info("getSettingWorkTime result : {}",result);
            if (result.isEmpty()) {
                return ResponseEntity.noContent().build(); //204 No Content
            }
            return ResponseEntity.ok(result); //200 OK
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN) ; //403 ERROR
        /*
        * 근태담당자 권한 확인
        * 권한 확인 성공시 -> 정규 출/퇴근 시간 설정 내역 정보 반환 받음
        *                   가져온 데이터가 비었을 때 - 204 No Content 반환
        *                   가져온 데이터가 비어있지 않을 때 - 200 OK 반환
        * 권한 확인 실패시 -> 403 에러 반환
        * */
    }


    //근속 연수에 따른 기본 부여 연차 개수 설정 내역 확인 메서드
    /*TODO : 추후 권한 확인 추가*/
    /* TODO : 추후 페이지네이션 , 페이지네이션 validation 체크 추가 */
    @GetMapping("/manager/vacation/setting_history/vacation_default")
    public ResponseEntity<List<VacationQuantitySettingDto>>  getHistoryOfvacationDefaultSetting() {
        if(authCheckApi()){
            List<VacationQuantitySettingDto> result = manService2.getVacationSettingHistory();
            log.info("getVacationSettingHistory result : {}",result);
            if(result.isEmpty()){
                return ResponseEntity.noContent().build(); //204 No Content
            }
            return ResponseEntity.ok(result); //200 OK
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 ERROR
        /*
        * 근태 담당자 권한 확인
        * 권한 확인 성공 시 -> 근속 연수에 따른 기본 부여 연차 개수 설정 내역 정보 반환 받음
        *                  가져온 데이터가 비었을 때 - 204 No Content 반환
        *                  가져온 데이터가 비어있지 않을 때 - 200 OK
        * 권한 확인 실패 시 -> 403 ERROR 반환
        * */
    }

    // 타 사원의 반려된 연차 이력 조회 메서드
    /* TODO : 추후 권한 확인 추가 */
    /* TODO : 추후 페이지네이션 , 페이지네이션 validation 체크 추가 */
    @GetMapping("/manager/vacation/reject/{employee_id}")
    public ResponseEntity<List<VacationRequestDto>> getHistoryOfRejectedVacationOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        if(authCheckApi()){
            if(!validationId(employeeId)){
                return ResponseEntity.badRequest().build(); // 400 Bad Request
            }
            List<VacationRequestDto> result = manService2.getHistoryOfRejectedVacationOfEmployee(employeeId);
            log.info("getHistoryOfRejectedVacationOfEmployee result : {}",result);
            if(result.isEmpty()){
                return ResponseEntity.noContent().build(); // 204 No Content
            }
            return ResponseEntity.ok(result); //200 OK
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 ERROR
        /*
        * 근태 담당자 권한 확인
        * 권한 확인 성공 시 -> id validation check
        *                -> id validation check 성공시 타 사원의 반려된 연차 이력 정보 반환 받음
        *                   가져온 데이터가 비었을 때 - 204 No Content 반환
        *                   가져온 데이터가 비어있지 않을 때 - 200 OK 반환
        *                -> id validation check 실패시 400 Bad Request 반환
        * 권한 확인 실패 시 -> 403 error 반환
        * */
    }
}
