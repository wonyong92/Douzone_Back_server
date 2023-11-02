package com.example.bootproject.vo.vo1.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class AttendanceApprovalUpdateRequestDto {

    private String attendanceStatusCategory;

   private Long attendanceInfoId;



}
