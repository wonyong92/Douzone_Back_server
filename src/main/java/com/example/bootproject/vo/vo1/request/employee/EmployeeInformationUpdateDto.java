package com.example.bootproject.vo.vo1.request.employee;

import lombok.Data;

@Data
public class EmployeeInformationUpdateDto {
    //TODO : 암호화/복호화 라이브러리를 이용하여 비밀번호 검증을 수행하도록 수정 필요 - 개발 단계에서는 평문으로 송수신
    String oldPassword;
    String newPassword;
}
