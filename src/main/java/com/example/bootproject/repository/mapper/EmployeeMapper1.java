package com.example.bootproject.repository.mapper;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.vo.vo1.request.Attendance_Info;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.sql.Time;
import java.util.Date;

@Mapper
public interface EmployeeMapper1 {

    // employee_id 에대한 start_time을 넣기위한 쿼리 merge을 employee_id에 대하여 실수로 두번 요청을 보내면 무시한다
    @Update("UPDATE attendance_info SET start_time = NOW() WHERE employee_id = #{employee_id}")
    void updateStartTime(@Param("employee_id") String employee_id);






    Employee findMemberByMemberId(String test);
}
