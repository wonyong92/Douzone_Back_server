package com.example.bootproject.service.service1;


import com.example.bootproject.vo.vo1.request.AttendanceInfoDto;


import java.time.LocalDate;
import java.util.List;

public interface EmployeeService1 {


    //출근
    void updateStartTime(String employee_id);

    //퇴근
    void updateEndTime(String employee_id);

    //타사원년월일
    List<AttendanceInfoDto> getAttendanceByDateAndEmployee(LocalDate attendance_date, String employee_id);

    //타사원월일
    List<AttendanceInfoDto> getAttendanceByMonthAndEmployee(LocalDate startDate, LocalDate endDate, String employeeId);

}