package com.example.bootproject.service.service1;

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
import com.example.bootproject.vo.vo1.response.Page;
import com.example.bootproject.vo.vo1.response.employee.EmployeeResponseDto;

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

    Page<List<EmployeeResponseDto>> getEmployeeList(int page, String sort, String desc);
}
