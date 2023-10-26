package com.example.bootproject.service.service1;


import com.example.bootproject.vo.vo1.request.Attendance_Info;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EmployeeService1 {


    //출근
    void updateStartTime(String employee_id);

    //퇴근
    void updateEndTime(String employee_id);

    //타사원년월일
    List<Attendance_Info> getAttendanceByDateAndEmployee(LocalDate attendance_date, String employee_id);

    //타사원월일
    List<Attendance_Info> getAttendanceByMonthAndEmployee(LocalDate startDate, LocalDate endDate, String employeeId);

}