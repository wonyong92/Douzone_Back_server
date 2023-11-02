package com.example.bootproject.vo.vo2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DefaultVacationResponseDto {
    private int settingKey;
    private int freshman;
    private int senior;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime settingTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    private String employeeId;

    public DefaultVacationResponseDto(int freshman) {
        this.freshman = freshman;
    }
}
