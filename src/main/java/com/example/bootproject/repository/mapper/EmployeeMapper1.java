package com.example.bootproject.repository.mapper;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.vo.vo1.request.AttendanceInfoDto;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EmployeeMapper1 {

    // employee_id 에대한 start_time을 넣기위한 쿼리 merge을 employee_id에 대하여 실수로 두번 요청을 보내면 무시한다
    //출근기록

    @Update("UPDATE attendance_info SET end_time = #{starttime} WHERE employee_id = #{employeeid}" )
    void updateStartTime(@Param("employeeid") String employee_id ,@Param("starttime") LocalDateTime start_time);
    //퇴근기록

    @Update("UPDATE attendance_info SET end_time = #{endtime} WHERE employee_id = #{employeeid}")
    void updateEndTime(@Param("employeeid") String employee_id, @Param("endtime")LocalDateTime end_time);


    //타사원년월일 사원근태정보검색
    @Select("SELECT " +
            "employee_id, " +
            "attendance_date, " +
            "attendance_status_category, " +
            "start_time, " +
            "end_time " +
            "FROM attendance_info " +
            "WHERE attendance_date = #{attendance_date} AND employee_id = #{employee_id} " +
            "ORDER BY employee_id")
    List<AttendanceInfoDto> selectAttendanceByDate(@Param("attendance_date") LocalDate attendance_date, @Param("employee_id")String employee_id);

    //타사원년월 사원근태정보검색
    @Select("SELECT " +
            "employee_id, " +
            "attendance_date, " +
            "attendance_status_category, " +
            "start_time, " +
            "end_time " +
            "FROM attendance_info " +
            "WHERE employee_id = #{employeeId} " +
            "AND attendance_date >= #{startDate} " +
            "AND attendance_date <= #{endDate} " +
            "ORDER BY attendance_date")
    List<AttendanceInfoDto> selectAttendanceByMonthAndEmployee(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("employeeId") String employeeId);






    Employee findMemberByMemberId(String test);
}
