package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo3.request.appeal.AppealProcessRequestDto;
import com.example.bootproject.vo.vo3.request.appeal.AppealRequestDto;
import com.example.bootproject.vo.vo3.response.appeal.AppealRequestResponseDto;

public interface AppealService {
    AppealRequestResponseDto makeAppealRequest(AppealRequestDto dto);

    AppealRequestResponseDto processAppealRequest(AppealProcessRequestDto dto);
}
