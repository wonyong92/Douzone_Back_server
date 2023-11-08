package com.example.bootproject.vo.vo3.request.vacation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class VacationRequestDto {
    Long vacationRequestKey = null;
    String vacationCategoryKey;
    @JsonIgnore
    String employeeId;
    String vacationRequestStateCategoryKey;
    Integer vacationQuantity;

    String reason;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate vacationStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate vacationEndDate;
}
