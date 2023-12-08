package com.example.bootproject.vo.vo1.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class PageRequest {
    //@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "sort", defaultValue = "attendance_approval_date") String sort, @RequestParam(name = "desc", defaultValue = "DESC") String desc
    @Min(1)
    int page = 1;
    String sort = "employee_id";
    String desc = "desc";
}
