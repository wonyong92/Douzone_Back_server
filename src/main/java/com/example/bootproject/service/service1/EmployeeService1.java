package com.example.bootproject.service.service1;

import com.example.bootproject.repository.mapper.EmployeeMapper1;
import com.example.bootproject.vo.vo1.request.Attendance_Info;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.util.Date;

public interface EmployeeService1 {



    void updateStartTime(String employee_id);

    void updateEndTime(String employee_id );
}



