package com.example.bootproject.vo.vo1.request.appeal;

import lombok.Data;

@Data
public class AppealProcessRequestDto {
    String employeeId;
    Long attendanceAppealRequestId;
    String status;
    String reasonForRejection;
}
