package com.example.bootproject.controller.rest.manager;

import com.example.bootproject.service.service2.ManagerService2;
import com.example.bootproject.vo.vo2.response.EmployeeDto;

import com.example.bootproject.vo.vo2.response.SettingWorkTimeDto;

import com.example.bootproject.vo.vo2.response.VacationQuantitySettingDto;

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

    //전체 연차 요청 정보 조회 메서드
    @GetMapping("/manager/vacation/requests")
    public List<VacationRequestDto> getRequestVacationInformationOfAll(@RequestParam(name = "year") int year, @RequestParam(name = "month") int month, @RequestParam(name = "day") int day) {
        //year, month, day를 쿼리 파라미터로 받아옴
        //받아온 날짜 데이터를 'year-month-day' 형태의 문자열로 변환
        String date = String.format("%04d-%02d-%02d", year, month, day);
        return manService2.getAllVacationHistory(date);
    }

    //타 사원의 연차 요청 정보 조회 메서드
    @GetMapping("/manager/vacation/requests/{employee_id}")
    public VacationRequestDto getRequestVacationInformationOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        // 경로 변수로 사원의 사원 번호 데이터를 전달받음
        return manService2.getEmpReqVacationHistory(employeeId);
    }

    // 정규 출/퇴근 시간 설정 내역 확인 메서드
    @GetMapping("/manager/setting_history/work_time")
    public List<SettingWorkTimeDto> settingWorkTime() {
        return manService2.getSettingWorkTime();
    }

    //근속 연수에 따른 기본 부여 연차 개수 설정 내역 확인 메서드
    @GetMapping("/manager/vacation/setting_history/vacation_default")
    public List<VacationQuantitySettingDto>  getHistoryOfvacationDefaultSetting() {
        return manService2.getVacationSettingHistory();
    }


}
