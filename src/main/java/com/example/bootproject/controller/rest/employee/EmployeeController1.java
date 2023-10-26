package com.example.bootproject.controller.rest.employee;
import com.example.bootproject.service.service1.EmployeeService1;
import com.example.bootproject.vo.vo1.request.Attendance_Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController1 {

    private final EmployeeService1 employeeService1;

    @Autowired
    public EmployeeController1(EmployeeService1 employeeService1) {
        this.employeeService1 = employeeService1;
    }


    //세션로그인 예상
//    @GetMapping("/attendance")
//    public ResponseEntity<Void> makeAttendanceInfo(HttpSession session) {
//        String employee_id = (String) session.getAttribute("employee_id");
//        if (employee_id == null || employee_id.trim().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        employeeService1.updateStartTime(employee_id);
//        return ResponseEntity.ok().build();
//    }

    //세션로그인 예상
//    @GetMapping("/leave")
//    public ResponseEntity<Void> makeLeaveInformation(HttpSession session){
//        String employeeId = (String) session.getAttribute("employee_id");
//        if (employeeId == null || employeeId.trim().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        employeeService1.updateEndTime(employeeId);
//        return ResponseEntity.ok().build();
//    }

    //출근기록
    @GetMapping("/attendance")
    public ResponseEntity<Void> makeAttendanceInfo() {
        String employee_id = "emp001";
        employeeService1.updateStartTime(employee_id);
        return ResponseEntity.ok().build();
    }

    //퇴근기록
    @GetMapping("/leave")
    public ResponseEntity<Void> makeLeaveInformation(){
        String employee_id = "emp02";
        employeeService1.updateEndTime(employee_id);
        return ResponseEntity.ok().build();
    }


    //년월일 타사원정보검색 년월만 입력하면 년월만 입력데이터 적용되게 구현
    @GetMapping("/attendance_info/{employee_id}/")
    public ResponseEntity<List<Attendance_Info>> getAttendanceInfoOfEmployeeByDay(
            @PathVariable("employee_id") String employeeId,
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            @RequestParam(value = "day", required = false) Integer day) {

        if (day != null) {
            // 일 데이터가 있으면 해당 날짜로 검색
            LocalDate attendanceDate = LocalDate.of(year, month, day);
            List<Attendance_Info> attendanceInfo = employeeService1.getAttendanceByDateAndEmployee(attendanceDate, employeeId);
            return ResponseEntity.ok(attendanceInfo);
        } else {
            // 일 데이터가 없으면 해당 월로 검색
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);
            List<Attendance_Info> attendanceInfo = employeeService1.getAttendanceByMonthAndEmployee(startDate, endDate, employeeId);
            return ResponseEntity.ok(attendanceInfo);
        }
    }

}