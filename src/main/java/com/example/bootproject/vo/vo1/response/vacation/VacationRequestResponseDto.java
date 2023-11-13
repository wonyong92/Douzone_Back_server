package com.example.bootproject.vo.vo1.response.vacation;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VacationRequestResponseDto {
    private String vacationRequestKey;
    private String vacationCategoryKey;
    private String employeeId;
    private String vacationRequestStateCategoryKey;
    private Integer vacationQuantity;
    private LocalDate vacationStartDate;
    private LocalDate vacationEndDate;
    private String reason;
    private LocalDateTime vacationRequestTime;
    private String reasonForRejection;
}
