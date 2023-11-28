package com.example.bootproject.service.service1;

import com.example.bootproject.vo.vo1.request.*;
import com.example.bootproject.vo.vo1.response.*;
import com.example.bootproject.vo.vo1.response.employee.EmployeeResponseDto;
import com.example.bootproject.vo.vo1.response.vacation.PagingRequsetWithDateSearchDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;


public interface ManagerService1 {

    //사원의 id와 근태관리자여부 true/false를 담음


    //정규출퇴근시간 설정 사원아이디는 위에 있는 사원아이디를 담기위해 작성
    RegularTimeAdjustmentHistoryResponseDto insertRegularTimeAdjustmentHistory
    (RegularTimeAdjustmentHistoryRequestDto dto, String employeeId);

    //타사원근태이상승인내역
    Page<List<AttendanceApprovalUpdateResponseDto>> managerGetApprovalInfoByEmployeeId(String employeeId, int page, String sort, String desc);


    //타사원의 조정요청내역
    Page<List<AttendanceAppealMediateResponseDto>> managerfindAttendanceInfoByMine(String employeeId, int page, String sort, String desc);

    Page<List<AttendanceInfoResponseDto>> getmanagerAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId, String sort, String desc,
                                                                                int page, int year, int month);

    //사원년월검색
    Page<List<AttendanceInfoResponseDto>> getmanagerAttendanceByMonthAndEmployee(int year, int month, String employeeId, int page,
                                                                                 String sort, String desc);

    boolean employeeExists(String employeeId);

    Page<List<VacationRequestDto>> getAllVacationHistory(PagingRequestWithDateDto pagingRequestWithDateDto);

    Page<List<VacationRequestDto>> getHistoryVacationOfEmployee(PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto);

    Page<List<SettingWorkTimeDto>> getSettingWorkTime(PagingRequestDto pagingRequestDto);

    Page<List<VacationQuantitySettingDto>> getVacationSettingHistory(PagingRequestDto pagingRequestDto);

    DefaultVacationResponseDto makeDefaultVacationResponse(DefaultVacationRequestDto dto);

    int getDefaultSettingValue(String employeeId);

    int getEmployeeCheck(String id);

    Page<List<VacationResponseDto>> getVacationHistory(PagingRequsetWithDateSearchDto requestDto);


    Page<List<EmployeeResponseDto>> getEmployeeList(int currentPage, String sortColumn, String descCheck);

    int getVacationDefaultLatestCount(String info);

    Page<List<AttendanceAppealHistory>> getAttendanceHistory(PagingRequsetWithDateSearchDto requestDto);

    List<AllAttendanceAppealMediateResponseDto> searchAppealAllRequestedByIdOrNumber(@RequestParam String searchParameter);

    List<AllVacationRequestResponseDto> searchAllVacationRequestedByIdOrNumber(@RequestParam String searchParameter);
}
