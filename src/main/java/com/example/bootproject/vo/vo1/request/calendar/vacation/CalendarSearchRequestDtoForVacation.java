package com.example.bootproject.vo.vo1.request.calendar.vacation;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CalendarSearchRequestDtoForVacation {
    @NotNull
    @Min(1990)
    @Max(2040)
    Integer year = LocalDate.now().getYear();
    @NotNull
    @Min(1)
    @Max(12)
    Integer month = LocalDate.now().getMonthValue();

}
