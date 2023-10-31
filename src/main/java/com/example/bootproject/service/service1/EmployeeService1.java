package com.example.bootproject.service.service1;


import com.example.bootproject.vo.vo1.request.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeService1 {


    //출근내역
     LocalDateTime getStartTimeByEmployeeIdAndDate(String employeeId, LocalDate date);

     //퇴근내역
     LocalDateTime getEndTimeByEmployeeIdAndDate(String employeeId, LocalDate date);

     int startTime(AttendanceInfoStartDto attendanceInfoStartDto);

     int endTime(AttendanceInfoEndDto attendanceInfoEndDto);



    //퇴근기록
//    AttendanceInfoEndDto endTime(String employeeId);


    //타사원년월일
    List<AttendanceInfoDto> getAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId);

    //타사원년월
    List<AttendanceInfoDto> getAttendanceByMonthAndEmployee(LocalDate startDate, LocalDate endDate, String employeeId);

    //자신의 근태승인요청
    AttendanceApprovalDto  approveAttendance(Long attendanceInfoId,String employeeId);

    //자신의근태이상승인내역
    List<AttendanceApprovalDto> findApprovalInfoByMine(String employeeId);




}