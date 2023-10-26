package com.example.bootproject.controller.rest.employee;

import com.example.bootproject.service.service1.EmployeeService1;
import com.example.bootproject.vo.vo1.request.Attendance_Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/employee")
public class EmployeeController1 {
    @Autowired
    private EmployeeService1 employeeService1;


//    @GetMapping("/update-start-time")
//    public ResponseEntity<Void> makeAttendanceInfo(HttpSession session) {
//        String employee_id = (String) session.getAttribute("employee_id");
//        if (employee_id == null || employee_id.trim().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        employeeService1.updateStartTime(employee_id);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/attendance")
    public ResponseEntity<Void> makeAttendanceInfo() {
        String employee_id = "emp001";
        employeeService1.updateStartTime(employee_id);
        return ResponseEntity.ok().build();
    }

}