package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo1.request.vacation.VacationAdjustRequestDto;
import com.example.bootproject.vo.vo1.request.vacation.VacationProcessRequestDto;
import com.example.bootproject.vo.vo1.request.vacation.VacationRequestDto;
import com.example.bootproject.vo.vo1.response.vacation.VacationAdjustResponseDto;
import com.example.bootproject.vo.vo1.response.vacation.VacationRequestResponseDto;

public interface VacationService {
    VacationRequestResponseDto makeVacationRequest(VacationRequestDto dto);

    VacationRequestResponseDto processVacationRequest(VacationProcessRequestDto dto);

    VacationAdjustResponseDto modifyVacationOfEmployee(VacationAdjustRequestDto dto, String employeeId);
}
