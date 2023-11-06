package com.example.bootproject.vo.vo1.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceApprovalUpdateRequestDto {

    private String attendanceStatusCategory;

    private Long attendanceInfoId;


}
