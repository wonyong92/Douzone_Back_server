package com.example.bootproject.service.service1;


import com.example.bootproject.entity.Employee;
import com.example.bootproject.vo.vo1.request.AttendanceInfoEndRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoStartRequestDto;
import com.example.bootproject.vo.vo1.request.PageRequest;
import com.example.bootproject.vo.vo1.request.PagedLocalDateDto;
import com.example.bootproject.vo.vo1.request.employee.EmployeeInformationUpdateDto;
import com.example.bootproject.vo.vo1.response.*;
import com.example.bootproject.vo.vo1.response.employee.EmployeeResponseDto;
import com.example.bootproject.vo.vo1.response.vacation.VacationRequestResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {


    //출근요청
    AttendanceInfoResponseDto makeStartRequest(AttendanceInfoStartRequestDto dto);


    //퇴근요청
    AttendanceInfoResponseDto makeEndRequest(AttendanceInfoEndRequestDto dto, String employeeId);


    //타사원 근태 정보 년월일검색
    Page<List<AttendanceInfoResponseDto>> getAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId, String sort, String desc,
                                                                         int page);

    //사원 근태 정보 년월일검색
    Page<List<AttendanceInfoResponseDto>> getAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId, String sort, String desc,
                                                                         int page, int size);

    //사원년월검색
    Page<List<AttendanceInfoResponseDto>> getAttendanceByMonthAndEmployee(int year, int month, String employeeId, int page,
                                                                          String sort, String desc, int size);

    Page<List<AttendanceInfoResponseDto>> getAttendanceByMonthAndEmployee(int year, int month, String employeeId, int page,
                                                                          String sort, String desc);


    //자신의 근태승인요청
    AttendanceApprovalResponseDto approveAttendance(String employeeId, Long attendanceInfoId);


    //자신의근태이상승인내역
    Page<List<AttendanceApprovalUpdateResponseDto>> getApprovalInfoByEmployeeId(String employeeId, int page, String sort, String desc);


    //자신의조정요청이력조회
    Page<List<AttendanceAppealMediateResponseDto>> findAppealRequestOfMine(String employeeId, int page, String sort, String desc);


    //사원이름이나사번검색
    List<EmployeeSearchResponseDto> searchByEmployeeIdOrName(String searchParameter);

    boolean employeeExists(String employeeId);

    Page<List<VacationRequestDto>> getHistoryOfVacationOfMine(PagedLocalDateDto dto, String employeeId, String status);

    int getRemainOfVacationOfMine(String id);

    int getRemainOfVacationOfMineForRequest(String id);

    Employee findEmployeeInfoById(String loginId);

    EmployeeResponseDto updateInformation(String loginId, EmployeeInformationUpdateDto dto);


    List<AttendanceInfoResponseDto> findAllAttendanceInfoOfMineByYearAndMonth(String loginId, Integer year, Integer month);

    Page<List<AllVacationRequestResponseDto>> getAllRequestedInformationOfVacation(PageRequest pagedLocalDateDto);

    Page<List<AllAttendanceAppealMediateResponseDto>> getAllRequestedInformationOfAppeal(PageRequest pageRequest);

    List<VacationRequestResponseDto> findAllVacationRequestByEmployeeIdByYearAndByMonth(String loginId, Integer year, Integer month);
}