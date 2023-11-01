package com.example.bootproject.service.service2;

import com.example.bootproject.vo.vo2.response.Page;
import com.example.bootproject.vo.vo2.response.PagingRequestDto;
import com.example.bootproject.vo.vo2.response.PagingRequestWithIdDto;
import com.example.bootproject.vo.vo2.response.VacationRequestDto;

import java.util.List;

public interface EmployeeService2 {
    public Page<List<VacationRequestDto>> getHistoryOfUsedVacationOfMine(PagingRequestWithIdDto pagingRequestWithIdDto);
    public List<VacationRequestDto> getHistoryOfRejectedVacationOfMine(String id);
    public List<VacationRequestDto> getHistoryOfUsedVacationOfEmployee(String id);
}
