package com.example.bootproject.vo.vo1.request.vacation;

import lombok.Data;

@Data
public class VacationProcessRequestDto {
    String employeeId;
    Long vacationRequestKey;
    String vacationRequestStateCategoryKey;
    String reasonForRejection;
}
