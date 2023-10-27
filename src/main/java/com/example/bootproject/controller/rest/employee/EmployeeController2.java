package com.example.bootproject.controller.rest.employee;

import com.example.bootproject.service.service2.EmployeeService2;
import com.example.bootproject.service.service2.ManagerService2;
import com.example.bootproject.vo.vo2.response.EmployeeDto;
import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class EmployeeController2 {

    @Autowired
    private EmployeeService2 empService2;

    // 본인의 연차 사용 이력 조회 메서드
    @GetMapping("/employee/vacation/use")
    public List<VacationRequestDto> getHistoryOfUsedVacationOfMine() {
        // id값은 원래 session에서 가져와야 하나, 아직 작성 전이므로 하드코딩으로 id 지정
        String id="testid";
        return empService2.getHistoryOfUsedVacationOfMine(id);
    }

    // 본인의 반려된 연차 이력 조회 메서드
    @GetMapping("/employee/vacation/reject")
    public List<VacationRequestDto> getHistoryOfRejectedVacationOfMine() {
        // id값은 원래 session에서 가져와야 하나, 아직 작성 전이므로 하드코딩으로 id 지정
        String id="testid";
        return empService2.getHistoryOfRejectedVacationOfMine(id);
    }
}
