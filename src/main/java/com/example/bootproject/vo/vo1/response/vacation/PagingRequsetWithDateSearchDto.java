package com.example.bootproject.vo.vo1.response.vacation;

import com.example.bootproject.vo.vo1.response.Page;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PagingRequsetWithDateSearchDto {
    @Min(value = 1990)
    @Max(value = 2100)
    private Integer year;
    @Min(value = 1)
    @Max(value = 12)
    private Integer month;
    @Min(value = 0)
    //null 대신에 0으로 처리
    @Max(value = 31)
    private Integer day;
    @Min(value = 1)
    private int page = 1;
    @NotNull
    private String sort = "''";
    @NotNull
    private String desc = "desc";
    private Integer size = Page.PAGE_SIZE;

    private String SearchParameter;

    private String totalElement;

    public LocalDate makeLocalDate() {
        if (day == null || day == 0) {
            return LocalDate.of(year, month, 1); // 일(Day)를 1로 설정
        } else {
            return LocalDate.of(year, month, day);
        }
    }


}