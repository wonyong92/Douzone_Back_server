package com.example.bootproject.controller.rest.employee;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.service.service1.EmployeeService;
import com.example.bootproject.service.service3.api.AppealService;
import com.example.bootproject.service.service3.api.LoginService;
import com.example.bootproject.service.service3.api.LogoutService;
import com.example.bootproject.service.service3.api.VacationService;
import com.example.bootproject.system.util.IpAnalyzer;
import com.example.bootproject.system.validator.PageRequestValidator;
import com.example.bootproject.system.validator.PagedLocalDateDtoValidator;
import com.example.bootproject.vo.vo1.request.*;
import com.example.bootproject.vo.vo1.request.appeal.AppealRequestDto;
import com.example.bootproject.vo.vo1.request.employee.EmployeeInformationUpdateDto;
import com.example.bootproject.vo.vo1.response.*;
import com.example.bootproject.vo.vo1.response.appeal.AppealRequestResponseDto;
import com.example.bootproject.vo.vo1.response.employee.EmployeeResponseDto;
import com.example.bootproject.vo.vo1.response.login.LoginResponseDto;
import com.example.bootproject.vo.vo1.response.logout.LogoutResponseDto;
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
import java.util.List;

import static com.example.bootproject.system.StaticString.SESSION_ID_NOT_MATCHED_LOGIN_REQUEST;
import static com.example.bootproject.system.util.ValidationChecker.*;


@RestController
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final LoginService loginService;
    private final LogoutService logoutService;
    private final VacationService vacationService;
    private final AppealService appealService;


    /*
      - 사원 ID의 유효성을 검증한다 처리중 null이 반환되면 BadRequest 응답을 반환한다
      - 요청된 DTO가 null이거나 처리 중 null이 반환되면 BadRequest 응답을 반환한다.
      - 사원 ID가 데이터베이스에 없다면 BadRequest 응답을 반환한다.
      - 사원 ID 유효성 검사를 통과하고 출근 정보가 성공적으로 생성되면, OK 응답과 함께 출근 정보 DTO를 반환한다.
    */
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


    @PostMapping("/employee/attendance")
    public ResponseEntity<AttendanceInfoResponseDto> makeAttendanceInfo(@Valid AttendanceInfoStartRequestDto requestDto, BindingResult br, HttpServletRequest req) {
        /*TODO : 인터셉터로 뽑아내기 -> 뽑아내면 employeeId를 다음 로직으로 어떻게 전달해 줄껀데?*/
        String employeeId = getLoginIdOrNull(req);
        if (employeeId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (br.hasErrors()) {
            AttendanceInfoResponseDto body = new AttendanceInfoResponseDto();
            body.setMessage("Validation rule violated" + br.getAllErrors());
            return ResponseEntity.badRequest().body(body);
        }

        requestDto.setEmployeeId(employeeId);
        AttendanceInfoResponseDto responseDto = employeeService.makeStartRequest(requestDto);
        if (responseDto.getMessage() != null) {
            // 오류 메시지가 있는 경우, 클라이언트에게 메시지를 포함하여 응답
            log.info("error msg : {}", responseDto.getMessage());
            return ResponseEntity.badRequest().body(responseDto);
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

    //톼근처리
    @PostMapping("/employee/leave")
    public ResponseEntity<AttendanceInfoResponseDto> makeLeaveInformation(AttendanceInfoEndRequestDto requestDto, HttpServletRequest req) {


        String employeeId = getLoginIdOrNull(req);
        if (employeeId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        AttendanceInfoResponseDto responseDto = employeeService.makeEndRequest(requestDto, employeeId);

        if (responseDto.getMessage() != null) {
            // 오류 메시지가 있는 경우, 클라이언트에게 메시지를 포함하여 응답
            log.info("error msg : {}", responseDto.getMessage());
            return ResponseEntity.badRequest().body(responseDto);
        }


        return ResponseEntity.ok(responseDto);
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

    //년월일 자신의 근태정보조회 년월만 입력하면 년월만 입력데이터 적용되게 구현
    @GetMapping("/employee/attendance_info")
    public ResponseEntity<Page<List<AttendanceInfoResponseDto>>> getAttendanceInfoOfEmployeeByDay(@Valid PagedLocalDateDto pagedLocalDateDto, BindingResult br, HttpServletRequest req) {

        String employeeId = getLoginIdOrNull(req); // 실제 어플리케이션에서는 세션에서 가져오거나 인증 메커니즘을 사용해야 함
        if (employeeId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (br.hasErrors()) {
            AttendanceInfoResponseDto body = new AttendanceInfoResponseDto();
            body.setMessage(br.getAllErrors().toString());
            log.info("Validation rule violated" + br.getAllErrors());
            if (br.hasFieldErrors("page")) {
                log.info("잘못된 페이지 번호 요청, 기본값인 1로 초기화 수행");
                pagedLocalDateDto.setPage(1);
            } else if (br.hasFieldErrors("sort")) {
                log.info("잘못된 정렬 대상 컬럼 이름 요청, 기본값인 1로 초기화 수행");
                pagedLocalDateDto.setSort("''");
            } else if (br.hasFieldErrors("desc")) {
                log.info("잘못된 정렬 방식 요청, 기본값인 1로 초기화 수행");
                pagedLocalDateDto.setDesc("desc");
            }

        }


        // 입력된 날짜가 유효한지 검증

        if (!validateDateIsRealDate(pagedLocalDateDto)) {
            log.info("유효하지 않은 날짜: {}}", pagedLocalDateDto.toString());
            return ResponseEntity.badRequest().build();
        }

        // 근태 정보 조회
        Page<List<AttendanceInfoResponseDto>> attendanceInfo;
        if (pagedLocalDateDto.getDay() != null) {
            // 날짜를 기준으로 근태 정보 조회
            LocalDate attendanceDate = pagedLocalDateDto.makeLocalDate();
            /*TODO : pageRequest로 리팩토링*/
            attendanceInfo = employeeService.getAttendanceByDateAndEmployee(attendanceDate, employeeId, pagedLocalDateDto.getSort(), pagedLocalDateDto.getDesc(), pagedLocalDateDto.getPage(), pagedLocalDateDto.getSize());
        } else {
            // 월을 기준으로 근태 정보 조회
            attendanceInfo = employeeService.getAttendanceByMonthAndEmployee(pagedLocalDateDto.getYear(), pagedLocalDateDto.getMonth(), employeeId, pagedLocalDateDto.getPage(), pagedLocalDateDto.getSort(), pagedLocalDateDto.getDesc(), pagedLocalDateDto.getSize());
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
  - 쿼리 파라미터로 attendanceInfoId를 받아온다.
  - 세션 또는 인증 메커니즘으로부터 employeeId를 가져온다.
  - 만약 사원 ID나 근태 정보 ID가 전달되지 않으면 오류 코드를 반환한다.
  - 데이터가 전달되면 로그에 해당 데이터를 기록한다.
  - 사원 ID가 유효한지 검사한다.
  - 모든 조건을 만족시킨 후에는 승인 처리 결과를 AttendanceApprovalResponseDto로 반환한다.
*/

    //자신의 근태 승인요청
    @PostMapping("/employee/approve/{attendanceInfoId}")
    public ResponseEntity<AttendanceApprovalResponseDto> makeApproveRequest(@PathVariable("attendanceInfoId") Long attendanceInfoId, HttpServletRequest req) {
        String employeeId = getLoginIdOrNull(req);
        if (employeeId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (attendanceInfoId == null || employeeId == null) {
            log.info("근태 정보 ID 또는 사원 ID가 누락되었습니다.");
            return ResponseEntity.badRequest().build();
        }

        AttendanceApprovalResponseDto approvalResponseDto = employeeService.approveAttendance(employeeId, attendanceInfoId);
        log.info("승인 요청 결과: {}", approvalResponseDto);
        return ResponseEntity.ok(approvalResponseDto);
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

    //자신의 근태승인내역
    @GetMapping("/employee/approve")
    public ResponseEntity<Page<List<AttendanceApprovalUpdateResponseDto>>> getHistoryOfApproveOfMine(@Valid PageRequest pageRequest, BindingResult br, HttpServletRequest req) {
        String employeeId = getLoginIdOrNull(req);
        if (employeeId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (br.hasErrors()) {
            AttendanceApprovalUpdateResponseDto body = new AttendanceApprovalUpdateResponseDto();
            body.setMessage(br.getAllErrors().toString());
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

        try {
            // 승인 내역 검색 시도
            Page<List<AttendanceApprovalUpdateResponseDto>> approvalPage = employeeService.getApprovalInfoByEmployeeId(employeeId, pageRequest.getPage(), pageRequest.getSort(), pageRequest.getDesc());

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
    - 유효성 검사에 실패할 경우 page,sort,desc 로그를 남기고 400 Bad Request 응답을 반환한다.
    - 사원 ID에 대한 근태 승인 이력을 조회한다.
    - 조회된 승인 이력이 없을 경우, 로그를 남기고 204 No Content 응답을 반환한다.
    - 조회에 성공한 경우, 승인 이력 목록을 담아 200 OK 응답과 함께 반환한다.
    */

    //자신의근태조정내역
    @GetMapping("/employee/appeal/requests")
    public ResponseEntity<Page<List<AttendanceAppealMediateResponseDto>>> getAppealHistoryOfEmployee(@Valid PageRequest pageRequest, BindingResult br, HttpServletRequest req) {
        if (br.hasErrors()) {
            AttendanceAppealMediateResponseDto body = new AttendanceAppealMediateResponseDto();
            body.setMessage(br.getAllErrors().toString());
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

        String employeeId = getLoginIdOrNull(req); // Assume this is retrieved from session or security context
        if (employeeId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        try {
            // 승인 내역 검색 시도
            Page<List<AttendanceAppealMediateResponseDto>> appealPage = employeeService.findAppealRequestOfMine(employeeId, pageRequest.getPage(), pageRequest.getSort(), pageRequest.getDesc());

            // 데이터가 없을 경우
            if (appealPage == null) {
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

    @GetMapping("/employee/search")
    public ResponseEntity<List<EmployeeSearchResponseDto>> searchEmployeeByIdOrNumber(@RequestParam String searchParameter) {

        List<EmployeeSearchResponseDto> searchResults = employeeService.searchByEmployeeIdOrName(searchParameter);

        if (searchParameter.isEmpty()) {
            log.info("검색요청데이터가 안들어왔습니다");
            return ResponseEntity.badRequest().build();
        }

        if (searchResults.isEmpty()) {
            log.info("검색결과가 존재하지않습니다");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(searchResults);


    }

    //employee 2


    // 본인의 연차 이력 조회 메서드 (승인, 반려, 전체 필터링 가능)
    /* TODO : 추후 권한 확인 추가 */
    @GetMapping("/employee/vacation/history")
    public ResponseEntity<Page<List<VacationRequestDto>>> getHistoryOfVacationOfMine(/*@RequestParam(name = "page", defaultValue = "1") String getPageNum, @RequestParam(name = "sort", defaultValue = "") String getSort, @RequestParam(name = "sortOrder", defaultValue = "") String getSortOrder*/
            @RequestParam(name = "status", defaultValue = "") String getStatus,
            @Valid PagedLocalDateDto pagedLocalDateDto, BindingResult br,
            HttpServletRequest req) {

        if (br.hasErrors()) {
            AttendanceInfoResponseDto body = new AttendanceInfoResponseDto();
            body.setMessage(br.getAllErrors().toString());
            log.info("Validation rule violated" + br.getAllErrors());
            if (br.hasFieldErrors("page")) {
                log.info("잘못된 페이지 번호 요청, 기본값인 1로 초기화 수행");
                pagedLocalDateDto.setPage(1);
            } else if (br.hasFieldErrors("sort")) {
                log.info("잘못된 정렬 대상 컬럼 이름 요청, 기본값인 1로 초기화 수행");
                pagedLocalDateDto.setSort("''");
            } else if (br.hasFieldErrors("desc")) {
                log.info("잘못된 정렬 방식 요청, 기본값인 1로 초기화 수행");
                pagedLocalDateDto.setDesc("desc");
            }

        }

        // 입력된 날짜가 유효한지 검증

        if (!validateDateIsRealDate(pagedLocalDateDto)) {
            log.info("유효하지 않은 날짜: {}}", pagedLocalDateDto.makeLocalDate().toString());
            return ResponseEntity.badRequest().build();
        }

        //status validation check 추가 필요
        //TODO : 로그인 정보를 가져오기
        String employeeId = getLoginIdOrNull(req);
        if (employeeId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (employeeId != null) {
//            int currentPage = validationPageNum(String.valueOf(pagedLocalDateDto.getPage())); //페이지 번호에 대한 validation 체크
//            String sort = validationSort(pagedLocalDateDto.getSort()); // 페이지 정렬 기준 컬럼에 대한 validation 체크
//            String sortOrder = validationDesc(pagedLocalDateDto.getDesc()); // 정렬 방식에 대한 validation 체크
            String status = validateVacationRequestResultStatus(getStatus); // 신청 결과에 대한 validation 체크

//            PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto = new PagingRequestWithIdStatusDto(pagedLocalDateDto.getPage(), pagedLocalDateDto.getSort(), pagedLocalDateDto.getDesc(), employeeId, status);

            Page<List<VacationRequestDto>> result = employeeService.getHistoryOfVacationOfMine(pagedLocalDateDto, employeeId, status); // 본인의 연차 이력 데이터 반환 받음
            log.info("getHistoryOfRejectedVacationOfMine result : {}", result);
            if (result.getData().isEmpty()) {
                return ResponseEntity.noContent().build(); //204 No Content
            }
            return ResponseEntity.ok(result); //200 OK
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 ERROR
        /*
         * 사원 권한 확인 (+로그인 확인)
         * 1. 사원 권한, 로그인 확인 성공 시
         * 쿼리 파라미터로 받아온 페이지 번호, sort할 컬럼, sort 방식, 신청 결과에 대한 validation 체크
         *   - validation 만족하지 않으면 기본 값으로 지정
         * 본인의 연차 이력 데이터 반환 받음
         *  - 정상적으로 데이터 존재시 200 OK
         *  - 정상적으로 반환되었으나 데이터가 비어있을 경우 204 No Content
         * 2. 사원 권한, 로그인 인증 실패시
         * 403 ERROR 반환
         * */
    }

    /*TODO : 권한 확인 로직 실제 구현 해야함*/
    /*TODO : Session에서 아이디 값 받아오도록 해야함*/

    // 본인의 잔여 연차 개수 확인
    @GetMapping("/employee/vacation/remain")
    public ResponseEntity<Integer> getRemainOfVacationOfMine(HttpServletRequest req) {
        String id = getLoginIdOrNull(req);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Error
        }
        int setting = employeeService.getRemainOfVacationOfMine(id); //본인의 잔여 연차 개수 반환 받음
        log.info("남은 연차 수 : {}", setting);

        return ResponseEntity.ok(setting); // 200 OK
        /*
         * 사원 권한 확인 (+로그인 확인)
         * 1. 사원 권한, 로그인 확인 성공 시
         * 본인의 잔여 연차 개수 반환 받음
         *  - 정상적으로 데이터 존재시 200 OK
         * 2. 사원 권한, 로그인 인증 실패시
         * 403 ERROR 반환
         */
    }

    @GetMapping("/employee/vacation/remain/request")
    public ResponseEntity<Integer> getRemainOfVacationOfMineForRequest(HttpServletRequest req) {
        String id = getLoginIdOrNull(req);
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403 Error
        }
        int setting = employeeService.getRemainOfVacationOfMineForRequest(id); //본인의 잔여 연차 개수 반환 받음
        log.info("남은 연차 수 : {}", setting);

        return ResponseEntity.ok(setting); // 200 OK
        /*
         * 사원 권한 확인 (+로그인 확인)
         * 1. 사원 권한, 로그인 확인 성공 시
         * 본인의 잔여 연차 개수 반환 받음
         *  - 정상적으로 데이터 존재시 200 OK
         * 2. 사원 권한, 로그인 인증 실패시
         * 403 ERROR 반환
         */
    }


    //employee 2 end

    //employee 3


    @GetMapping("/employee/information")
    public ResponseEntity<Employee> getInformationOfMine(HttpServletRequest req) {
        String loginId = getLoginIdOrNull(req);
        if (loginId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Employee result = employeeService.findEmployeeInfoById(loginId);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        log.info("조회 결과 없음 혹은 실패");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping("/employee/information")
    public ResponseEntity<com.example.bootproject.vo.vo1.response.employee.EmployeeResponseDto> modifyEmployeeInformationOfMine(HttpServletRequest req, @ModelAttribute EmployeeInformationUpdateDto dto) {
        String loginId = getLoginIdOrNull(req);
        if (loginId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        EmployeeResponseDto result = employeeService.updateInformation(loginId, dto);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        log.info("조회 결과 없음 혹은 실패");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid LoginRequestDto dto, BindingResult br, HttpServletRequest req) {
        if (br.hasErrors()) {
            LoginResponseDto body = new LoginResponseDto();
            body.setMessage(br.getAllErrors().toString());
            log.info("Validation rule violated" + br.getAllErrors());
            return ResponseEntity.badRequest().body(body);
        }


        if (br.hasErrors()) {
            log.info("LoginRequestDto validation error");
            return ResponseEntity.badRequest().build();
        }
        /*TODO : loginRequestDto validation 체크 필요*/

        dto.setIp(dto.getIp() == null ? IpAnalyzer.getClientIp(req) : dto.getIp());
        log.info("LoginRequestDto dto : {}", dto);
        LoginResponseDto loginResult = null;
        loginResult = loginService.sessionLogin(dto, req);
        if (loginResult != null && loginResult.getMessage().equals(SESSION_ID_NOT_MATCHED_LOGIN_REQUEST)) {
            return ResponseEntity.badRequest().body(loginResult);
        }
        log.info("session login 결과 : {}", loginResult);
        if (loginResult != null) {
            log.info("session login sessionId : {}", req.getSession(false).getId());
            return ResponseEntity.ok(loginResult);
        } else {
            loginResult = loginService.formLogin(dto, req);
            if (loginResult != null) {
                log.info("form login request : {}", dto);
                return ResponseEntity.ok(loginResult);
            }
            return ResponseEntity.badRequest().build();
        }
        /*
         * form 로그인 요청 - 로그인 request DTO 생성
         * 세션 로그인 검사 - 세션 로그인 서비스 생성 및 session 객체 전달
         * 세션 로그인 실패시 로그인 서비스 호출 - dto 전달
         * 서비스 응답 - 로그인 response Dto 전달. null 검사로 로그인 결과 확인
         * 정상 로그인 결과 : 로그인 아이디,ip, 로그인 시간 전달
         * ip 변경 발생 로그인 결과 : 로그인 아이디, 기존 로그인 ip 전달
         * */
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        LogoutResponseDto dto = logoutService.logout();
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/employee/appeal")
    public ResponseEntity<AppealRequestResponseDto> makeAppealRequest(@ModelAttribute AppealRequestDto dto, HttpServletRequest req) {
        String loginId = getLoginIdOrNull(req);
        if (loginId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        dto.setEmployeeId(loginId);

        log.info("정상 작업 진행");
        AppealRequestResponseDto result = null;

        result = appealService.makeAppealRequest(dto);

        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/employee/vacation")
    public ResponseEntity<VacationRequestResponseDto> requestVacation(@ModelAttribute com.example.bootproject.vo.vo1.request.vacation.VacationRequestDto dto, @SessionAttribute(name = "loginId") String employeeId, HttpServletRequest req) {

        String loginId = getLoginIdOrNull(req);
        if (loginId == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        dto.setEmployeeId(loginId);

        log.info("정상 작업 진행");
        VacationRequestResponseDto result = null;
        if (dto.getEmployeeId() == null) {
            dto.setEmployeeId(employeeId);
        } else if (!dto.getEmployeeId().equals(employeeId)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            result = vacationService.makeVacationRequest(dto);
        } catch (Exception e) {
            log.info("연차 요청 수행 도중 에러 발생");
            e.printStackTrace();
        }
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }
    //employee 3 end
}











