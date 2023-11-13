package com.example.bootproject.vo.vo1.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginRequestDto {
    @Length(min = 3, max = 10)
    String loginId;
    @Length(min = 3, max = 10)
    String password;
    String ip;
}
