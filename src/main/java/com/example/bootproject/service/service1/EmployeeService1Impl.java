package com.example.bootproject.service.service1;


import com.example.bootproject.repository.mapper.EmployeeMapper1;
import com.example.bootproject.vo.vo1.request.Attendance_Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class EmployeeService1Impl implements EmployeeService1{

        private final EmployeeMapper1 employeeMapper1;

        @Autowired
        public EmployeeService1Impl(EmployeeMapper1 employeeMapper1) {
                this.employeeMapper1 = employeeMapper1;
        }


        @Override
        public void updateStartTime(String employee_id) {
                LocalDateTime localTime = LocalDateTime.now();

                employeeMapper1.updateStartTime(employee_id,localTime);
        }

        @Override
        public void updateEndTime(String employee_id) {
                LocalDateTime localTime = LocalDateTime.now();
                employeeMapper1.updateEndTime(employee_id,localTime);
        }
}