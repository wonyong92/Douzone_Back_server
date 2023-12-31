package com.example.bootproject.service.service3.api;

import com.example.bootproject.controller.rest.admin.EmployeeSearchResponseDto;
import com.example.bootproject.vo.vo1.request.PageRequest;
import com.example.bootproject.vo.vo1.response.Page;

import java.util.List;

public interface EmployeeDeleteService {
    boolean backUpDataAndDeleteEmployeeInformation(String employeeId);

    Page<List<String>> searchEmployeeNumbers(PageRequest pageRequest, String searchText);

    Page<List<String>> searchEmployeeNumbersAndEmployeeName(PageRequest pageRequest, String searchText,boolean isManager);

    Page<List<String>> getEmployeeNumbers(PageRequest pageRequest);
}
