package com.example.bootproject.repository.mapper;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.vo.vo1.request.Attendance_Info;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Mapper
public interface EmployeeMapper1 {

    // employee_id 에대한 start_time을 넣기위한 쿼리 merge을 employee_id에 대하여 실수로 두번 요청을 보내면 무시한다
    @Update("UPDATE attendance_info SET end_time = #{starttime} WHERE employee_id = #{employeeid}" )
    void updateStartTime(@Param("employeeid") String employee_id ,@Param("starttime") LocalDateTime start_time);

    @Update("UPDATE attendance_info SET end_time = #{endtime} WHERE employee_id = #{employeeid}")
    void updateEndTime(@Param("employeeid") String employee_id, @Param("endtime")LocalDateTime end_time);






    Employee findMemberByMemberId(String test);
}
