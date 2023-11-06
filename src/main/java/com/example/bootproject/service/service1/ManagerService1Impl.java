package com.example.bootproject.service.service1;


import com.example.bootproject.repository.mapper1.ManagerMapper1;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryRequestDto;
import com.example.bootproject.vo.vo1.response.AttendanceAppealMediateResponseDto;
import com.example.bootproject.vo.vo1.response.AttendanceApprovalUpdateResponseDto;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import com.example.bootproject.vo.vo1.response.RegularTimeAdjustmentHistoryResponseDto;
import com.example.bootproject.vo.vo3.response.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    // 정규 출퇴근 시간 설정
    @Override
// 정규 출퇴근 시간 설정. 사원 ID는 컨트롤러에서 받아서 사용한다.
    public RegularTimeAdjustmentHistoryResponseDto insertRegularTimeAdjustmentHistory(
            RegularTimeAdjustmentHistoryRequestDto dto, String employeeId) {

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

        RegularTimeAdjustmentHistoryResponseDto responseDto =
                managerMapper1.selectregulartimeadjustment(employeeId);

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


/*
- 사원 ID, 페이지 번호, 정렬 기준, 정렬 방향을 인자로 받는다.
- 로그를 남기면서 근태 승인 정보를 조회한다.
- 데이터베이스에서 근태 승인 정보를 페이징 처리하여 조회한다.
- 조회 결과를 Page 객체에 담아 반환한다.
- 데이터는 List<AttendanceApprovalUpdateResponseDto> 타입으로 반환된다.
*/

    @Override
    public Page<List<AttendanceApprovalUpdateResponseDto>> managerGetApprovalInfoByEmployeeId(
            String employeeId, int page, String sort, String desc) {

        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }
        int size = Page.PAGE_SIZE;
        int startRow = (page - 1) * size;
        log.info("사원 ID {}에 대한 승인 정보 페이지: {}, 정렬: {}, 방향: {}", employeeId, page, sort, desc);

        // 데이터베이스에서 데이터 조회
        List<AttendanceApprovalUpdateResponseDto> data =
                managerMapper1.getAllEmployeeByEmployeeId(employeeId, sort, desc, size, startRow);
        int totalElements = managerMapper1.countApprovalInfoByEmployeeId(employeeId);

        // 다음 페이지 존재 여부 계산
        boolean hasNext = (page * size) < totalElements;

        // Page 객체 생성. 여기서 data는 List<AttendanceApprovalUpdateResponseDto>입니다.
        Page<List<AttendanceApprovalUpdateResponseDto>> result = new Page<>(
                data, // 바로 data를 넘김. List<T> 타입을 만족함.
                !hasNext, // 다음 페이지가 없으면 isLastPage는 true임.
                sort, desc, page, totalElements);

        return result;
    }

/*
- 사원 ID, 페이지 번호, 정렬 기준, 정렬 방향을 인자로 받는다.
- 조정 요청 이력을 페이징 처리하여 조회한다.
- 조회 결과를 Page 객체에 담아 반환한다.
- 로그를 남겨 조회 정보를 기록한다.
*/

    //타사원의 조정요청 이력조회
    @Override
    public Page<List<AttendanceAppealMediateResponseDto>> managerfindAttendanceInfoByMine(
            String employeeId, int page, String sort, String desc) {

        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }
        int size = Page.PAGE_SIZE;
        int startRow = (page - 1) * size;

        List<AttendanceAppealMediateResponseDto> data =
                managerMapper1.managerfindAttendanceAppealByEmployeeId(employeeId, sort, desc, size, startRow);
        int totalElements = managerMapper1.countAttendanceAppealByEmployeeId(employeeId);
        boolean hasNext = (page * size) < totalElements;

        if (totalElements <= 0) {
            // 근태 이의 신청이 없을 때 처리할 로직
            log.error("사원 ID {}에 대한 조정요청이 없습니다.", employeeId);
            return null; // 또는 적절한 예외 처리를 할 수 있습니다.
        }


        // 로그 남김
        log.info("사원 ID {}의 조정 요청 이력 수: {}", employeeId, totalElements);

        Page<List<AttendanceAppealMediateResponseDto>> result = new Page<>(
                data, // 바로 data를 넘김. List<T> 타입을 만족함.
                !hasNext, // 다음 페이지가 없으면 isLastPage는 true임.
                sort, desc, page, totalElements);

        return result;
    }

    //        사원 년,월,일 사원근태정보검색
    @Override
    public Page<List<AttendanceInfoResponseDto>> getmanagerAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId, String sort, String desc,
                                                                                       int page, int year, int month) {

        int size = Page.PAGE_SIZE;
        int startRow = (page - 1) * size;

        List<AttendanceInfoResponseDto> data =
                managerMapper1.selectmanagerAttendanceByDate(attendanceDate, employeeId, sort, desc, size, startRow);
        int totalElements = managerMapper1.managercountAttendanceInfoByEmployeeId(employeeId, year, month);
        boolean hasNext = (page * size) < totalElements;

        if (totalElements <= 0) {
            // 근태 이의 신청이 없을 때 처리할 로직
            log.error("사원 ID {}에 대한 조정요청이 없습니다.", employeeId);
            return null; // 또는 적절한 예외 처리를 할 수 있습니다.
        }

        log.info("사원 ID {}의 조정 요청 이력 수: {}", employeeId, totalElements);

        Page<List<AttendanceInfoResponseDto>> result = new Page<>(
                data,
                !hasNext,
                sort, desc, page, totalElements);

        return result;
    }

    //사원 년,월 사원근태정보검색
    @Override
    public Page<List<AttendanceInfoResponseDto>> getmanagerAttendanceByMonthAndEmployee(int year, int month, String employeeId, int page,
                                                                                        String sort, String desc) {
        if (!employeeExists(employeeId)) {
            log.info("이 아이디가 존재하지 않는다: {}", employeeId);
            return null;
        }

        int size = Page.PAGE_SIZE;
        int startRow = (page - 1) * size;
        List<AttendanceInfoResponseDto> data =
                managerMapper1.selectmanagerAttendanceByMonthAndEmployee(year, month, employeeId, sort, desc, size, startRow);
        int totalElements = managerMapper1.managercountAttendanceInfoByEmployeeId(employeeId, year, month);
        boolean hasNext = (page * size) < totalElements;

        if (totalElements <= 0) {
            // 근태 이의 신청이 없을 때 처리할 로직
            log.error("사원 ID {}에 대한 조정요청이 없습니다.", employeeId);
            return null; // 또는 적절한 예외 처리를 할 수 있습니다.
        }

        Page<List<AttendanceInfoResponseDto>> result = new Page<>(
                data, // 바로 data를 넘김. List<T> 타입을 만족함.
                !hasNext, // 다음 페이지가 없으면 isLastPage는 true임.
                sort, desc, page, totalElements);

        return result;
    }

    @Override
    public boolean employeeExists(String employeeId) {
        return managerMapper1.existsById(employeeId);
    }

}
