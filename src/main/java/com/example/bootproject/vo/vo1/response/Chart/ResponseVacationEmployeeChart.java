package com.example.bootproject.vo.vo1.response.Chart;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseVacationEmployeeChart {

    //총 연차 갯수
    private int totalVacation;

    //사용 연차 갯수
    private int useVacation;
}
