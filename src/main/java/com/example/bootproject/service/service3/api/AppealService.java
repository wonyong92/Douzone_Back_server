package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo3.request.appeal.AppealRequestDto;
import com.example.bootproject.vo.vo3.request.vacation.VacationRequestDto;
import com.example.bootproject.vo.vo3.response.appeal.AppealRequestResponseDto;
import com.example.bootproject.vo.vo3.response.vacation.VacationRequestResponseDto;

public interface AppealService {
    AppealRequestResponseDto makeAppealRequest(AppealRequestDto dto);
}
