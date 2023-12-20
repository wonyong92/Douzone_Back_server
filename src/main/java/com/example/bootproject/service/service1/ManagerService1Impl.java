package com.example.bootproject.service.service1;


import com.example.bootproject.repository.mapper1.EmployeeMapper1;
import com.example.bootproject.repository.mapper1.ManagerMapper1;
import com.example.bootproject.vo.vo1.request.*;
import com.example.bootproject.vo.vo1.response.*;
import com.example.bootproject.vo.vo1.response.employee.EmployeeResponseWithoutPasswordDto;
import com.example.bootproject.vo.vo1.response.vacation.PagingRequsetWithDateSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.bootproject.vo.vo1.response.Page.PAGE_SIZE;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManagerService1Impl implements ManagerService1 {

    private final ManagerMapper1 managerMapper1;

    //년월알 데이터 형식 맞는지에 validationcheck
//    public static boolean isValidDate(int year, int month, int day) {
//        try {
//            LocalDate.of(year, month, day);
//            return true;
//        } catch (DateTimeException e) {
//            return false;
//        }
//
//    }


    /*
    - RegularTimeAdjustmentHistoryRequestDto 객체와 사원 ID를 받아서 근무 시간 조정 이력을 데이터베이스에 삽입한다.
    - 현재 시간을 근무 시간 조정 시간으로 설정한다.
    - DTO에 사원 ID와 근무 시간 조정 시간을 설정한 후 로그에 정보를 남긴다.
    - DTO를 데이터베이스에 삽입하고 결과로 영향 받은 행의 수를 받는다.
    - 사원 ID로 근무 시간 조정 이력을 조회한다.
    - 조회 결과가 존재하면 해당 결과를 반환한다.
    - 결과가 없으면 로그를 남기고 null을 반환한다. 이 부분에서 예외 처리를 추가할 수 있다.
    */

    /*
    - 사원 ID, 페이지 번호, 정렬 기준, 정렬 방향을 인자로 받는다.
    - 로그를 남기면서 근태 승인 정보를 조회한다.
    - 데이터베이스에서 근태 승인 정보를 페이징 처리하여 조회한다.
    - 조회 결과를 Page 객체에 담아 반환한다.
    - 데이터는 List<AttendanceApprovalUpdateResponseDto> 타입으로 반환된다.
    */
    private final EmployeeMapper1 employeeMapper;

/*
- 사원 ID, 페이지 번호, 정렬 기준, 정렬 방향을 인자로 받는다.
- 조정 요청 이력을 페이징 처리하여 조회한다.
- 조회 결과를 Page 객체에 담아 반환한다.
- 로그를 남겨 조회 정보를 기록한다.
*/

    //숫자여부판단
    public static boolean isNumeric(String str) {
        return str != null && str.matches("^[0-9]+$");
    }

    // 정규 출퇴근 시간 설정
    @Override
// 정규 출퇴근 시간 설정. 사원 ID는 컨트롤러에서 받아서 사용한다.
    public RegularTimeAdjustmentHistoryResponseDto insertRegularTimeAdjustmentHistory(RegularTimeAdjustmentHistoryRequestDto dto, String employeeId) {

        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }
        // 현재 설정 시간
        LocalDateTime regularTimeAdjustmentTime = LocalDateTime.now();

        dto.setEmployeeId(employeeId);
        dto.setRegularTimeAdjustmentTime(regularTimeAdjustmentTime);

        log.info("DTO 내용: {}", dto);
        int requestInfo = managerMapper1.insertregulartimeadjustment(dto, employeeId);

        RegularTimeAdjustmentHistoryResponseDto responseDto = managerMapper1.selectregulartimeadjustment(employeeId);

        // 결과 반환
        if (responseDto != null) {
            // 결과가 존재하면 그대로 반환
            return responseDto;
        } else {
            // 결과가 없으면 로그를 남기고 null 반환 또는 예외 처리
            log.info("사원 ID에 대한 데이터를 찾을 수 없습니다: {}", employeeId);
            return null; // 또는 적절한 예외를 던지거나 다른 처리를 수행
        }
        // TODO 사원 ID는 컨트롤러에서 받는다.
        // TODO GENERIC KEY를 사용하거나, 데이터 존재 여부를 확인하고 count로 조회 후 true로 설정하든지 해야한다.
    }

    @Override
    public Page<List<AttendanceApprovalUpdateResponseDto>> managerGetApprovalInfoByEmployeeId(String employeeId, int page, String sort, String desc) {

        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }
        int size = Page.PAGE_SIZE;
        int startRow = (page - 1) * size;
        log.info("사원 ID {}에 대한 승인 정보 페이지: {}, 정렬: {}, 방향: {}", employeeId, page, sort, desc);
        // 데이터베이스에서 데이터 조회
        List<AttendanceApprovalUpdateResponseDto> data = managerMapper1.getAllEmployeeByEmployeeId(employeeId, "attendance_info."+sort, desc, size, startRow);
        int totalElements = managerMapper1.countApprovalInfoByEmployeeId(employeeId);

        // 다음 페이지 존재 여부 계산
        boolean hasNext = (page * size) < totalElements;

        // Page 객체 생성. 여기서 data는 List<AttendanceApprovalUpdateResponseDto>입니다.
        Page<List<AttendanceApprovalUpdateResponseDto>> result = new Page<>(data, // 바로 data를 넘김. List<T> 타입을 만족함.
                hasNext, // 다음 페이지가 없으면 isLastPage는 true임.
                sort, desc, page, totalElements);

        return result;
    }

    //타사원의 조정요청 이력조회
    @Override
    public Page<List<AttendanceAppealMediateResponseDto>> managerfindAttendanceInfoByMine(String employeeId, int page, String sort, String desc) {

        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }
        int size = Page.PAGE_SIZE;
        int startRow = (page - 1) * size;

        List<AttendanceAppealMediateResponseDto> data = managerMapper1.managerfindAttendanceAppealByEmployeeId(employeeId, sort, desc, size, startRow);
        int totalElements = managerMapper1.countAttendanceAppealByEmployeeId(employeeId);
        boolean hasNext = (page * size) < totalElements;

        if (totalElements <= 0) {
            // 근태 이의 신청이 없을 때 처리할 로직
            log.error("사원 ID {}에 대한 조정요청이 없습니다.", employeeId);
            return null; // 또는 적절한 예외 처리를 할 수 있습니다.
        }


        // 로그 남김
        log.info("사원 ID {}의 조정 요청 이력 수: {}", employeeId, totalElements);

        Page<List<AttendanceAppealMediateResponseDto>> result = new Page<>(data, // 바로 data를 넘김. List<T> 타입을 만족함.
                hasNext, // 다음 페이지가 없으면 isLastPage는 true임.
                sort, desc, page, totalElements);

        return result;
    }

    //        사원 년,월,일 사원근태정보검색
    @Override
    public Page<List<AttendanceInfoResponseDto>> getmanagerAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId, String sort, String desc, int page, int year, int month) {

        int size = Page.PAGE_SIZE;
        int startRow = (page - 1) * size;

        List<AttendanceInfoResponseDto> data = managerMapper1.selectmanagerAttendanceByDate(attendanceDate, employeeId, sort, desc, size, startRow);
        int totalElements = managerMapper1.managercountAttendanceInfoByEmployeeId(employeeId, year, month);
        boolean hasNext = (page * size) < totalElements;

        if (totalElements <= 0) {
            // 근태 이의 신청이 없을 때 처리할 로직
            log.error("사원 ID {}에 대한 조정요청이 없습니다.", employeeId);
            return null; // 또는 적절한 예외 처리를 할 수 있습니다.
        }

        log.info("사원 ID {}의 조정 요청 이력 수: {}", employeeId, totalElements);

        Page<List<AttendanceInfoResponseDto>> result = new Page<>(data, hasNext, sort, desc, page, totalElements);

        return result;
    }

    //사원 년,월 사원근태정보검색
    @Override
    public Page<List<AttendanceInfoResponseDto>> getmanagerAttendanceByMonthAndEmployee(int year, int month, String employeeId, int page, String sort, String desc) {
        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }

        int size = Page.PAGE_SIZE;
        int startRow = (page - 1) * size;
        List<AttendanceInfoResponseDto> data = managerMapper1.selectmanagerAttendanceByMonthAndEmployee(year, month, employeeId, sort, desc, size, startRow);
        int totalElements = managerMapper1.managercountAttendanceInfoByEmployeeId(employeeId, year, month);
        boolean hasNext = (page * size) < totalElements;

        if (totalElements <= 0) {
            // 근태 이의 신청이 없을 때 처리할 로직
            log.error("사원 ID {}에 대한 조정요청이 없습니다.", employeeId);
            return null; // 또는 적절한 예외 처리를 할 수 있습니다.
        }

        Page<List<AttendanceInfoResponseDto>> result = new Page<>(data, // 바로 data를 넘김. List<T> 타입을 만족함.
                hasNext, // 다음 페이지가 없으면 isLastPage는 true임.
                sort, desc, page, totalElements);

        return result;
    }

    @Override
    public boolean employeeExists(String employeeId) {
        return managerMapper1.existsById(employeeId);
    }

    @Override
    public Page<List<VacationRequestDto>> getAllVacationHistory(PagingRequestWithDateDto pagingRequestWithDateDto) {

        int size = PAGE_SIZE; // 한 페이지에 출력할 게시물 개수
        int startRow = (pagingRequestWithDateDto.getCurrentPage() - 1) * size; // 가져오기 시작할 row의 번호

        String orderByCondition = pagingRequestWithDateDto.getSort(); // 정렬할 컬럼 이름
//        orderByCondition = (orderByCondition.equals("name")?"E.":"V.")+orderByCondition;
        log.info("size, orderByCondition,startRow,sortOrder : {},{},{},{}", size, orderByCondition, startRow, pagingRequestWithDateDto.getSortOrder());
        List<VacationRequestDto> getData = managerMapper1.getAllVacationHistory(size, orderByCondition, startRow, pagingRequestWithDateDto.getSortOrder(), pagingRequestWithDateDto.getDate()); // 현재 페이지에 대해서 size만큼 orderByCondition 정렬 조건에 맞추어 startRow부터 데이터를 가져온다
        log.info("managerMapper1.getEmpInfo의 getData : {}", getData);

        /* 가져온 데이터가 비어있다면 Page 객체를 새로 생성하고, Page 객체 중 가져온 데이터를 담는 속성에 빈 ArrayList를 생성하여 리턴한다 */
        if (getData.isEmpty()) {
            Page<List<VacationRequestDto>> pageObj = new Page();
            pageObj.setData(new ArrayList<>());
            return pageObj;
        }

        int totalRowCount = managerMapper1.getAllVacationRequestCountByDate(pagingRequestWithDateDto.getDate()); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestWithDateDto.getCurrentPage() < lastPageNumber); // 마지막 페이지 여부

        /* 생성자 이용해 Page 객체 생성, 리턴*/
        Page<List<VacationRequestDto>> result = new Page<>(getData, isLastPage, pagingRequestWithDateDto.getSort(), pagingRequestWithDateDto.getSortOrder(), pagingRequestWithDateDto.getCurrentPage(), totalRowCount);
        return result;
    }

    @Override
    public Page<List<VacationRequestDto>> getHistoryVacationOfEmployee(PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto) {
        int size = PAGE_SIZE; // 한 페이지에 출력할 게시물 개수
        int startRow = (pagingRequestWithIdStatusDto.getCurrentPage() - 1) * size; // 가져오기 시작할 row의 번호
        String orderByCondition = pagingRequestWithIdStatusDto.getSort(); // 정렬할 컬럼 이름

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> getData = managerMapper1.getHistoryOfVacationOfEmployee(size, orderByCondition, startRow, pagingRequestWithIdStatusDto.getSortOrder(), pagingRequestWithIdStatusDto.getId(), pagingRequestWithIdStatusDto.getStatus());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine()의 getData : {}", getData);

        /* 가져온 데이터가 비어있다면 Page 객체를 새로 생성하고, Page 객체 중 가져온 데이터를 담는 속성에 빈 ArrayList를 생성하여 리턴한다 */
        if (getData.isEmpty()) {
            Page<List<VacationRequestDto>> pageObj = new Page();
            pageObj.setData(new ArrayList<>());
            return pageObj;
        }

        int totalRowCount = managerMapper1.getHistoryOfVacationOfEmployeeTotalRow(pagingRequestWithIdStatusDto.getId(), pagingRequestWithIdStatusDto.getStatus()); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestWithIdStatusDto.getCurrentPage() < lastPageNumber); // 마지막 페이지 여부

        /* 생성자 이용해 Page 객체 생성, 리턴*/
        Page<List<VacationRequestDto>> result = new Page<>(getData, isLastPage, pagingRequestWithIdStatusDto.getSort(), pagingRequestWithIdStatusDto.getSortOrder(), pagingRequestWithIdStatusDto.getCurrentPage(), totalRowCount);
        return result;
    }

    @Override
    public Page<List<SettingWorkTimeDto>> getSettingWorkTime(PagingRequestDto pagingRequestDto) {
        int size = PAGE_SIZE; // 한 페이지에 출력할 게시물 개수
        int startRow = (pagingRequestDto.getCurrentPage() - 1) * size; // 가져오기 시작할 row의 번호
        String orderByCondition = pagingRequestDto.getSort(); // 정렬할 컬럼 이름

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<SettingWorkTimeDto> getData = managerMapper1.getSettingWorkTime(size, orderByCondition, startRow, pagingRequestDto.getSortOrder());
        log.info("managerMapper1.getSettingWorkTime의 getData : {}", getData);

        /* 가져온 데이터가 비어있다면 Page 객체를 새로 생성하고, Page 객체 중 가져온 데이터를 담는 속성에 빈 ArrayList를 생성하여 리턴한다 */
        if (getData.isEmpty()) {
            Page<List<SettingWorkTimeDto>> pageObj = new Page();
            pageObj.setData(new ArrayList<>());
            return pageObj;
        }

        int totalRowCount = managerMapper1.getSettingWorkTimeCount(); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestDto.getCurrentPage() < lastPageNumber); // 마지막 페이지 여부

        /* 생성자 이용해 Page 객체 생성, 리턴*/
        Page<List<SettingWorkTimeDto>> result = new Page<>(getData, isLastPage, pagingRequestDto.getSort(), pagingRequestDto.getSortOrder(), pagingRequestDto.getCurrentPage(), totalRowCount);
        return result;
    }

    @Override
    public Page<List<VacationQuantitySettingDto>> getVacationSettingHistory(PagingRequestDto pagingRequestDto) {
        int size = PAGE_SIZE; // 한 페이지에 출력할 게시물 개수
        int startRow = (pagingRequestDto.getCurrentPage() - 1) * size; // 가져오기 시작할 row의 번호
        String orderByCondition = pagingRequestDto.getSort(); // 정렬할 컬럼 이름

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationQuantitySettingDto> getData = managerMapper1.getVacationSettingHistory(size, orderByCondition, startRow, pagingRequestDto.getSortOrder());
        log.info("managerMapper1.getVacationSettingHistory의 getData : {}", getData);

        /* 가져온 데이터가 비어있다면 Page 객체를 새로 생성하고, Page 객체 중 가져온 데이터를 담는 속성에 빈 ArrayList를 생성하여 리턴한다 */
        if (getData.isEmpty()) {
            Page<List<VacationQuantitySettingDto>> pageObj = new Page();
            pageObj.setData(new ArrayList<>());
            return pageObj;
        }

        int totalRowCount = managerMapper1.getVacationSettingHistoryCount(); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestDto.getCurrentPage() < lastPageNumber); // 마지막 페이지 여부

        /* 생성자 이용해 Page 객체 생성, 리턴*/
        Page<List<VacationQuantitySettingDto>> result = new Page<>(getData, isLastPage, pagingRequestDto.getSort(), pagingRequestDto.getSortOrder(), pagingRequestDto.getCurrentPage(), totalRowCount);
        return result;


    }

    @Override
    public DefaultVacationResponseDto makeDefaultVacationResponse(DefaultVacationRequestDto dto) { //dto에는 1년 미만 일 때 개수, 1년 이상일 때 개수, 대상 날짜의 데이터만 존재함

        managerMapper1.insertDefaultVacation(dto); //데이터 insert

        // generated key를 이용하여 insert한 데이터를 가져와 반환
        DefaultVacationResponseDto result = managerMapper1.getDefaultVacationResponseDto(dto.getSettingKey());
        log.info("managerMapper1.getDefaultVacationResponseDto(dto.getSettingKey())의 result : {}", result);

        return result;

    }

    @Override
    public int getDefaultSettingValue(String employeeId) {

        int year = managerMapper1.getHireYear(employeeId); //입사 연도 들고옴
        log.info("getHireYear(employeeId) : {}", year);

        int setting = managerMapper1.getDefaultSettingVacationValue(year); //입사 연도에 따른 기본 연차 설정값 들고옴
        log.info("managerMapper1.getDefaultSettingVacationValue(year) : {}", setting);

        /* 조정된 연차 개수를 들고 와서, 기본 연차 설정 값과 더한 결과*/
        // 만약 조정된 것이 없다면? 0이 리턴되도록
        int thisYearSettingVacationValue = setting + managerMapper1.getVacationAdjustedHistory(employeeId);
        log.info("조정된 연차 개수 데이터 : {}", managerMapper1.getVacationAdjustedHistory(employeeId));
        log.info("기본 연차 설정 값 + 조정된 연차 개수 데이터 : {}", thisYearSettingVacationValue);
        //

        /* 연차 신청 결과 승인인 튜플 중 vacation_quantity의 총합 */
        int approveVacationSum = managerMapper1.getApproveVacationQuantity(employeeId);
        log.info("올해 연차 승인 개수 합 : {}", approveVacationSum);

        return thisYearSettingVacationValue - approveVacationSum; //기본값 + 조정값 - 승인 튜플 수
    }

    @Override
    public int getEmployeeCheck(String id) {
        return managerMapper1.getEmployeeCheck(id);
    }

    public Page<List<EmployeeResponseWithoutPasswordDto>> getEmployeeList(int page, String sort, String desc) {
        int size = PAGE_SIZE; // Page 객체로부터 size를 가져옴
        int startRow = (page - 1) * size; // 가져오기 시작할 row의 번호

        List<EmployeeResponseWithoutPasswordDto> findEmployees = employeeMapper.getAllEmployee(sort, desc, size, startRow);
        log.info("List<EmployeeResponseDto> result : {}", findEmployees);
        if (findEmployees.isEmpty()) {
            Page<List<EmployeeResponseWithoutPasswordDto>> empty = new Page<>();
            empty.setData(new ArrayList<>());
            return empty;
        }

        int totalRowCount = employeeMapper.getEmpInfoTotalRow(); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호

        Page<List<EmployeeResponseWithoutPasswordDto>> result = new Page<>(findEmployees, page < lastPageNumber, sort, desc, page, totalRowCount);
        log.info("Page<List<EmployeeResponseDto>> result : {}", result);
        return result;
    }


    @Override
    public Page<List<VacationResponseDto>> getAllVacationHistorys(int page, String sort, String desc, String searchParameter) {
        int size = PAGE_SIZE;
        int startRow = (page - 1) * size;
        List<VacationResponseDto> vacationHistory;
        int totalRowCount;

        if (isNumeric(searchParameter)) {
            vacationHistory = managerMapper1.getAllVacationHistorysById(size, startRow, sort, desc, searchParameter);
            totalRowCount = managerMapper1.countAllVacationHistoryById(searchParameter);
        } else {
            vacationHistory = managerMapper1.getAllVacationHistorysByName(size, startRow, sort, desc, searchParameter);
            totalRowCount = managerMapper1.countAllVacationHistoryByName(searchParameter);
        }

        if (vacationHistory.isEmpty()) {
            return new Page<>(new ArrayList<>(), false, sort, desc, page, 0);
        }

        int lastPageNumber = (int)Math.ceil((double) totalRowCount / size);
        return new Page<>(vacationHistory, page < lastPageNumber, sort, desc, page, totalRowCount);
    }

    @Override
    public Page<List<AttendanceAppealHistory>> getAllAttendanceHistory(int page, String sort, String desc, String searchParameter) {
        int size = PAGE_SIZE;
        int startRow = (page - 1) * size;
        List<AttendanceAppealHistory> attendanceAppealHistory;
        int totalRowCount;

        if (isNumeric(searchParameter)) {
            attendanceAppealHistory = managerMapper1.getAllAttendanceHistorysById(size, startRow, sort, desc, searchParameter);
            totalRowCount = managerMapper1.countAllAttendanceHistoryById(searchParameter);
        } else {
            attendanceAppealHistory = managerMapper1.getAllAttendanceHistorysByName(size, startRow, sort, desc, searchParameter);
            totalRowCount = managerMapper1.countAllAttendanceHistoryByName(searchParameter);
        }

        if (attendanceAppealHistory.isEmpty()) {
            return new Page<>(new ArrayList<>(), false, sort, desc, page, 0);
        }

        int lastPageNumber = (int)Math.ceil((double) totalRowCount / size);
        return new Page<>(attendanceAppealHistory, page < lastPageNumber, sort, desc, page, totalRowCount);
    }

    @Override
    public int getVacationDefaultLatestCount(String info) {
        log.info("getVacationDefaultLatestCount : {}", info);
        return managerMapper1.getVacationDefaultLatestCount(info);
    }

    @Override
    public Page<List<VacationResponseDto>> getVacationHistory(PagingRequsetWithDateSearchDto requestDto) {
        // 파라미터 추출
        int size = Page.PAGE_SIZE;
        int currentPage = requestDto.getPage();
        int startRow = (currentPage - 1) * size;
        String searchParameter = requestDto.getSearchParameter();
        String sort = requestDto.getSort();
        String desc = requestDto.getDesc();

        // 데이터 검색
        LocalDate searchDate = requestDto.makeLocalDate();
        String formattedDate = searchDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        List<VacationResponseDto> vacationHistory;
        int totalElement;

        // 검색 매개변수가 숫자인지 판별
        boolean isNumeric = isNumeric(searchParameter);

        if (requestDto.getDay() == null || requestDto.getDay() == 0) {
            if (isNumeric) {
                vacationHistory = managerMapper1.getVacationHistoryByMonthAndId(size, startRow, formattedDate, searchParameter);
                totalElement = managerMapper1.countVacationRequestByMonthAndId(formattedDate, searchParameter);
            } else {
                vacationHistory = managerMapper1.getVacationHistoryByMonth(size, startRow, formattedDate, searchParameter);
                totalElement = managerMapper1.countVacationRequestByMonth(formattedDate, searchParameter);
            }
        } else {
            String formattedDay = searchDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (isNumeric) {
                vacationHistory = managerMapper1.getVacationHistoryByDateAndId(size, startRow, formattedDay, searchParameter);
                totalElement = managerMapper1.countVacationRequestByDateAndId(formattedDay, searchParameter);
            } else {
                vacationHistory = managerMapper1.getVacationHistoryByDate(size, startRow, formattedDay, searchParameter);
                totalElement = managerMapper1.countVacationRequestByDate(formattedDay, searchParameter);
            }
        }

        // 결과 처리
        if (vacationHistory.isEmpty()) {
            return new Page<>(new ArrayList<>(), false, sort, desc, currentPage, 0);
        }
        int lastPageNumber = (int) Math.ceil((double) totalElement / size);
        boolean isLastPage = currentPage >= lastPageNumber;

        // 페이지 반환
        return new Page<>(vacationHistory, isLastPage, sort, desc, currentPage, totalElement);
    }



//    @Override
//    public Page<List<VacationRequestDto>> getAllVacationHistory(PagingRequestDto pagingRequestDto) {
//        int size = PAGE_SIZE; // 한 페이지에 출력할 게시물 개수
//        int currentPage = pagingRequestDto.getCurrentPage(); // 현재 페이지
//        int startRow = (currentPage - 1) * size; // 가져오기 시작할 row의 번호
//        String orderByCondition = pagingRequestDto.getSort(); // 정렬할 컬럼 이름
//        String sortOrder = pagingRequestDto.getSortOrder(); // 정렬 순서
//
//        // 전체 데이터 개수 조회
//        int totalElements = managerMapper1.getVacationAdjustedHistory(); // myMapper는 예시이며, 실제 매퍼를 사용해야 함
//
//        // 현재 페이지 데이터 조회
//        List<VacationRequestDto> vacationRequests = managerMapper1.findAllVacationRequests(startRow, size, orderByCondition, sortOrder); // myMapper는 예시이며, 실제 매퍼를 사용해야 함
//
//        // 총 페이지 수 계산
//        int totalPages = (int) Math.ceil((double) totalElements / size);
//
//        // Page 객체 생성 및 반환
//        return new Page<>(vacationRequests, currentPage, totalPages, totalElements);
//    }

    @Override
    public Page<List<AttendanceAppealHistory>> getAttendanceHistory(PagingRequsetWithDateSearchDto requestDto) {
        int size = Page.PAGE_SIZE;
        int currentPage = requestDto.getPage();
        int startRow = (currentPage - 1) * size;
        String searchParameter = requestDto.getSearchParameter();
        String sort = requestDto.getSort();
        String desc = requestDto.getDesc();

        LocalDate searchDate = requestDto.makeLocalDate();
        String formattedDate = searchDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        List<AttendanceAppealHistory> attendanceAppealHistory;
        int totalElement;

        boolean isNumeric = isNumeric(searchParameter);

        if (requestDto.getDay() == null || requestDto.getDay() == 0) {
            if (isNumeric) {
                attendanceAppealHistory = managerMapper1.getAttendanceHistoryByMonthAndId(size, startRow, formattedDate, searchParameter);
                totalElement = managerMapper1.countAttendanceRequestByMonthAndId(formattedDate, searchParameter);
            } else {
                attendanceAppealHistory = managerMapper1.getAttendanceHistoryByMonthname(size, startRow, formattedDate, searchParameter);
                totalElement = managerMapper1.countAttendanceRequestByMonth(formattedDate, searchParameter);
            }
        } else {
            String formattedDay = searchDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (isNumeric) {
                attendanceAppealHistory = managerMapper1.getAttendanceHistoryByDateAndId(size, startRow, formattedDay, searchParameter);
                totalElement = managerMapper1.countAttendanceRequestByDateAndId(formattedDay, searchParameter);
            } else {
                attendanceAppealHistory = managerMapper1.getAttendanceHistoryByDatename(size, startRow, formattedDay, searchParameter);
                totalElement = managerMapper1.countAttendanceRequestByDate(formattedDay, searchParameter);
            }
        }

        if (attendanceAppealHistory.isEmpty()) {
            return new Page<>(new ArrayList<>(), false, sort, desc, currentPage, 0);
        }
        int lastPageNumber = (int) Math.ceil((double) totalElement / size);
        boolean isLastPage = currentPage >= lastPageNumber;

        // 페이지 반환
        return new Page<>(attendanceAppealHistory, isLastPage, sort, desc, currentPage, totalElement);

    }








    @Override
    public List<AllAttendanceAppealMediateResponseDto> searchAppealAllRequestedByIdOrNumber(String searchParameter) {

        // 숫자만 있는지 검사합니다.
        if (searchParameter.matches("^[a-zA-Z0-9가-힣]+$")) {
            // 숫자나 문자(한글 포함)만 포함되어 있을 경우, 더 세부적인 검색을 진행
            if (searchParameter.matches("\\d+")) {
                // 숫자일 경우, employeeId로 검색
                return managerMapper1.searchAppealAllRequestedById(searchParameter);
            } else {
                // 문자열(한글 포함)일 경우, name으로 검색
                return managerMapper1.searchAppealAllRequestedByName(searchParameter);
            }
        } else {
            log.info("특수문자가 포함되어있습니다");
            return null;


        }
    }

    @Override
    public List<AllVacationRequestResponseDto> searchAllVacationRequestedByIdOrNumber(String searchParameter) {
        // 숫자만 있는지 검사합니다.
        if (searchParameter.matches("^[a-zA-Z0-9가-힣]+$")) {
            // 숫자나 문자(한글 포함)만 포함되어 있을 경우, 더 세부적인 검색을 진행
            if (searchParameter.matches("\\d+")) {
                // 숫자일 경우, employeeId로 검색
                return managerMapper1.searchVacationAllRequestedById(searchParameter);
            } else {
                // 문자열(한글 포함)일 경우, name으로 검색
                return managerMapper1.searchVacationAllRequestedByName(searchParameter);
            }
        } else {
            log.info("특수문자가 포함되어있습니다");
            return null;


        }
    }

}

