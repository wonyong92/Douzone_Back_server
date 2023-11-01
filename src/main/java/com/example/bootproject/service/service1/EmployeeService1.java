package com.example.bootproject.service.service1;


import com.example.bootproject.vo.vo1.request.*;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;


import java.time.LocalDate;
import java.util.List;

public interface EmployeeService1 {


    //출근내역
    AttendanceInfoResponseDto makeStartResponse(AttendanceInfoStartRequestDto dto, String employeeId);



//    AttendanceInfoEndRequestDto endTime(String employeeId);


    //타사원년월일
    List<AttendanceInfoRequestDto> getAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId);

    //타사원년월
    List<AttendanceInfoRequestDto> getAttendanceByMonthAndEmployee(LocalDate startDate, LocalDate endDate, String employeeId);

    //자신의 근태승인요청
    AttendanceApprovalRequestDto approveAttendance(Long attendanceInfoId, String employeeId);

    //자신의근태이상승인내역
    List<AttendanceApprovalRequestDto> findApprovalInfoByMine(String employeeId);




}