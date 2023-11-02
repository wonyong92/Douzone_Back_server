package com.example.bootproject.service.service2;

import com.example.bootproject.vo.vo2.response.*;

import java.util.List;

public interface EmployeeService2 {
    public Page<List<VacationRequestDto>> getHistoryOfVacationOfMine (PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto);

}
