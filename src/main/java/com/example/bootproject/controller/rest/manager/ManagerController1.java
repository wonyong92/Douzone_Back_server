package com.example.bootproject.controller.rest.manager;

import com.example.bootproject.repository.mapper.ManagerMapper1;
import com.example.bootproject.service.service1.ManagerService1;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/manager")
public class ManagerController1 {

    private final ManagerMapper1 managerMapper1;

    private final ManagerService1 managerService1;

    @Autowired
    public ManagerController1(ManagerMapper1 managerMapper1, ManagerService1 managerService1) {
        this.managerMapper1 = managerMapper1;
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
}