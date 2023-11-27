package com.example.bootproject.vo.vo1.request.appeal;

import lombok.Data;

@Data
public class AppealProcessRequestDto {
    /*요청을 처리한 매니저의 id가 삽입된다*/
    String employeeId;
    Long attendanceAppealRequestId;
    String status;
    String reasonForRejection;
}
