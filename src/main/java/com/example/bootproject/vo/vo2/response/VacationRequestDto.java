package com.example.bootproject.vo.vo2.response;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
public class VacationRequestDto {
    private int vacationRequestKey; //대리키
    private String vacationCategoryKey; //연차 종류 - 키
    private String employeeId; // 사원-사원 번호
    private String vacationRequestStateCategoryKey; //연차 신청 결과
    private int vacationQuantity; // 한번에 사용한 연차 개수
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vacationStartDate; // 연차 시작 날짜
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate vacationEndDate; // 연차 끝 날짜
    private String reason; // 사유
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime vacationRequestTime; // 연차 사용 신청 시간
    private String reasonForRejection; // 반려 사유
    private String name; //사원 이름

}
