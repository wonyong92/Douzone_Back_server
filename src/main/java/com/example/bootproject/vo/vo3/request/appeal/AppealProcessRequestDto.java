package com.example.bootproject.vo.vo3.request.appeal;

import lombok.Data;

@Data
public class AppealProcessRequestDto {
    String employeeId;
    Long attendanceAppealRequestId;
    String status;
    String reasonForRejection;
}
