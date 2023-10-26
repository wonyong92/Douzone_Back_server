package com.example.bootproject.service.service1;


import com.example.bootproject.repository.mapper.EmployeeMapper1;
import com.example.bootproject.vo.vo1.request.Attendance_Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeService1Impl implements EmployeeService1{

        private final EmployeeMapper1 employeeMapper1;

        @Autowired
        public EmployeeService1Impl(EmployeeMapper1 employeeMapper1) {
                this.employeeMapper1 = employeeMapper1;
        }


        //출근기록
        @Override
        public void updateStartTime(String employee_id) {
                LocalDateTime localTime = LocalDateTime.now();

                employeeMapper1.updateStartTime(employee_id,localTime);
        }

        //퇴근기록
        @Override
        public void updateEndTime(String employee_id) {
                LocalDateTime localTime = LocalDateTime.now();
                employeeMapper1.updateEndTime(employee_id,localTime);
        }


        //사원 년,월,일 사원근태정보검색
        @Override
        public List<Attendance_Info> getAttendanceByDateAndEmployee(LocalDate attendance_date, String employee_id) {
                return employeeMapper1.selectAttendanceByDate(attendance_date,employee_id);
        }

        //사원 년,월 사원근태정보검색
        @Override
        public List<Attendance_Info> getAttendanceByMonthAndEmployee(LocalDate startDate, LocalDate endDate, String employeeId) {
                return employeeMapper1.selectAttendanceByMonthAndEmployee(startDate,endDate,employeeId);
        }


}