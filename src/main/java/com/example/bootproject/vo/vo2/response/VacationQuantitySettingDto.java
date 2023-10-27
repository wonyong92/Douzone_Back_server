package com.example.bootproject.vo.vo2.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class VacationQuantitySettingDto {
    private int settind_key; //대리키
    private int freshman; //1년 미만 - 개수
    private int senior; //1년 이상 - 개수
    private LocalDateTime setting_time; // 설정 시간
    private LocalDate target_date; // 적용 날짜
    private String employee_id; //사원-사원번호
}
