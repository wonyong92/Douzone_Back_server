package com.example.bootproject.vo.vo1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VacationResponseDto {
    private String vacationRequestStateCategoryKey; //연차 신청 결과
    private String employeeId; // 사원-사원 번호
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
