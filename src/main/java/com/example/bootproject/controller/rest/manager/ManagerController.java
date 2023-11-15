package com.example.bootproject.controller.rest.manager;


import com.example.bootproject.entity.Employee;
import com.example.bootproject.service.service1.EmployeeService;
import com.example.bootproject.service.service1.ManagerService1;
import com.example.bootproject.service.service3.api.AppealService;
import com.example.bootproject.service.service3.api.VacationService;
import com.example.bootproject.system.validator.PageRequestValidator;
import com.example.bootproject.system.validator.PagedLocalDateDtoValidator;
import com.example.bootproject.vo.vo1.request.PageRequest;
import com.example.bootproject.vo.vo1.request.PagedLocalDateDto;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryRequestDto;
import com.example.bootproject.vo.vo1.response.AttendanceAppealMediateResponseDto;
import com.example.bootproject.vo.vo1.response.AttendanceApprovalUpdateResponseDto;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import com.example.bootproject.vo.vo1.response.RegularTimeAdjustmentHistoryResponseDto;
import com.example.bootproject.vo.vo1.request.DefaultVacationRequestDto;
import com.example.bootproject.vo.vo1.request.PagingRequestDto;
import com.example.bootproject.vo.vo1.request.PagingRequestWithDateDto;
import com.example.bootproject.vo.vo1.request.PagingRequestWithIdStatusDto;
import com.example.bootproject.vo.vo1.response.DefaultVacationResponseDto;
import com.example.bootproject.vo.vo1.response.SettingWorkTimeDto;
import com.example.bootproject.vo.vo1.response.VacationQuantitySettingDto;
import com.example.bootproject.vo.vo1.response.VacationRequestDto;
import com.example.bootproject.vo.vo1.request.appeal.AppealProcessRequestDto;
import com.example.bootproject.vo.vo1.request.vacation.VacationAdjustRequestDto;
import com.example.bootproject.vo.vo1.request.vacation.VacationProcessRequestDto;
import com.example.bootproject.vo.vo1.response.Page;
import com.example.bootproject.vo.vo1.response.appeal.AppealRequestResponseDto;
import com.example.bootproject.vo.vo1.response.employee.EmployeeResponseDto;
import com.example.bootproject.vo.vo1.response.vacation.VacationAdjustResponseDto;
import com.example.bootproject.vo.vo1.response.vacation.VacationRequestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.bootproject.system.StaticString.VACATION_REQUEST_STATE_REJECTED;
import static com.example.bootproject.system.util.ValidationChecker.*;


@RestController

@Slf4j
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService1 managerService1;

    private final EmployeeService employeeService;


    private final VacationService vacationService;

    private final AppealService appealService;

//    private final EmployeeService employeeService;

    private final ManagerService1 managerService;

    private final PageRequestValidator pageRequestValidator;
    private final PagedLocalDateDtoValidator pagedLocalDateDtoValidator;

    @InitBinder("pageRequest")
    protected void pageRequestMessageBinder(WebDataBinder binder) {
        binder.addValidators(pageRequestValidator);
//        binder.addValidators(pagedLocalDateDtoValidator);
    }

    @InitBinder("pagedLocalDateDto")
    protected void pagedLocalDateDtoMessageBinder(WebDataBinder binder) {
//        binder.addValidators(pageRequestValidator);
        binder.addValidators(pagedLocalDateDtoValidator);
    }

    public boolean validationId(String id) { // Id Validation 체크
        int idCheck = managerService1.getEmployeeCheck(id); //id값이 실제로 테이블에 존재하면 1 반환
        /* employeeId가 숫자로 구성 되어 있고,  실제로 테이블에 존재하는지 확인 */
        return id.matches("^[0-9]+$") && idCheck == 1;
    }


    //정규출퇴근시간 설정
    @PostMapping("/manager/adjustment")
    public ResponseEntity<RegularTimeAdjustmentHistoryResponseDto> setWorkTime(@ModelAttribute RegularTimeAdjustmentHistoryRequestDto dto, HttpServletRequest req) {

        log.info("RegularTimeAdjustmentHistoryDto dto{}:", dto);
        if (!isManager(req)) {
            // 권한이 없으면 403 금지 응답을 반환합니다.
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        log.info("요청 내용{}", dto);

        String employeeId = getLoginIdOrNull(req); // 이는 예시입니다. 실제 구현에서는 세션 또는 요청에서 직원 ID를 받아야 합니다.

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
    @GetMapping("/manager/approve/{employeeId}")
    public ResponseEntity<Page<List<AttendanceApprovalUpdateResponseDto>>> getHistoryOfApproveOfEmployee(@PathVariable("employeeId") String employeeId, @Valid PageRequest pageRequest, BindingResult br, HttpServletRequest req) {

        // 'employeeId'가 세션 또는 보안 컨텍스트에서 검색된 것으로 가정합니다.

        if (!isManager(req)) {
            // 권한이 없으면 403 금지 응답을 반환합니다.
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (br.hasErrors()) {
            AttendanceApprovalUpdateResponseDto body = new AttendanceApprovalUpdateResponseDto();
            body.setMessage(br.getAllErrors().toString());
            log.info("Validation rule violated" + br.getAllErrors());
            if (br.hasFieldErrors("page")) {
                log.info("잘못된 페이지 번호 요청, 기본값인 1로 초기화 수행");
                pageRequest.setPage(1);
            }
            if (br.hasFieldErrors("sort")) {
                log.info("잘못된 정렬 대상 컬럼 이름 요청, 기본값인 1로 초기화 수행");
                pageRequest.setSort("''");
            }
            if (br.hasFieldErrors("desc")) {
                log.info("잘못된 정렬 방식 요청, 기본값인 1로 초기화 수행");
                pageRequest.setDesc("desc");
            }
        }

        // 예외를 던지지 않고 작업을 계속 진행합니다.
        Page<List<AttendanceApprovalUpdateResponseDto>> approvalPage = managerService1.managerGetApprovalInfoByEmployeeId(employeeId, pageRequest.getPage(), pageRequest.getSort(), pageRequest.getDesc());

        // 결과를 반환합니다.
        return ResponseEntity.ok(approvalPage);
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
    @GetMapping("/manager/attendance_info/{employeeId}")
    public ResponseEntity<Page<List<AttendanceInfoResponseDto>>> getAttendanceInfoOfEmployeeByDay(@PathVariable("employeeId") String employeeId, PagedLocalDateDto pagedLocalDateDto, HttpServletRequest req) {

        if (isManager(req)) {

            // 근태 정보 조회
            Page<List<AttendanceInfoResponseDto>> attendanceInfo;
            if (pagedLocalDateDto.getDay() != null) {
                log.info("일단위 조회");
                // 날짜를 기준으로 근태 정보 조회
                LocalDate attendanceDate = pagedLocalDateDto.makeLocalDate();
                attendanceInfo = employeeService.getAttendanceByDateAndEmployee(attendanceDate, employeeId, pagedLocalDateDto.getSort(), pagedLocalDateDto.getDesc(), pagedLocalDateDto.getPage());
            } else {
                // 월을 기준으로 근태 정보 조회
                log.info("월단위 조회");
                attendanceInfo = employeeService.getAttendanceByMonthAndEmployee(pagedLocalDateDto.getYear(), pagedLocalDateDto.getMonth(), employeeId, pagedLocalDateDto.getPage(), pagedLocalDateDto.getSort(), pagedLocalDateDto.getDesc());
            }

            // 조회된 근태 정보가 없을 경우
            if (attendanceInfo == null) {
                log.info("해당 사원의 근태 기록이 없습니다: {}", employeeId);
                return ResponseEntity.noContent().build();
            }

            // 근태 정보 반환
            return ResponseEntity.ok(attendanceInfo);
        }
        log.info("manager가 아닌 유저의 요청 발생 {}", req.getRequestURL());
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @GetMapping("/manager/appeal/requests/{employeeId}")
    public ResponseEntity<Page<List<AttendanceAppealMediateResponseDto>>> getAppealHistoryOfEmployee(@PathVariable("employeeId") String employeeId, PageRequest pageRequest, HttpServletRequest req) {
        if (!isManager(req)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // 예외를 던지지 않고 연산을 계속 진행합니다.
        Page<List<AttendanceAppealMediateResponseDto>> appealPage = managerService1.managerfindAttendanceInfoByMine(employeeId, pageRequest.getPage(), pageRequest.getSort(), pageRequest.getDesc());

        // 결과를 반환합니다.
        return ResponseEntity.ok(appealPage);
    }
    //전체 연차 요청 정보 조회 메서드
    /*TODO : 추후 권한 확인 추가*/

    @GetMapping("/manager/vacation/requests")
    public ResponseEntity<Page<List<VacationRequestDto>>> getRequestVacationInformationOfAll(@Valid PagedLocalDateDto pagedLocalDateDto, BindingResult br, HttpServletRequest req) {

        if (br.hasErrors()) {
            log.info("Validation rule violated" + br.getAllErrors());
            if (br.hasFieldErrors("page")) {
                log.info("잘못된 페이지 번호 요청, 기본값인 1로 초기화 수행");
                pagedLocalDateDto.setPage(1);
            }
            if (br.hasFieldErrors("sort")) {
                log.info("잘못된 정렬 대상 컬럼 이름 요청, 기본값인 1로 초기화 수행");
                pagedLocalDateDto.setSort("''");
            }
            if (br.hasFieldErrors("desc")) {
                log.info("잘못된 정렬 방식 요청, 기본값인 1로 초기화 수행");
                pagedLocalDateDto.setDesc("desc");
            }
        }
        if (isManager(req)) { //권한 확인
            String date = pagedLocalDateDto.makeLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // year-month-day 형태의 문자열로 변환

            PagingRequestWithDateDto pagingRequestWithDateDto = new PagingRequestWithDateDto(pagedLocalDateDto.getPage(), pagedLocalDateDto.getSort(), pagedLocalDateDto.getDesc(), date);

            Page<List<VacationRequestDto>> result = managerService1.getAllVacationHistory(pagingRequestWithDateDto); // 전체 사원 정보 반환
            log.info("getAllVacationHistory result : {}", result);
            if (result.getData().isEmpty()) { // 반환된 데이터가 비어있을 때
                log.info("데이터가 하나도 없습니다");
//                return ResponseEntity.noContent().build(); // 204 No Content
            }
            return ResponseEntity.ok(result); // 200 OK
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
    @GetMapping("/manager/vacation/history/{employee_id}")
    public ResponseEntity<Page<List<VacationRequestDto>>> getHistoryVacationOfEmployee(@PathVariable(name = "employee_id") String id, @RequestParam(name = "page", defaultValue = "1") String getPageNum, @RequestParam(name = "sort", defaultValue = "") String getSort, @RequestParam(name = "sortOrder", defaultValue = "") String getSortOrder, @RequestParam(name = "status", defaultValue = "") String getStatus, HttpServletRequest req) {
        if (isManager(req)) { //권한 확인
            if (validationId(id)) { //id에 대한 validation 체크
                int currentPage = validationPageNum(getPageNum); //페이지 번호에 대한 validation 체크
                String sort = validationSort(getSort); // 페이지 정렬 기준 컬럼에 대한 validation 체크
                String sortOrder = validationDesc(getSortOrder); // 정렬 방식에 대한 validation 체크
                String status = validateVacationRequestResultStatus(getStatus); // 신청 결과에 대한 validation 체크
                PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto = new PagingRequestWithIdStatusDto(currentPage, sort, sortOrder, id, status);

                // 타 사원의 연차 이력 데이터를 반환 받음
                Page<List<VacationRequestDto>> result = managerService1.getHistoryVacationOfEmployee(pagingRequestWithIdStatusDto);
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
    public ResponseEntity<Page<List<SettingWorkTimeDto>>> settingWorkTime(@Valid PageRequest pageRequest, BindingResult br, HttpServletRequest req) {
        if (br.hasErrors()) {
            AttendanceApprovalUpdateResponseDto body = new AttendanceApprovalUpdateResponseDto();
            body.setMessage(br.getAllErrors().toString());
            log.info("Validation rule violated" + br.getAllErrors());
            if (br.hasFieldErrors("page")) {
                log.info("잘못된 페이지 번호 요청, 기본값인 1로 초기화 수행");
                pageRequest.setPage(1);
            }
            if (br.hasFieldErrors("sort")) {
                log.info("잘못된 정렬 대상 컬럼 이름 요청, 기본값인 1로 초기화 수행");
                pageRequest.setSort("''");
            }
            if (br.hasFieldErrors("desc")) {
                log.info("잘못된 정렬 방식 요청, 기본값인 1로 초기화 수행");
                pageRequest.setDesc("desc");
            }
        }
        if (isManager(req)) { // 권한 확인

            PagingRequestDto pagingRequestDto = new PagingRequestDto(pageRequest.getPage(), pageRequest.getSort(), pageRequest.getDesc());

            /* 정규 출,퇴근 시간 설정 내역 데이터를 반환 받아온다 */
            Page<List<SettingWorkTimeDto>> result = managerService1.getSettingWorkTime(pagingRequestDto);
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
    public ResponseEntity<Page<List<VacationQuantitySettingDto>>> getHistoryOfvacationDefaultSetting(@Valid PageRequest pageRequest, BindingResult br, HttpServletRequest req) {
        if (br.hasErrors()) {
            AttendanceApprovalUpdateResponseDto body = new AttendanceApprovalUpdateResponseDto();
            body.setMessage(br.getAllErrors().toString());
            log.info("Validation rule violated" + br.getAllErrors());
            if (br.hasFieldErrors("page")) {
                log.info("잘못된 페이지 번호 요청, 기본값인 1로 초기화 수행");
                pageRequest.setPage(1);
            }
            if (br.hasFieldErrors("sort")) {
                log.info("잘못된 정렬 대상 컬럼 이름 요청, 기본값인 1로 초기화 수행");
                pageRequest.setSort("''");
            }
            if (br.hasFieldErrors("desc")) {
                log.info("잘못된 정렬 방식 요청, 기본값인 1로 초기화 수행");
                pageRequest.setDesc("desc");
            }
        }

        if (isManager(req)) { // 권한 확인

            PagingRequestDto pagingRequestDto = new PagingRequestDto(pageRequest.getPage(), pageRequest.getSort(), pageRequest.getDesc());

            /* 근속 연수에 따른 기본 부여 연차 개수 설정 내역 데이터 반환 받음 */
            Page<List<VacationQuantitySettingDto>> result = managerService1.getVacationSettingHistory(pagingRequestDto);
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
    public ResponseEntity<DefaultVacationResponseDto> setDefaultVacation(@ModelAttribute DefaultVacationRequestDto requestDto, HttpServletRequest req) {
        // form 데이터를 DefaultVacationRequestDto 형으로 받아온다
        log.info("requestDto.getEmployeeId() : {}", requestDto.getEmployeeId());

        if (isManager(req)) { //권한 확인
            /* requestDto의 항목에 대한 validation check - 값 오류 혹은 비워있는지 확인*/
            PagedLocalDateDto dto = new PagedLocalDateDto();
            dto.fromLocalDate(requestDto.getTargetDate());
            if (isEmployeeFreshman(requestDto.getFreshman()) && isEmployeeSenior(requestDto.getSenior()) && validateDateIsRealDate(dto) && validateDateIsRightDateFormat(requestDto.getTargetDate())) {

                String id = getLoginIdOrNull(req); //세션에서 가져온 ID
                requestDto.setEmployeeId(id);

                // 연차 개수 설정 후, 설정 된 데이터를 받아온다
                DefaultVacationResponseDto defaultVacationResponseDto = managerService1.makeDefaultVacationResponse(requestDto);
                log.info("managerService1.makeDefaultVacationResponse(requestDto,id)의 결과 : {}", defaultVacationResponseDto);

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
    public ResponseEntity<Integer> getRemainOfVacationOfEmployee(@PathVariable(name = "employee_id") String employeeId, HttpServletRequest req) {
        if (isManager(req)) {
            if (validationId(employeeId)) {
                int setting = managerService1.getDefaultSettingValue(employeeId);
                log.info("남은 연차 수 : {}", setting);

                return ResponseEntity.ok(setting); // 200 OK
            }
            log.info("id validation fail");
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

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
    //manager 2 end

    //manager 3

    @GetMapping("/manager/employees")
    public ResponseEntity<Page<List<EmployeeResponseDto>>> getEmployeeList(HttpServletRequest req, @Valid PageRequest pageRequest, BindingResult br) {
        if (br.hasErrors()) {
            log.info("Validation rule violated" + br.getAllErrors());
            if (br.hasFieldErrors("page")) {
                log.info("잘못된 페이지 번호 요청, 기본값인 1로 초기화 수행");
                pageRequest.setPage(1);
            } else if (br.hasFieldErrors("sort")) {
                log.info("잘못된 정렬 대상 컬럼 이름 요청, 기본값인 1로 초기화 수행");
                pageRequest.setSort("''");
            } else if (br.hasFieldErrors("desc")) {
                log.info("잘못된 정렬 방식 요청, 기본값인 1로 초기화 수행");
                pageRequest.setDesc("desc");
            }

        }
        if (isManager(req)) {
            int currentPage = pageRequest.getPage();
            String sortColumn = pageRequest.getSort();
            String descCheck = pageRequest.getDesc();
            log.info("get Employee list - validation result : {} {} {}", currentPage, sortColumn, descCheck);

            Page<List<EmployeeResponseDto>> result = managerService.getEmployeeList(currentPage, sortColumn, descCheck);
            log.info("getEmpInfo result : {}", result);
            if (result.getData().isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(result); // 200 OK
            }
        }
        log.info("manager 가 아닌 유저의 요청");
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 ERROR
    }

    @PostMapping("/manager/vacation/process")
    public ResponseEntity<VacationRequestResponseDto> processVacationRequest(@ModelAttribute VacationProcessRequestDto dto, HttpServletRequest req) {
        if (isManager(req)) {
            String status = dto.getVacationRequestStateCategoryKey();
            if (status.equals(VACATION_REQUEST_STATE_REJECTED)) {
                if (dto.getReasonForRejection().equals(null) || dto.getReasonForRejection().trim().equals("")) {
                    //거절 상태에 대해 사유가 누락된 경우
                    log.info("거절 사유가 누락됨");
                    return ResponseEntity.badRequest().build();
                }
            }
            dto.setEmployeeId((String) req.getSession().getAttribute("loginId"));
            VacationRequestResponseDto result = vacationService.processVacationRequest(dto);
            if (result == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
        } else {

            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/manager/vacation/modify/{employee_id}")
    public ResponseEntity<VacationAdjustResponseDto> modifyVacationQuantityOfEmployee(@PathVariable(name = "employee_id") String employeeId, @ModelAttribute VacationAdjustRequestDto dto, HttpServletRequest req) {
//        - 컨트롤러
//                - form - post로 데이터 전달
//        - vacationAdjustRequestDto
//                - 저장 대상 employee_id(관리자 자신의 아이디와 일치하면 안된다)
//        - adjust_quantity
//                - reason
//                - adjust_type
//                - 로그인 유무, manager 유무 확인 및 현재 로그인된 아이디와 동일한 타겟 employee_id 인지 확인
//                - forbidden 응답
//                - adjust_quantity가 적절한 범위를 벗어나는 경우, reason이 누락된 경우, 연차 종류가 이상한 데이터가 들어오는 경우(상수 리스트에서 검색?, DB에서 직접 검색?)
//                - badRequest 응답
//                - 서비스로 dto 전달
//        - 서비스
//                - 분기점 1 : 타겟 employee_id로 사원을 찾을 수 없는 경우
//                - badRequest 응답을 위한 응답
//                - 분기점 1 : 저장 대상 사원을 찾은 경우
//                - vacation_adjusted_history에 데이터 insert
//        - generated key를 이용하여 재조회 후 vacationAdjustResponseDto에 담아서 응답 전달
        String loginEmployeeId = getLoginIdOrNull(req);
        if (loginEmployeeId == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (isManager(req) && !loginEmployeeId.equals(employeeId)) {
            try {
                if (dto.getReason() == null || dto.getReason().trim().equals("")) {
                    return ResponseEntity.badRequest().build();
                }
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
            VacationAdjustResponseDto result = vacationService.modifyVacationOfEmployee(dto, employeeId);
            return ResponseEntity.ok(result);
        } else {
            if (loginEmployeeId.equals(employeeId)) {
                log.info("자신의 연차 정보에 접근 발생");
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/manager/appeal/process") //39
    public ResponseEntity<AppealRequestResponseDto> processAppealRequest(@ModelAttribute AppealProcessRequestDto dto, HttpServletRequest req) {
        if (isManager(req)) {
            String status = dto.getStatus();
            if (status.equals(VACATION_REQUEST_STATE_REJECTED)) {
                if (dto.getReasonForRejection().equals(null) || dto.getReasonForRejection().trim().equals("")) {
                    //거절 상태에 대해 사유가 누락된 경우
                    log.info("거절 사유가 누락됨");
                    return ResponseEntity.badRequest().build();
                }
            }
            dto.setEmployeeId((String) req.getSession().getAttribute("loginId"));
            AppealRequestResponseDto result = appealService.processAppealRequest(dto);
            if (result == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
        } else {

            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    //TODO : 매니저 권한 확인 로직 작성 필요


    @GetMapping("/manager/information/{employee_id}")
    public ResponseEntity<Employee> getInformationOfEmployee(@PathVariable(name = "employee_id") String employeeId, HttpServletRequest req) {
        if (isManager(req)) {
            Employee result = employeeService.findEmployeeInfoById(employeeId);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
            log.info("조회 결과 없음 혹은 실패");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }


        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

    @GetMapping("/manager/vacation/all/requested")
    public ResponseEntity<List<VacationRequestResponseDto>> getAllRequestedInformationOfVacation(@ModelAttribute PagedLocalDateDto pagedLocalDateDto) {
        return ResponseEntity.ok(employeeService.getAllRequestedInformationOfVacation(pagedLocalDateDto));
    }
}