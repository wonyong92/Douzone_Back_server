package com.example.bootproject.controller.rest.manager;

import com.example.bootproject.service.service2.ManagerService2;
import com.example.bootproject.vo.vo2.request.DefaultVacationRequestDto;
import com.example.bootproject.vo.vo2.response.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ManagerController2 {
    private final ManagerService2 manService2;

    public boolean authCheckApi() { //권한 확인 API,
        // 로그인 확인
        return true;
    }

    public boolean validationId(String id) { // Id Validation 체크
        int idCheck = manService2.getEmployeeCheck(id); //id값이 실제로 테이블에 존재하면 1 반환
        /* employeeId가 숫자로 구성 되어 있고,  실제로 테이블에 존재하는지 확인 */
        return id.matches("^[0-9]+$")&&idCheck==1;
    }



    public int validationPageNum(String getPageNum){ //요청 받은 페이지에 대한 validation check
        try{
            int currentPage = Integer.parseInt(getPageNum); // 쿼리파라미터로 받아온 페이지 번호를 int 형으로 변환
            return (currentPage>0?currentPage:1);
        }catch (NumberFormatException e){
            return 1;
        }
    }

    public String validationSort(String getSort){
        return (getSort.matches("^[a-zA-Z_]+$")?getSort:"''");
    }



    public String validationDesc(String getSortOrder){
        return (getSortOrder.matches("^(desc|DESC|)$")?getSortOrder:"''");
    }

    public boolean validationDate(int year, int month, int day) { //날짜 Validation 확인
        if (year < 1 || month < 1 || month > 12) // year가 양수가 아니거나, month가 1보다 작거나 12보다 클때 false
            return false;
        int[] lastDay = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //각 월별 마지막 날

        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
            lastDay[1] = 29; //윤년일 때는 2월의 마지막 날을 29일로 설정

        if (day < 1 || lastDay[month - 1] < day)
            return false; // day가 1보다 작거나 마지막날 보다 클 떄 false
        return true; // 이외의 경우는 true 반환
    }

    public String validationStatus(String getStatus){
        return (getStatus.matches("^(승인|반려|)$")?getStatus:"''");
    }


    public boolean validationFreshman(int freshman){ //근속 연수 1년 미만 연차 개수 validation check
        return freshman>0;
    }

    public boolean validationSenior(int senior){ //근속 연수 1년 이상 연차 개수 validation check
        return senior>0;
    }


    public boolean validationTargetDate(LocalDate targetDate) { // 최소 내년 이면서  'yyyy-MM-dd' 형식 인지 확인

        // targetDate가 yyyy-MM-dd 형식인지 확인
        if (targetDate.toString().matches("^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])")) {

            // 현재 날짜 가져오기
            LocalDate today = LocalDate.now();

            // targetDate의 년도를 가져와서 현재 년도보다 큰지 확인
            return targetDate.getYear() > today.getYear();

        }
        return false; // yyyy-MM-dd 형식이 아니면 유효성 검사 실패
    }



    //전체 연차 요청 정보 조회 메서드
    /*TODO : 추후 권한 확인 추가*/

    @GetMapping("/manager/vacation/requests")
    public ResponseEntity<Page<List<VacationRequestDto>>> getRequestVacationInformationOfAll(@RequestParam(name = "year") int year, @RequestParam(name = "month") int month, @RequestParam(name = "day") int day, @RequestParam(name = "page",defaultValue = "1") String getPageNum, @RequestParam(name = "sort", defaultValue = "") String getSort, @RequestParam(name = "sortOrder", defaultValue = "") String getSortOrder) {
        if (authCheckApi()) {
            //페이징 코드
            if ( validationDate(year, month, day)) {
                int currentPage = validationPageNum(getPageNum);
                String sort = validationSort(getSort);
                String sortOrder = validationDesc(getSortOrder);
                log.info("getRequestVacationInformationOfAll의 sort, sortOrder : {} {}",sort,sortOrder);
                String date = String.format("%04d-%02d-%02d", year, month, day); // year-month-day 형태의 문자열로 변환

                PagingRequestWithDateDto pagingRequestWithDateDto = new PagingRequestWithDateDto(currentPage, sort, sortOrder, date);

                Page<List<VacationRequestDto>> result = manService2.getAllVacationHistory(pagingRequestWithDateDto); // 전체 사원 정보 반환
                log.info("getAllVacationHistory result : {}", result);
                if (result.getData().isEmpty()) {
                    return ResponseEntity.noContent().build(); // 204 No Content
                }
                return ResponseEntity.ok(result); // 200 OK
            }
            //로그
            log.info("Validation failed for year, month, day: {},{},{}", year, month, day);
            return ResponseEntity.badRequest().build(); //400 Bad Request
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);



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

    // 타 사원의 연차 이력 조회 메서드
    /* TODO : 추후 권한 확인 추가 */
    @GetMapping("/manager/vacation/history/{employee_id}")
    public ResponseEntity <Page<List<VacationRequestDto>>> getHistoryVacationOfEmployee(@PathVariable(name = "employee_id") String id,@RequestParam(name = "page", defaultValue = "1") String getPageNum, @RequestParam(name="sort", defaultValue = "") String getSort, @RequestParam(name="sortOrder", defaultValue = "") String getSortOrder,@RequestParam(name="status",defaultValue = "") String getStatus) {
        if(authCheckApi()){
            if(validationId(id)){
                int currentPage = validationPageNum(getPageNum);
                String sort = validationSort(getSort);
                String sortOrder = validationDesc(getSortOrder);
                String status = validationStatus(getStatus);
                PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto = new PagingRequestWithIdStatusDto(currentPage,sort,sortOrder,id,status);

                Page<List<VacationRequestDto>> result = manService2.getHistoryVacationOfEmployee(pagingRequestWithIdStatusDto);
                log.info("getHistoryOfUsedVacationOfEmployee result : {}", result);
                if (result.getData().isEmpty()) {
                    return ResponseEntity.noContent().build(); //204 No Content
                }
                return ResponseEntity.ok(result); //200 OK
            }
            log.info("Validation failed for id: {}", id);
            return ResponseEntity.badRequest().build(); //400 bad request

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 ERROR

        /*
         * 근태 담당자 권한 확인
         * 권한 확인 성공시 -> id의 validation 체크
         *               -> validation 체크 성공시 타 사원의 연차 사용 이력 데이터 반환 받음
         *                  반환한 데이터가 비어있을 때 : 204 No Content 반환
         *                  반환한 데이터가 비어있지 않을 때
         *  : 200 OK 반환
         *               -> validation 체크 실패 시 400 Bad Request 반환
         * 권한 확인 실패시 -> 403 ERROR 발생
         * */
    }

    // 정규 출/퇴근 시간 설정 내역 확인 메서드
    /*TODO : 추후 권한 확인 추가*/
    @GetMapping("/manager/setting_history/work_time")
    public ResponseEntity<Page<List<SettingWorkTimeDto>>> settingWorkTime(@RequestParam(name = "page",defaultValue = "1") String getPageNum, @RequestParam(name = "sort", defaultValue = "") String getSort, @RequestParam(name = "sortOrder", defaultValue = "") String getSortOrder) {
        if (authCheckApi()) {
            int currentPage = validationPageNum(getPageNum);
            String sort = validationSort(getSort);
            String sortOrder = validationDesc(getSortOrder);

            PagingRequestDto pagingRequestDto = new PagingRequestDto(currentPage, sort, sortOrder);
            Page<List<SettingWorkTimeDto>> result = manService2.getSettingWorkTime(pagingRequestDto);
            log.info("getSettingWorkTime result : {}", result);
            if (result.getData().isEmpty()) {
                return ResponseEntity.noContent().build(); //204 No Content
            }
            return ResponseEntity.ok(result); //200 OK

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 ERROR
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
    @GetMapping("/manager/vacation/setting_history/vacation_default")
    public ResponseEntity<Page<List<VacationQuantitySettingDto>>> getHistoryOfvacationDefaultSetting(@RequestParam(name = "page",defaultValue = "1") String getPageNum, @RequestParam(name = "sort", defaultValue = "") String getSort, @RequestParam(name = "sortOrder", defaultValue = "") String getSortOrder) {
        if (authCheckApi()) {
            int currentPage = validationPageNum(getPageNum);
            String sort = validationSort(getSort);
            String sortOrder = validationDesc(getSortOrder);

            PagingRequestDto pagingRequestDto = new PagingRequestDto(currentPage, sort, sortOrder);
            Page<List<VacationQuantitySettingDto>> result = manService2.getVacationSettingHistory(pagingRequestDto);
            log.info("getVacationSettingHistory result : {}", result);
            if (result.getData().isEmpty()) {
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


    // 근속 연수에 따른 연차 개수 설정
    @PostMapping("/manager/setting/vacation_default")
    public ResponseEntity<DefaultVacationResponseDto> setDefaultVacation(@ModelAttribute DefaultVacationRequestDto requestDto) {

        log.info("requestDto.getEmployeeId() : {}",requestDto.getEmployeeId());

        if(authCheckApi()){
            /* requestDto의 항목에 대한 validation check - 값 오류 혹은 비워있는지 확인*/
            if(validationFreshman(requestDto.getFreshman())&&validationSenior(requestDto.getSenior())&&validationTargetDate(requestDto.getTargetDate())){

                String id="1234"; //세션에서 가져온 ID
                requestDto.setEmployeeId(id);

                DefaultVacationResponseDto defaultVacationResponseDto = manService2.makeDefaultVacationResponse(requestDto);
                log.info("manService2.makeDefaultVacationResponse(requestDto,id)의 결과 : {}",defaultVacationResponseDto);

                log.info("OK");
                return ResponseEntity.ok(defaultVacationResponseDto); //200 OK
            }
            log.info("validation check fail");
            return ResponseEntity.badRequest().build(); // 400 에러

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 에러


    }

    //
    @GetMapping("/manager/vacation/remain/{employee_id}")
    public ResponseEntity<Integer> getRemainOfVacationOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        if(authCheckApi()) {
            if(validationId(employeeId)){
                int setting = manService2.getDefaultSettingValue(employeeId);
                log.info("남은 연차 수 : {}",setting);

                return ResponseEntity.ok(setting); // 200 OK
            }
            log.info("id validation fail");
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
        log.info("권한 에러 발생 (403)");
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Error


        //  1. 기본 설정 데이터 값 들고옴 - 입사 년도가 올해 이면 freshman, 입사 년도가 올해보다 이전이면 senior 값을 들고온다
        //    1.1 조정 데이터 있으면
        //      1.1.1 기본 값에 반영 (+,-)
        //    1.1 조정 데이터 없으면
        //      1.1.2 X
        //  2. 1번 데이터에서 전체 연차 이력에서 승인 개수를 제외함


    }



}

