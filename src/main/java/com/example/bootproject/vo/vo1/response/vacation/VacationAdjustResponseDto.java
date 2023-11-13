package com.example.bootproject.vo.vo1.response.vacation;

import lombok.Data;

@Data
public class VacationAdjustResponseDto {
    Long vacationAdjustedHistoryId;
    String employeeId;
    String adjustQuantity;
    String reason;
    String adjustType;
}
