package com.example.bootproject.controller.rest.manager;

import com.example.bootproject.service.service2.ManagerService2;
import com.example.bootproject.vo.vo2.response.EmployeeDto;
import com.example.bootproject.vo.vo2.response.SettingWorkTimeDto;
import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
public class ManagerController2 {
    @Autowired
    private ManagerService2 manService2;

    @GetMapping("/manager/vacation/requests")
    public List<VacationRequestDto> getRequestVacationInformationOfAll(@RequestParam(name = "year") int year, @RequestParam(name = "month") int month, @RequestParam(name = "day") int day) {
        String date = String.format("%04d-%02d-%02d", year, month, day);
        return manService2.getAllVacationHistory(date);
    }

    @GetMapping("/manager/vacation/requests/{employee_id}")
    public VacationRequestDto getRequestVacationInformationOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        return manService2.getEmpReqVacationHistory(employeeId);
    }

    @GetMapping("/manager/setting_history/work_time")
    public List<SettingWorkTimeDto> settingWorkTime() {
        return manService2.getSettingWorkTime();
    }


}
