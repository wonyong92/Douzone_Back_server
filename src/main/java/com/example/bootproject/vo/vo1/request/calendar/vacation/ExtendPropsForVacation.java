package com.example.bootproject.vo.vo1.request.calendar.vacation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtendPropsForVacation {
    String kind = "holiday";
    Integer quantity = 1;
    String status = "requested";
    String requestId;
}
