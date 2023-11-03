package com.example.bootproject.vo.vo2.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DefaultVacationRequestDto {
    private int freshman; //1년 미만일 때 개수
    private int senior; //1년 이상일 때 개수

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate; // 대상 날짜

    @JsonIgnore
    private String employeeId; // 작성 근태 담당자의 사원 번호

    private int settingKey; // generated key 저장


}
