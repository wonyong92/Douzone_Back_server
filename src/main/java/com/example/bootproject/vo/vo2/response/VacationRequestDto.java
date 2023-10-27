package com.example.bootproject.vo.vo2.response;

import lombok.Data;


import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
public class VacationRequestDto {
    private int vacation_request_key; //대리키
    private String vacation_category_key; //연차종류 - 키
    private String employee_id; // 사원-사원번호
    private String result; // 신청결과
    private int vacation_quantity; // 한번에 사용한 연차 개수
    private LocalDate vacation_start_time; // 연차 시작 날짜
    private LocalDate vacation_end_time; // 연차 끝 날짜
    private LocalTime vacation_related_start_time; // 수정 출근 시간
    private LocalTime vacation_related_end_time; // 수정 퇴근 시간
    private String reason; // 사유
    private LocalDateTime vacation_request_time; // 연차 사용 시청 시간
    private String reason_for_rejection; // 반려 사유

}
