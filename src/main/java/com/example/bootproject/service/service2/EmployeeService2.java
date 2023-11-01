package com.example.bootproject.service.service2;

import com.example.bootproject.vo.vo2.response.Page;
import com.example.bootproject.vo.vo2.response.PagingRequestDto;
import com.example.bootproject.vo.vo2.response.PagingRequestWithIdDto;
import com.example.bootproject.vo.vo2.response.VacationRequestDto;

import java.util.List;

public interface EmployeeService2 {
    public Page<List<VacationRequestDto>> getHistoryOfUsedVacationOfMine(PagingRequestWithIdDto pagingRequestWithIdDto);
    public Page<List<VacationRequestDto>> getHistoryOfRejectedVacationOfMine(PagingRequestWithIdDto pagingRequestWithIdDto);
    public Page<List<VacationRequestDto>> getHistoryOfUsedVacationOfEmployee(PagingRequestWithIdDto pagingRequestWithIdDto);
}
