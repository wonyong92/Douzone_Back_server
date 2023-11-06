package com.example.bootproject.controller.rest.manager;

import com.example.bootproject.service.service2.ManagerService2;
import com.example.bootproject.vo.vo2.request.DefaultVacationRequestDto;
import com.example.bootproject.vo.vo2.request.PagingRequestDto;
import com.example.bootproject.vo.vo2.request.PagingRequestWithDateDto;
import com.example.bootproject.vo.vo2.request.PagingRequestWithIdStatusDto;
import com.example.bootproject.vo.vo2.response.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
            return (currentPage>0?currentPage:1);// 0보다 크면 받아온 데이터 반환, 그렇지 않으면 1 반환
        }catch (NumberFormatException e){
            return 1; // 예외 발생시 1 반환
        }
    }

    /* 정렬할 컬럼이 영문자와 '_'로 이루어 져있는지 확인 후 해당 값 반환, 조건 만족하지 않으면 비어있는 값 반환 */
    public String validationSort(String getSort){
        return (getSort.matches("^[a-zA-Z_]+$")?getSort:"''");
    }


    /* 정렬 방식이 desc 인지 확인 후 정렬 방식 반환, 조건 만족하지 않으면 비어있는 값 반환 */
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

    /* 신청 결과가 승인 혹은 반려가 들어왔으면 해당 값 리턴하고, 이외의 값은 빈값 리턴*/
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
        if (authCheckApi()) { //권한 확인
            if ( validationDate(year, month, day)) { //쿼리 파라미터로 받아온 날짜에 대한 validation 체크
                int currentPage = validationPageNum(getPageNum); //페이지 번호에 대한 validation 체크
                String sort = validationSort(getSort); // 페이지 정렬 기준 컬럼에 대한 validation 체크
                String sortOrder = validationDesc(getSortOrder); // 정렬 방식에 대한 validation 체크
                log.info("getRequestVacationInformationOfAll의 sort, sortOrder : {} {}",sort,sortOrder);
                String date = String.format("%04d-%02d-%02d", year, month, day); // year-month-day 형태의 문자열로 변환

                PagingRequestWithDateDto pagingRequestWithDateDto = new PagingRequestWithDateDto(currentPage, sort, sortOrder, date);

                Page<List<VacationRequestDto>> result = manService2.getAllVacationHistory(pagingRequestWithDateDto); // 전체 사원 정보 반환
                log.info("getAllVacationHistory result : {}", result);
                if (result.getData().isEmpty()) { // 반환된 데이터가 비어있을 때
                    return ResponseEntity.noContent().build(); // 204 No Content
                }
                return ResponseEntity.ok(result); // 200 OK
            }
            //로그
            log.info("Validation failed for year, month, day: {},{},{}", year, month, day);
            return ResponseEntity.badRequest().build(); //400 Bad Request
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 ERROR



        /*
         * 근태 담당자 권한 확인
         * 1. 권한 확인 성공시
         * 날짜 데이터 validation 체크
         *      - 체크 성공 시
         *        -- 페이지 번호, 페이지 정렬 기준 컬럼, 정렬 방식에 대한 validation 체크
         *        -- 날짜 데이터를 year-month-day 형태의 문자열로 변환
         *        -- 전체 사원에 대한 정보를 받아옴
         *        -- 반환된 데이터가 비어있으면
         *          --- 204 No Content
         *        -- 정상적으로 반환된 데이터가 존재하면
         *          --- 200 OK
         *      - 체크 실패 시
         *        -- 400 Bad Request 반환
         * 2. 권한 확인 실패 시
         * 403 에러 발생
         * */
    }

    // 타 사원의 연차 이력 조회 메서드
    /* TODO : 추후 권한 확인 추가 */
    @GetMapping("/manager/vacation/history/{employee_id}")
    public ResponseEntity <Page<List<VacationRequestDto>>> getHistoryVacationOfEmployee(@PathVariable(name = "employee_id") String id,@RequestParam(name = "page", defaultValue = "1") String getPageNum, @RequestParam(name="sort", defaultValue = "") String getSort, @RequestParam(name="sortOrder", defaultValue = "") String getSortOrder,@RequestParam(name="status",defaultValue = "") String getStatus) {
        if(authCheckApi()){ //권한 확인
            if(validationId(id)){ //id에 대한 validation 체크
                int currentPage = validationPageNum(getPageNum); //페이지 번호에 대한 validation 체크
                String sort = validationSort(getSort); // 페이지 정렬 기준 컬럼에 대한 validation 체크
                String sortOrder = validationDesc(getSortOrder); // 정렬 방식에 대한 validation 체크
                String status = validationStatus(getStatus); // 신청 결과에 대한 validation 체크
                PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto = new PagingRequestWithIdStatusDto(currentPage,sort,sortOrder,id,status);

                // 타 사원의 연차 이력 데이터를 반환 받음
                Page<List<VacationRequestDto>> result = manService2.getHistoryVacationOfEmployee(pagingRequestWithIdStatusDto);
                log.info("getHistoryOfUsedVacationOfEmployee result : {}", result);
                if (result.getData().isEmpty()) { //받아온 데이터가 비어있으면
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
         * 1. 권한 확인 성공시
         * 쿼리 파라미터로 받아온 ID에 대한 validation 체크
         *      - 체크 성공 시
         *        -- 페이지 번호, 페이지 정렬 기준 컬럼, 정렬 방식에 대한 validation 체크
         *        -- 타 사원의 연차 이력 데이터를 반환 받음
         *        -- 반환된 데이터가 비어있으면
         *          --- 204 No Content
         *        -- 정상적으로 반환된 데이터가 존재하면
         *          --- 200 OK
         *      - 체크 실패 시
         *        -- 400 Bad Request 반환
         * 2. 권한 확인 실패 시
         * 403 에러 발생
         * */
    }

    // 정규 출/퇴근 시간 설정 내역 확인 메서드
    /*TODO : 추후 권한 확인 추가*/
    @GetMapping("/manager/setting_history/work_time")
    public ResponseEntity<Page<List<SettingWorkTimeDto>>> settingWorkTime(@RequestParam(name = "page",defaultValue = "1") String getPageNum, @RequestParam(name = "sort", defaultValue = "") String getSort, @RequestParam(name = "sortOrder", defaultValue = "") String getSortOrder) {
        if (authCheckApi()) { // 권한 확인
            int currentPage = validationPageNum(getPageNum);  //페이지 번호에 대한 validation 체크
            String sort = validationSort(getSort); // 페이지 정렬 기준 컬럼에 대한 validation 체크
            String sortOrder = validationDesc(getSortOrder); // 정렬 방식에 대한 validation 체크

            PagingRequestDto pagingRequestDto = new PagingRequestDto(currentPage, sort, sortOrder);

            /* 정규 출,퇴근 시간 설정 내역 데이터를 반환 받아온다 */
            Page<List<SettingWorkTimeDto>> result = manService2.getSettingWorkTime(pagingRequestDto);
            log.info("getSettingWorkTime result : {}", result);
            if (result.getData().isEmpty()) { // 반환 받은 데이터가 비어있으면
                return ResponseEntity.noContent().build(); //204 No Content
            }
            return ResponseEntity.ok(result); //200 OK

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 ERROR

        /*
         * 근태 담당자 권한 확인
         * 1. 권한 확인 성공시
         * 페이지 번호, 페이지 정렬 기준 컬럼, 정렬 방식에 대한 validation 체크
         * 정규 출,퇴근 시간 설정 내역 데이터를 반환 받아온다
         *  - 반환된 데이터가 비어있으면
         *      -- 204 No Content
         *  - 정상적으로 반환된 데이터가 존재하면
         *      -- 200 OK
         * 2. 권한 확인 실패 시
         * 403 에러 발생
         * */
    }


    //근속 연수에 따른 기본 부여 연차 개수 설정 내역 확인 메서드
    /*TODO : 추후 권한 확인 추가*/
    @GetMapping("/manager/vacation/setting_history/vacation_default")
    public ResponseEntity<Page<List<VacationQuantitySettingDto>>> getHistoryOfvacationDefaultSetting(@RequestParam(name = "page",defaultValue = "1") String getPageNum, @RequestParam(name = "sort", defaultValue = "") String getSort, @RequestParam(name = "sortOrder", defaultValue = "") String getSortOrder) {
        if (authCheckApi()) { // 권한 확인
            int currentPage = validationPageNum(getPageNum); //페이지 번호에 대한 validation 체크
            String sort = validationSort(getSort); // 페이지 정렬 기준 컬럼에 대한 validation 체크
            String sortOrder = validationDesc(getSortOrder); // 정렬 방식에 대한 validation 체크

            PagingRequestDto pagingRequestDto = new PagingRequestDto(currentPage, sort, sortOrder);

            /* 근속 연수에 따른 기본 부여 연차 개수 설정 내역 데이터 반환 받음 */
            Page<List<VacationQuantitySettingDto>> result = manService2.getVacationSettingHistory(pagingRequestDto);
            log.info("getVacationSettingHistory result : {}", result);
            if (result.getData().isEmpty()) { // 반환 데이터가 비어있으면
                return ResponseEntity.noContent().build(); //204 No Content
            }
            return ResponseEntity.ok(result); //200 OK

        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 ERROR

        /*
         * 근태 담당자 권한 확인
         * 1. 권한 확인 성공시
         * 페이지 번호, 페이지 정렬 기준 컬럼, 정렬 방식에 대한 validation 체크
         * 근속 연수에 따른 기본 부여 연차 개수 설정 내역 데이터 반환 받음
         *  - 반환된 데이터가 비어있으면
         *      -- 204 No Content
         *  - 정상적으로 반환된 데이터가 존재하면
         *      -- 200 OK
         * 2. 권한 확인 실패 시
         * 403 에러 발생
         * */
    }


    // 근속 연수에 따른 연차 개수 설정
    @PostMapping("/manager/setting/vacation_default")
    public ResponseEntity<DefaultVacationResponseDto> setDefaultVacation(@ModelAttribute DefaultVacationRequestDto requestDto) {
        // form 데이터를 DefaultVacationRequestDto 형으로 받아온다
        log.info("requestDto.getEmployeeId() : {}",requestDto.getEmployeeId());

        if(authCheckApi()){ //권한 확인
            /* requestDto의 항목에 대한 validation check - 값 오류 혹은 비워있는지 확인*/
            if(validationFreshman(requestDto.getFreshman())&&validationSenior(requestDto.getSenior())&&validationTargetDate(requestDto.getTargetDate())){

                String id="1234"; //세션에서 가져온 ID
                requestDto.setEmployeeId(id);

                // 연차 개수 설정 후, 설정 된 데이터를 받아온다
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
        /*
         * 근태 담당자 권한 확인
         * 1. 권한 확인 성공 시
         * form으로 받아온 1년 미만 연차 개수, 1년 이상 연차 개수, 적용 날짜에 대한 validation 체크
         *  - 체크 성공 시
         *      requestDto의 id 값을 세션에서 받아와 세팅
         *      연차 개수 설정 후, 설정 된 데이터를 받아온다
         *      200 OK 반환
         *  - 체크 실패 시
         *      400 에러 발생
         * 2. 권한 확인 실패 시
         * 403 에러

         */




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

        /*
        * 근태 담당자 여부 권한 확인
        * 1. 권한 확인 성공 시
        *   기본 설정 데이터 값 들고옴
        *     - 입사 년도가 올해 이면 freshman, 입사 년도가 올해보다 이전이면 senior 값을 들고온다
        *       -- 조정 데이터 있으면
        *           --- 기본 값에 반영 (+,-)
        *       -- 조정 데이터 없으면
        *           --- X
        *   1번 데이터에서 전체 연차 이력에서 승인 개수를 제외함
        * 2. 권한 확인 실패 시
        * 403 에러
        * */



    }



}

