package com.example.bootproject.vo.vo3.request.vacation;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class VacationRequestDto {
    String vacationCategoryKey;
    String employeeId;
    String vacationRequestStateCategoryKey;
    Integer vacationQuantity;

    String reason;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate vacationStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate vacationEndDate;
}
