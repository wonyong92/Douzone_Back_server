package com.example.bootproject.service.service1;


import com.example.bootproject.vo.vo1.request.AttendanceApprovalInfoDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoEndDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoStartDto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeService1 {


    //출근기록
    AttendanceInfoStartDto startTime(String employeeId);
    //퇴근기록
    AttendanceInfoEndDto endTime(String employeeId);

    //타사원년월일
    List<AttendanceInfoDto> getAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId);

    //타사원년월
    List<AttendanceInfoDto> getAttendanceByMonthAndEmployee(LocalDate startDate, LocalDate endDate, String employeeId);

    //자신의 근태승인요청
    void approveAttendance(Long attendanceInfoId,String employeeId);

    //자신의근태이상승인내역
    List<AttendanceApprovalInfoDto> findApprovalInfoByMine(String employeeId);




}