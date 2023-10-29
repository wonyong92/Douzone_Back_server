package com.example.bootproject.repository.mapper;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.vo.vo1.request.AttendanceInfoDto;
import com.example.bootproject.vo.vo1.request.AttendanceStatusCategoryDto;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
            "WHERE attendance_date = #{attendance_date} AND employee_id = #{employeeId} " +
            "ORDER BY employeeId")
    List<AttendanceInfoDto> selectAttendanceByDate(LocalDate attendanceDate ,String employeeId);

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
    List<AttendanceInfoDto> selectAttendanceByMonthAndEmployee(LocalDate startDate, LocalDate endDate, String employeeId);


    //attendance_info테이블에 세션에 로그인된 자기자신의 employee_id와 조회할려는 해당날짜에 대한 대리키를 찾는다
    @Select("SELECT attendance_info_id FROM attendance_info WHERE employee_id = #{employeeId} AND attendance_date = #{attendanceDate}")
    Long findAttendanceInfoIdByEmployeeIdAndDate(String employeeId, LocalDate attendanceDate);


    //'지각' key값을 찾는 쿼리문이다
    @Select("SELECT * FROM attendance_status_category WHERE `key` = '지각'")
    AttendanceStatusCategoryDto findLateStatus();


    //attendance_info테이블에 대리키를 조회해 현재상태를 만약 근태이상이라고 있으면 이거를 인정한 지각이라는 데이터로 변경한다
    @Update("UPDATE attendance_info SET attendance_status_category = #{attendanceStatusCategory} WHERE attendance_info_id = #{attendanceInfoId}")
    int updateAttendanceStatus(AttendanceInfoDto attendanceInfoDto);

    //근태정보--승인 테이블에 승인을 한 내역을 남긴다
    @Insert("INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id) VALUES (#{attendanceInfoId}, NOW(), #{employeeId})")
    int insertAttendanceApproval(Long attendanceInfoId, String employeeId);









}
