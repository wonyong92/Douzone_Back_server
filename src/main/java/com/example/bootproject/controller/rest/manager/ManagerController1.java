package com.example.bootproject.controller.rest.manager;

import com.example.bootproject.repository.mapper.ManagerMapper1;
import com.example.bootproject.service.service1.ManagerService1;
import com.example.bootproject.vo.vo1.request.AttendanceApprovalInfoDto;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/manager")
public class ManagerController1 {



    private final ManagerService1 managerService1;

    @Autowired
    public ManagerController1( ManagerService1 managerService1) {
        this.managerService1 = managerService1;
    }


    //정규출퇴근시간 설정
    @PostMapping("/adjustment")
    public ResponseEntity<RegularTimeAdjustmentHistoryDto> setWorkTime(
            @RequestParam String employee_id,
            @RequestBody RegularTimeAdjustmentHistoryDto request) {
        try {
            RegularTimeAdjustmentHistoryDto result = managerService1.insertRegularTimeAdjustmentHistory(request, employee_id);
            return ResponseEntity.ok(result);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //타사원에 대한 근태 이상 승인 내역
    @GetMapping("approve/{employeeId}")
    public ResponseEntity<List<AttendanceApprovalInfoDto>> getApprovalInfo(
            @PathVariable String employeeId) {
        List<AttendanceApprovalInfoDto> approvalInfo = managerService1.getAttendanceApprovalInfoDto(employeeId);
        return new ResponseEntity<>(approvalInfo, HttpStatus.OK);
    }

}