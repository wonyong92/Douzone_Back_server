package com.example.bootproject.vo.vo1.request;

import lombok.Data;
import lombok.NoArgsConstructor;


//근태정보 --승인 테이블에 승인 내역을남기는 dto
@Data
@NoArgsConstructor
public class Attendance_Approval {
    private Long attendanceInfoId;

    private String employeeId;

}
