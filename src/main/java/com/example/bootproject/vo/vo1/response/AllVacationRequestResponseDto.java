package com.example.bootproject.vo.vo1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllVacationRequestResponseDto {
    private String name;
    private String employeeId;
    private String vacationRequestKey;
    private String vacationCategoryKey;
    private LocalDate vacationStartDate;
    private LocalDate vacationEndDate;
    private String reason;
    private LocalDateTime vacationRequestTime;

}
