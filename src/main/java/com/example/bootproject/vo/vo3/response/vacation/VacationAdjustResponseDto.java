package com.example.bootproject.vo.vo3.response.vacation;

import lombok.Data;

@Data
public class VacationAdjustResponseDto {
    Long vacationAdjustedHistoryId;
    String employeeId;
    String adjustQuantity;
    String reason;
    String adjustType;
}
