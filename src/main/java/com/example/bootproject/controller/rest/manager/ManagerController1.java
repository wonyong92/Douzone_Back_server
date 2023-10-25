package com.example.bootproject.controller.rest.manager;

import org.springframework.web.bind.annotation.*;

@RestController
public class ManagerController1 {

    @GetMapping("/manager/setting_information/work_time")
    public String settingWorkTime() {
        return "settingWorkTime";
    }

    @GetMapping("/manager/vacation/reject/{employee_id}")
    public String getHistoryOfRejectedVacationOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        return "getHistoryOfRejectedVacationOfEmployee";
    }

    @GetMapping("/manager/vacation/reject")
    public String getHistoryOfRejectedVacationOfMine() {
        return "getHistoryOfRejectedVacationOfMine";
    }

    @GetMapping("/manager/vacation/requests/{employee_id}")
    public String getRequestVacationInformationOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        return "getRequestVacationInformationOfEmployee";
    }

    @GetMapping("/manager/employees")
    public String getEmployeeList() {
        return "getEmployeeList";
    }

    @GetMapping("/manager/vacation/setting_history/vacation_default")
    public String getHistoryOfvacationDefaultSetting() {
        return "getHistoryOfvacationDefaultSetting";
    }

    @PostMapping("/manager/setting/work_time")
    public String setWorkTime() {
        return "setWorkTime";
    }

    @PostMapping("/manager/vacation/process")
    public String processVacationRequest() {
        return "processVacationRequest";
    }

    @PostMapping("/manager/vacation/modify/{employee_id}")
    public String modifyVacationQuantityOfEmployee(@PathVariable(name = "employee_id") String employeeId) {
        return "modifyVacationQuantityOfEmployee";
    }



    @PostMapping("/manager/appeal/process") //39
    public String processAppeal() {
        // 근태 이상 조정 요청 처리 로직을 여기에 구현합니다.
        // data 객체에는 폼에서 전달된 데이터가 들어있습니다.

        // 로직 처리 결과를 바탕으로 응답을 구성합니다.
        // 예를 들어, 처리가 성공했다면 상태 코드 200과 함께 성공 메시지를 반환합니다.
        return "근태이상조정요청처리";
    }

    @PostMapping("/manager/setting/vacation_default")  //40
    public String setDefaultVacation() {
        // 적용 날짜가 내일 이후인지 확인
        return "근속연수에따른 기본부여 연차갯수 설정.";
    }

}