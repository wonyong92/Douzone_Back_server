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
    private int freshman; //1년차 미만 일 때의 연차 설정 개수
    private int senior; // 1년차 이상 일 때의 연차 설정 개수

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime settingTime; // 설정한 시간

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate; // 적용할 날짜
    private String employeeId; //사원번호
}
