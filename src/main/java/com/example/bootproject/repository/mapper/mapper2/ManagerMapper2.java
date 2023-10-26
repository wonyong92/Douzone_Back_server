package com.example.bootproject.repository.mapper.mapper2;

import com.example.bootproject.vo.vo2.response.EmployeeDto;
import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ManagerMapper2 {


    @Select("SELECT* FROM vacation_request WHERE DATE_FORMAT(vacation_request_time,'%Y-%m-%d') =#{date};")
    public List<VacationRequestDto> getAllVacationHistory(String date);
}
