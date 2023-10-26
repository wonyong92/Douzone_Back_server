package com.example.bootproject.controller.rest.employee;

import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController1 {


/*    @GetMapping("/employee/attendance_info")
    public String getAttendanceInfoOfMineByDay(@RequestParam(name = "year") int year, @RequestParam(name = "month") int month, @RequestParam(name = "day") int day) {
//        @GetMapping("/employee/attendence_info")
//        public String getAttendanceInfoOfMineByMonth(@RequestParam(name = "year") int year, @RequestParam(name = "month") int month) {
//            return "getAttendanceInfoOfEmployeeByMonth";
//        }
        return "makeAttendanceInfoOfMine";
    }


    @GetMapping("/employee/attendance_info/{employee_id}")
    public String getAttendanceInfoOfEmployeeByDay(@PathVariable(name = "employee_id") String employeeId, @RequestParam(name = "year") int year, @RequestParam(name = "month") int month, @RequestParam(name = "day") int day) {
//        @GetMapping("/employee/attendence_info/{employee_id}")
//        public String getAttendanceInfoOfEmployeeByMonth(@PathVariable(name = "employee_id") String employeeId, @RequestParam(name = "year") int year, @RequestParam(name = "month") int month) {
//            return "getAttendanceInfoOfEmployeeByMonth";
//        }
        return "getAttendanceInfoOfEmployeeByMonth";
    }

    @GetMapping("/employee/information/{employee_id}")
    public String getInformationOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        return "getInformationOfEmployee";
    }

    @GetMapping("/employee/information")
    public String getInformationOfMine() {
        return "getInformationOfMine";
    }

    @PostMapping("/employee/information")
    public String modifyEmployeeInformationOfMine() {
        return "modifyEmployeeInformationOfMine";
    }

    // 기본 기능

    @PostMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/employee/leave")
    public String makeLeaveInformation() {
        return "makeLeaveInformation";
    }


    @GetMapping("/employee/attendance")
    public String makeAttendanceInfo() {
        return "makeAttendanceInfo";
    }

    //승인
    @GetMapping("/employee/approve/{employee_id}")
    public String getHistoryOfApproveOfEmployee(@PathVariable(name = "employee_id") String employeeId, @RequestParam(name = "year") int year, @RequestParam(name = "month") int month, @RequestParam(name = "day") int day) {
        return "getHistoryOfApproveOfEmployee";
    }

    @GetMapping("/employee/approves")
    public String getHistoryOfApproveOfMine(@RequestParam(name = "year") int year, @RequestParam(name = "month") int month, @RequestParam(name = "day") int day) {
        return "getHistoryOfApproveOfMine";
    }

    @PostMapping("/employee/approve")
    public String makeApproveRequest() {
        return "makeApproveRequest";
    }

    //연차
    @GetMapping("/employee/vacation/use/{employee_id}")
    public String getHistoryOfUsedVacationOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        return "getHistoryOfUsedVacationOfEmployee";
    }

    @GetMapping("/employee/vacation/use")
    public String getHistoryOfUsedVacationOfMine() {
        return "getHistoryOfUsedVacationOfEmployee";
    }

    @GetMapping("/employee/vacation/remain/{employee_id}")
    public String getRemainOfVacationOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        return "getRemainOfVacation";
    }

    @GetMapping("/employee/vacation/remain")
    public String getRemainOfVacationOfMine() {
        return "getRemainOfVacationOfMine";
    }

    @GetMapping("/employee/vacation/requests")
    public String getRequestVacationInformationOfAll(@RequestParam(name = "year") int year, @RequestParam(name = "month") int month, @RequestParam(name = "day") int day) {
        return "getRequestVacationInformationOfAll";
    }

    @PostMapping("/employee/vacation")
    public String requestVacation() {
        return "requestVacation";
    }


    //조정

    @PostMapping("/employee/appeal")
    public String makeAppealRequest() {
        return "makeAppealRequest";
    }

    @PostMapping("/employee/appeal/requests")
    public String getAppealRequestHistoryOfMine() {
        return "getAppealRequestHistoryOfMine";
    }*/

}