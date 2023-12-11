package com.example.bootproject.service.service1;


import com.example.bootproject.entity.Employee;
import com.example.bootproject.repository.mapper1.EmployeeMapper1;
import com.example.bootproject.vo.vo1.request.*;
import com.example.bootproject.vo.vo1.request.employee.EmployeeInformationUpdateDto;
import com.example.bootproject.vo.vo1.response.*;
import com.example.bootproject.vo.vo1.response.employee.EmployeeResponseWithoutPasswordDto;
import com.example.bootproject.vo.vo1.response.vacation.VacationRequestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.bootproject.vo.vo1.response.Page.PAGE_SIZE;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper1 employeeMapper1;

 /*
- 사원 ID로 사원의 존재 여부를 확인한다.
- 사원 ID가 없을 경우, 로그를 남기고 null을 반환하여 204 No Content 응답을 나타낸다.
- 사원 ID가 존재하지 않는 경우, 로그를 남기고 null을 반환하여 400 Bad Request 응답을 나타낸다.
- 해당 날짜에 대해 사원의 출근 시간을 조회한다.
- 출근 시간이 이미 기록되어 있으면 로그를 남기고 null을 반환한다.
- 출근 시간이 없으면 현재 시간으로 출근 시간을 기록하고 데이터베이스에 저장한다.
- 기록 후, 출근 정보를 조회하여 반환한다.
- 조회에 성공하면 출근 정보를 담아 200 OK 응답과 함께 반환한다.
- 조회에 실패하면 로그를 남기고 null을 반환하여 400 Bad Request 응답을 나타낸다.
*/


  /*
- 사원 ID로 사원의 존재 여부를 확인한다.
- 사원 ID가 없을 경우, 로그를 남기고 null을 반환
- 사원 ID에 대한 유효성 검사에 실패하면 로그를 남기고 null을 반환
- 해당 날짜에 대해 사원의 출근 시간과 퇴근 시간을 조회한다.
- 출근 시간이 없으면 로그를 남기고 null을 반환한다.
- 퇴근 시간이 이미 기록되어 있으면 로그를 남기고 null을 반환한다.
- 퇴근 시간이 없으면 현재 시간으로 퇴근 시간을 기록하고 데이터베이스에 저장한다.
- 기록 후, 퇴근 정보를 조회하여 반환한다
- 조회에 실패하면 로그를 남기고 null을 반환
*/

    //퇴근요청
    public AttendanceInfoResponseDto makeEndRequest(AttendanceInfoEndRequestDto dto, String employeeId) {

        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }
        LocalDate attendanceDate = LocalDate.now();
        LocalDateTime findStartTime = employeeMapper1.getStartTimeByEmployeeIdAndDate(employeeId, attendanceDate);
        LocalDateTime findEndTime = employeeMapper1.getEndTimeByEmployeeIdAndDate(employeeId, attendanceDate);

        AttendanceInfoResponseDto responseDto = new AttendanceInfoResponseDto(); // Response 객체 초기화

        if (findStartTime == null) {
            responseDto.setMessage("출근기록이없습니다");
            log.info("출근기록이없습니다: {}", employeeId);
            return responseDto;
        } else if (findEndTime != null) {
            responseDto.setMessage("퇴근기록이있습니다");
            log.info("퇴근기록이있습니다: {}", employeeId);
            return responseDto;
        } else {
            LocalDateTime endTime = LocalDateTime.now();
            dto.setEmployeeId(employeeId);
            dto.setAttendanceDate(attendanceDate);
            dto.setEndTime(endTime);

            int endKey = employeeMapper1.endTimeRequest(dto);
            if (endKey > 0) {
                responseDto = employeeMapper1.findattendanceInfo(employeeId, attendanceDate);
                if (responseDto != null) {
                    return responseDto;
                } else {
                    responseDto.setMessage("출근정보를 조회하는데 실패하였습니다");
                    log.info("출근정보를 조회하는데 실패하였습니다: {}", employeeId);
                    return responseDto;
                }
            } else {
                responseDto.setMessage("기록에 실패하셨습니다");
                log.info("기록에 실패하셨습니다: {}", employeeId);
                return responseDto;
            }
        }
    }

        /*
- 사원 ID로 사원의 존재 여부를 확인한다.
- 사원 ID가 없을 경우, 로그를 남기고 null을 반환하여 204 No Content 응답을 나타낸다.
- 유효한 사원 ID가 있을 경우, 해당 날짜의 사원 근태 정보를 조회한다.
- 조회된 정보가 없으면 로그를 남기고 null을 반환하여 204 No Content 응답을 나타낸다.
- 조회에 성공하면 근태 정보 목록을 담아 200 OK 응답과 함께 반환한다.
*/

    //        사원 년,월,일 사원근태정보검색
    @Override
    public Page<List<AttendanceInfoResponseDto>> getAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId, String sort, String desc, int page) {

        int size = Page.PAGE_SIZE;
        int startRow = (page - 1) * size;

        List<AttendanceInfoResponseDto> data = employeeMapper1.selectAttendanceByDate(attendanceDate, employeeId, sort, desc, size, startRow);
        int totalElements = employeeMapper1.countAttendanceInfoByMonthEmployeeId(employeeId, attendanceDate);
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


    public Page<List<AttendanceInfoResponseDto>> getAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId, String sort, String desc, int page, int pageSize) {

        int size = pageSize;
        int startRow = (page - 1) * size;

        List<AttendanceInfoResponseDto> data = employeeMapper1.selectAttendanceByDate(attendanceDate, employeeId, sort, desc, size, startRow);
        int totalElements = employeeMapper1.countAttendanceInfoByMonthEmployeeId(employeeId, attendanceDate);
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

 /*
- 사원 ID로 사원의 존재 여부를 확인한다.
- 사원 ID가 없을 경우, 로그를 남기고 null을 반환하여 204 No Content 응답을 나타낸다.
- 유효한 사원 ID가 있을 경우, 해당 연월의 사원 근태 정보를 조회한다.
- 조회된 정보가 없으면 로그를 남기고 null을 반환하여 204 No Content 응답을 나타낸다.
- 조회에 성공
*/

    //자신의 근태승인요청

    //사원 년,월 사원근태정보검색
    @Override
    public Page<List<AttendanceInfoResponseDto>> getAttendanceByMonthAndEmployee(int year, int month, String employeeId, int page, String sort, String desc, int pageSize) {
        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }

        int size = pageSize;
        int startRow = (page - 1) * size;
        List<AttendanceInfoResponseDto> data = employeeMapper1.selectAttendanceByMonthAndEmployee(year, month, employeeId, sort, desc, size, startRow);
        int totalElements = employeeMapper1.countAttendanceInfoByEmployeeId(employeeId, year, month);
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
    public Page<List<AttendanceInfoResponseDto>> getAttendanceByMonthAndEmployee(int year, int month, String employeeId, int page, String sort, String desc) {
        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }

        int size = PAGE_SIZE;
        int startRow = (page - 1) * size;
        List<AttendanceInfoResponseDto> data = employeeMapper1.selectAttendanceByMonthAndEmployee(year, month, employeeId, sort, desc, size, startRow);
        int totalElements = employeeMapper1.countAttendanceInfoByEmployeeId(employeeId, year, month);
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

        /*
- 사원 ID의 존재 유무를 확인한다.
- 존재하지 않는 경우, 로그를 남기고 null을 반환한다.
- 지각 상태 정보를 조회하고, 근태 상태를 업데이트한다.
- 업데이트된 행 수를 로그로 남긴다.
- 근태 승인 정보를 데이터베이스에 삽입한다.
- 삽입된 행 수를 로그로 남긴다.
- 근태 승인 정보를 조회하여 반환한다.
*/

    public AttendanceApprovalResponseDto approveAttendance(String employeeId, Long attendanceInfoId) {
        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }

        AttendanceStatusCategoryRequestDto lateStatus = employeeMapper1.findLateStatus();
        log.info("지각 상태 정보를 가져왔습니다: {}", lateStatus);

        // 2. 근태 상태 업데이트
        AttendanceApprovalUpdateRequestDto updateRequestDto = new AttendanceApprovalUpdateRequestDto(lateStatus.getKey(), attendanceInfoId);
        int updatedRows = employeeMapper1.updateAttendanceStatus(updateRequestDto);
        log.info("근태 상태를 업데이트 했습니다. 업데이트된 행의 수: {}", updatedRows);

        // 3. 근태 승인 정보 삽입
        AttendanceApprovalInsertRequestDto insertRequestDto = new AttendanceApprovalInsertRequestDto(attendanceInfoId, employeeId);
        int insertedRows = employeeMapper1.insertAttendanceApproval(insertRequestDto);
        if (insertedRows != 1) {
            log.info("근태 승인 정보가 삽입되지 않았습니다 {}", insertedRows);
        } else {
            log.info("근태 승인 정보를 삽입했습니다. 삽입된 행의 수: {}", insertedRows);
        }
        // 4. 근태 승인 정보를 검색하여 반환
        return employeeMapper1.findAttendanceApproval(employeeId, attendanceInfoId);
    }
/*
        getApprovalInfoByEmployeeId 메소드:
                - 사원 ID의 존재 유무를 확인한다.
- 존재하지 않는 경우, 로그를 남기고 null을 반환한다.
                - 정렬과 페이징 정보를 기반으로 근태 승인 내역을 데이터베이스에서 조회한다.
- 조회 결과를 Page 객체에 담아 반환한다.
*/

    //자신의 근태 승인내역
    @Override
    public Page<List<AttendanceApprovalUpdateResponseDto>> getApprovalInfoByEmployeeId(String employeeId, int page, String sort, String desc) {
        int size = Page.PAGE_SIZE;
        int startRow = (page - 1) * size;

        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }

        // 데이터베이스에서 데이터를 조회합니다.
        List<AttendanceApprovalUpdateResponseDto> data = employeeMapper1.getAllEmployeeByEmployeeId(employeeId, sort, desc, size, startRow);
        int totalElement = employeeMapper1.countApprovalInfoByEmployeeId(employeeId);

        if (data.isEmpty()) {
            log.info("반환 데이터가 비어있습니다: {}", employeeId);

            return null;
        } else {
            log.info("사원id에대한 반환데이터가 제대로 들어왔습니다: {}", employeeId);
        }


        boolean hasNext = (page * size) < totalElement;


        Page<List<AttendanceApprovalUpdateResponseDto>> result = new Page<>(data, hasNext, sort, desc, page, totalElement);

        return result;
    }

        /*findAttendanceInfoByMine 메소드:
- 정렬과 페이징 정보를 기반으로 사원의 조정 요청 이력을 데이터베이스에서 조회한다.
- 조회된 목록과 페이징 정보를 Page 객체에 담아 반환한다.
- 사원 ID와 조회된 이력의 총 수를 로그로 남긴다.
*/

    //본인의 조정요청이력조회
    @Override
    public Page<List<AttendanceAppealMediateResponseDto>> findAppealRequestOfMine(String employeeId, int page, String sort, String desc) {


        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }

        int size = Page.PAGE_SIZE;
        int startRow = (page - 1) * size;
        List<AttendanceAppealMediateResponseDto> data = employeeMapper1.findAttendanceAppealByEmployeeId(employeeId, sort, desc, size, startRow);
        //빈 데이터 확인

        if (data.isEmpty()) {
            log.info("반환 데이터가 비어있습니다: {}", employeeId);

            return null;
        } else {
            log.info("사원id에대한 반환데이터가 제대로 들어왔습니다: {}", employeeId);
        }


        int totalElements = employeeMapper1.countAttendanceAppealByEmployeeId(employeeId);

        if (totalElements <= 0) {
            // 근태 이의 신청이 없을 때 처리할 로직
            log.error("사원 ID {}에 대한 조정요청이 없습니다.", employeeId);
            return null;
        }

        boolean hasNext = (page * size) < totalElements;

        // 로그를 남깁니다.
        log.info("사원 ID {}에 대한 조정요청 목록: {}", employeeId, totalElements);

        Page<List<AttendanceAppealMediateResponseDto>> result = new Page<>(data, // 바로 data를 넘깁니다. List<T> 타입을 만족합니다.
                hasNext, // 다음 페이지가 없으면 isLastPage는 true입니다.
                sort, desc, page, totalElements);

        return result;
    }

          /*
        searchByEmployeeIdOrName 메소드:
                - 검색 파라미터가 숫자인지 문자인지에 따라 사원 ID 또는 이름으로 검색을 수행한다.
- 검색 결과를 리스트로 반환한다.
                */

    //사원검색
    @Override
    public List<EmployeeSearchResponseDto> searchByEmployeeIdOrName(String searchParameter) {
        // 숫자만 있는지 검사합니다.
        if (searchParameter.matches("^[a-zA-Z0-9가-힣]+$")) {
            // 숫자나 문자(한글 포함)만 포함되어 있을 경우, 더 세부적인 검색을 진행
            if (searchParameter.matches("\\d+")) {
                // 숫자일 경우, employeeId로 검색
                return employeeMapper1.searchEmployeeEmployeeId(searchParameter);
            } else {
                // 문자열(한글 포함)일 경우, name으로 검색
                return employeeMapper1.searchEmployeeName(searchParameter);
            }
        } else {
            log.info("특수문자가 포함되어있습니다");
            return null;
        }

    }

    @Override
    public boolean employeeExists(String employeeId) {
        return employeeMapper1.existsById(employeeId);
    }

    @Override
    //출근요청
    //T
    public AttendanceInfoResponseDto makeStartRequest(AttendanceInfoStartRequestDto dto) {
        // 오늘 날짜 설정
        LocalDate attendanceDate = LocalDate.now();
        String employeeId = dto.getEmployeeId();
        // 기존 출근 정보 조회
        AttendanceInfoResponseDto responseDto = employeeMapper1.findattendanceInfo(employeeId, attendanceDate);

        // 출근 정보가 이미 있으면 바로 반환
        if (responseDto != null) {
            responseDto.setMessage("출근기록이있습니다");
            log.info("출근기록이있습니다: {}", employeeId);
            return responseDto;
        }

        // 출근 기록이 없으면 새로운 기록 생성
        LocalDateTime startTime = LocalDateTime.now();
        dto.setAttendanceDate(attendanceDate);
        dto.setStartTime(startTime);

        // DB에 출근 시간 기록
        employeeMapper1.startTimeRequest(dto);

        // DB 기록 후 다시 출근 정보 조회
        responseDto = employeeMapper1.findattendanceInfo(employeeId, attendanceDate);

        // 조회 성공 여부 확인 후 반환
        if (responseDto != null) {
            // 조회 성공, 출근 정보 반환
            return responseDto;
        } else {
            // 조회 실패, 로그 기록 후 null 반환
            log.info("출근 정보를 조회하는데 실패하였습니다: {}", employeeId);
            return null;
        }
    }

    @Override
    public Page<List<VacationRequestDto>> getHistoryOfVacationOfMine(PagedLocalDateDto pagedLocalDateDto, String employeeId, String status) {
        PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto = new PagingRequestWithIdStatusDto(pagedLocalDateDto.getPage(), pagedLocalDateDto.getSort(), pagedLocalDateDto.getDesc(), employeeId, status);

        int size = PAGE_SIZE; // 한 페이지에 출력할 게시물 개수
        int startRow = (pagingRequestWithIdStatusDto.getCurrentPage() - 1) * size; // 가져오기 시작할 row의 번호
        String orderByCondition = pagingRequestWithIdStatusDto.getSort(); // 정렬할 컬럼 이름

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> getData = employeeMapper1.getHistoryOfVacationOfMine(size, orderByCondition, startRow, pagingRequestWithIdStatusDto.getSortOrder(), pagingRequestWithIdStatusDto.getId(), pagingRequestWithIdStatusDto.getStatus());
        log.info("employeeMapper1.getHistoryOfRejectedVacationOfMine()의 getData : {}", getData);

        /* 가져온 데이터가 비어있다면 Page 객체를 새로 생성하고, Page 객체 중 가져온 데이터를 담는 속성에 빈 ArrayList를 생성하여 리턴한다 */
        if (getData.isEmpty()) {
            Page<List<VacationRequestDto>> pageObj = new Page();
            pageObj.setData(new ArrayList<>());
            return pageObj;
        }

        int totalRowCount = employeeMapper1.getHistoryOfVacationOfMineTotalRow(pagingRequestWithIdStatusDto.getId(), pagingRequestWithIdStatusDto.getStatus()); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestWithIdStatusDto.getCurrentPage() < lastPageNumber); // 마지막 페이지 여부

        /* 생성자 이용해 Page 객체 생성, 리턴*/
        Page<List<VacationRequestDto>> result = new Page<>(getData, isLastPage, pagingRequestWithIdStatusDto.getSort(), pagingRequestWithIdStatusDto.getSortOrder(), pagingRequestWithIdStatusDto.getCurrentPage(), totalRowCount);
        return result;
    }

    @Override
    public int getRemainOfVacationOfMine(String id) {
        int year = employeeMapper1.getHireYear(id); //입사 연도 들고옴
        log.info("getHireYear(employeeId) : {}", year);

        int setting = employeeMapper1.getDefaultSettingVacationValue(year); //입사 연도에 따른 기본 연차 설정값 들고옴
        log.info("employeeMapper1.getDefaultSettingVacationValue(year) : {}", setting);

        /* 조정된 연차 개수를 들고 와서, 기본 연차 설정 값과 더한 결과*/
        // 만약 조정된 것이 없다면? 0이 리턴되도록
        int thisYearSettingVacationValue = setting + employeeMapper1.getVacationAdjustedHistory(id);
        log.info("조정된 연차 개수 데이터 : {}", employeeMapper1.getVacationAdjustedHistory(id));
        log.info("기본 연차 설정 값 + 조정된 연차 개수 데이터 : {}", thisYearSettingVacationValue);
        //

        /* 연차 신청 결과 승인인 튜플 중 vacation_quantity의 총합 */
        int approveVacationSum = employeeMapper1.getApproveVacationQuantity(id);
        log.info("올해 연차 승인 개수 합 : {}", approveVacationSum);

        return thisYearSettingVacationValue - approveVacationSum; //기본값 + 조정값 - 승인 튜플 수
    }

    @Override
    public int getRemainOfVacationOfMineForRequest(String id) {
        int year = employeeMapper1.getHireYear(id); //입사 연도 들고옴
        log.info("getHireYear(employeeId) : {}", year);

        int setting = employeeMapper1.getDefaultSettingVacationValue(year); //입사 연도에 따른 기본 연차 설정값 들고옴
        log.info("employeeMapper1.getDefaultSettingVacationValue(year) : {}", setting);

        /* 조정된 연차 개수를 들고 와서, 기본 연차 설정 값과 더한 결과*/
        // 만약 조정된 것이 없다면? 0이 리턴되도록
        int thisYearSettingVacationValue = setting + employeeMapper1.getVacationAdjustedHistory(id);
        log.info("조정된 연차 개수 데이터 : {}", employeeMapper1.getVacationAdjustedHistory(id));
        log.info("기본 연차 설정 값 + 조정된 연차 개수 데이터 : {}", thisYearSettingVacationValue);
        //

        /* 연차 신청 결과 승인인 튜플 중 vacation_quantity의 총합 */
        int approveVacationSum = employeeMapper1.getApproveVacationQuantity(id);
        int requestedVacationSum = employeeMapper1.getRequestedVacationQuantity(id);
        log.info("올해 연차 승인 + 요청 개수 합 : {}", approveVacationSum + requestedVacationSum);

        return thisYearSettingVacationValue - approveVacationSum - requestedVacationSum; //기본값 + 조정값 - 승인 튜플 수 - 현재 요청 중인 튜플 수
    }

    @Override
    public Employee findEmployeeInfoById(String loginId) {
        Employee find = employeeMapper1.findEmployeeInfoById(loginId);
        return find;
    }

    @Override
    public EmployeeResponseWithoutPasswordDto updateInformation(String loginId, EmployeeInformationUpdateDto dto) {
        Employee find = employeeMapper1.findEmployeeInfoById(loginId);

        if (find == null) {
            log.info("로그인된 유저의 정보를 찾지 못함");
            return null;
        }
        String oldPassword = dto.getOldPassword();
        String newPassword = dto.getNewPassword();
        if (find.getPassword().equals(oldPassword) && !oldPassword.equals(newPassword)) {
            int affected = employeeMapper1.updatePassword(loginId, newPassword);
            if (affected != 1) {
                log.info("변경을 시도하였으나 수행 결과 변경된 컬럼이 없음");
                return null;
            }
            Employee updated = employeeMapper1.findEmployeeInfoById(loginId);
            return new EmployeeResponseWithoutPasswordDto(updated.getEmployeeId(), updated.getName(), updated.isAttendanceManager(), updated.getHireYear());
        }

        return null;
    }

    @Override
    public List<AttendanceInfoResponseDto> findAllAttendanceInfoOfMineByYearAndMonth(String loginId, Integer year, Integer month) {
        List<AttendanceInfoResponseDto> result = employeeMapper1.getAllAttendanceInfoByIdByYearByMonth(loginId, year, month);
        return result;
    }

    @Override
    public Page<List<AllVacationRequestResponseDto>> getAllRequestedInformationOfVacation(PageRequest pageRequest) {
        int page = pageRequest.getPage();
        int size = Page.PAGE_SIZE;
        String sort = pageRequest.getSort();
        String desc = pageRequest.getDesc();
        int startRow = (page - 1) * size;
        int totalElements = employeeMapper1.countAllRequestedInformationOfVacation();
        List<AllVacationRequestResponseDto> data = employeeMapper1.getAllRequestedInformationOfVacation(size, "employee."+sort, startRow, desc);
        boolean hasNext = (page * size) < totalElements;
        Page<List<AllVacationRequestResponseDto>> response = new Page<>(data, hasNext, sort, desc, page, totalElements);
        return response;
    }


    @Override
    public Page<List<AllAttendanceAppealMediateResponseDto>> getAllRequestedInformationOfAppeal(PageRequest pageRequest) {
        int page = pageRequest.getPage();
        int size = Page.PAGE_SIZE;
        String sort = pageRequest.getSort();
        String desc = pageRequest.getDesc();
        int startRow = (page - 1) * size;
        int totalElements = employeeMapper1.countAllRequestedInformationOfAppeal();
        List<AllAttendanceAppealMediateResponseDto> data = employeeMapper1.getAllRequestedInformationOfAppeal(size, "employee."+sort, startRow, desc);
        boolean hasNext = (page * size) < totalElements;
        Page<List<AllAttendanceAppealMediateResponseDto>> response = new Page<>(data, hasNext, sort, desc, page, totalElements);
        return response;
    }

    @Override
    public VacationRequestDto getSpecificVacationRequestInformation(String loginId, String identifier) {
        VacationRequestDto result = employeeMapper1.getSpecificVacationRequestInformation(loginId,identifier);
        return result;
    }

    @Override
    public List<VacationRequestResponseDto> findAllVacationRequestByEmployeeIdByYearAndByMonth(String loginId, Integer year, Integer month) {
        return employeeMapper1.findAllVacationRequestByEmployeeIdByYearAndByMonth(loginId, year, month);
    }

    public AllAttendanceAppealMediateResponseDto findAppealRequestByIdentifier(String loginId, String identifier){
        return employeeMapper1.getRequestedInformationOfAppeal(loginId,identifier);
    }
}