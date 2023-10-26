package com.example.bootproject.vo.vo2.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class VacationQuantitySettingDto {
    private int settind_key;
    private int freshman;
    private int senior;
    private LocalDateTime setting_time;
}
