package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo3.request.appeal.AppealProcessRequestDto;
import com.example.bootproject.vo.vo3.request.vacation.VacationProcessRequestDto;
import com.example.bootproject.vo.vo3.request.vacation.VacationRequestDto;
import com.example.bootproject.vo.vo3.response.appeal.AppealRequestResponseDto;
import com.example.bootproject.vo.vo3.response.vacation.VacationProcessResponseDto;
import com.example.bootproject.vo.vo3.response.vacation.VacationRequestResponseDto;

public interface VacationService {
    VacationRequestResponseDto makeVacationRequest(VacationRequestDto dto);

    VacationRequestResponseDto processVacationRequest(VacationProcessRequestDto dto);
}
