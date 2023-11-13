package com.example.bootproject.service.service1;


import com.example.bootproject.entity.Employee;
import com.example.bootproject.vo.vo1.request.AttendanceInfoEndRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoStartRequestDto;
import com.example.bootproject.vo.vo1.response.*;
import com.example.bootproject.vo.vo1.request.PagingRequestWithIdStatusDto;
import com.example.bootproject.vo.vo1.response.VacationRequestDto;
import com.example.bootproject.vo.vo1.request.employee.EmployeeInformationUpdateDto;
import com.example.bootproject.vo.vo1.response.Page;
import com.example.bootproject.vo.vo1.response.employee.EmployeeResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService1 {


    //출근요청
    AttendanceInfoResponseDto makeStartResponse(AttendanceInfoStartRequestDto dto);


    //퇴근요청
    AttendanceInfoResponseDto makeEndResponse(AttendanceInfoEndRequestDto dto, String employeeId);


    //사원년월일검색
    Page<List<AttendanceInfoResponseDto>> getAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId, String sort, String desc,
                                                                         int page);

    //사원년월검색
    Page<List<AttendanceInfoResponseDto>> getAttendanceByMonthAndEmployee(int year, int month, String employeeId, int page,
                                                                          String sort, String desc);


    //자신의 근태승인요청
    AttendanceApprovalResponseDto approveAttendance(String employeeId, Long attendanceInfoId);


    //자신의근태이상승인내역
    Page<List<AttendanceApprovalUpdateResponseDto>> getApprovalInfoByEmployeeId(String employeeId, int page, String sort, String desc);


    //자신의조정요청이력조회
    Page<List<AttendanceAppealMediateResponseDto>> findAttendanceInfoByMine(String employeeId, int page, String sort, String desc);


    //사원이름이나사번검색
    List<EmployeeSearchResponseDto> searchByEmployeeIdOrName(String searchParameter);

    boolean employeeExists(String employeeId);

    Page<List<VacationRequestDto>> getHistoryOfVacationOfMine(PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto);

    int getRemainOfVacationOfMine(String id);

    int getRemainOfVacationOfMineForRequest(String id);

    Employee findEmployeeInfoById(String loginId);

    EmployeeResponseDto updateInformation(String loginId, EmployeeInformationUpdateDto dto);

}