package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo3.request.vacation.VacationRequestDto;
import com.example.bootproject.vo.vo3.response.vacation.VacationRequestResponseDto;

public interface VacationService {
    VacationRequestResponseDto makeVacationRequest(VacationRequestDto dto);
}
