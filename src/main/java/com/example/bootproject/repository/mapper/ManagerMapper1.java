package com.example.bootproject.repository.mapper;

import com.example.bootproject.vo.vo1.request.EmployeeDto;
import com.example.bootproject.vo.vo1.request.EmployeeRequest;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Mapper
public interface ManagerMapper1 {


    //사원이 근태관리자인지 일반사원인지 확인
    @Select("SELECT employee_id, attendance_manager " +
            "FROM employee " +
            "WHERE employee_id = #{employee_id} AND attendance_manager = #{attendance_manager}")
    EmployeeRequest findattendancemanager(String employee_id, boolean attendance_manager);



    //정규출퇴근시간 설정
    @Insert("INSERT INTO regular_time_adjustment_history내 (" +
            "target_date, adjusted_start_time, adjusted_end_time, reason, regular_time_adjustment_time, employee_id) " +
            "VALUES (#{target_date}, #{adjusted_start_time}, #{adjusted_end_time }, #{reason}, #{regular_time_adjustment_time}, #{employee_id})")
    int insertregulartimeadjustmenthistory(RegularTimeAdjustmentHistoryDto dto);




}

