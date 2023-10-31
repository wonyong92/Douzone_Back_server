package com.example.bootproject.repository.mapper;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.vo.vo1.request.*;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Mapper
public interface EmployeeMapper1 {


    //출근기록
    @Insert("INSERT INTO attendance_info (employee_id, attendance_date, start_time) " +
            "VALUES (#{employeeId}, #{attendanceDate}, #{startTime}) " +
            "ON DUPLICATE KEY UPDATE start_time = VALUES (start_time)")
    int startTime(AttendanceInfoStartDto attendanceInfoStartDto);

    //퇴근기록
    @Insert("UPDATE attendance_info SET end_time = #{endTime} " +
            "WHERE employee_id = #{employeeId} AND attendance_date = #{attendanceDate}")
    int endTime(AttendanceInfoEndDto attendanceInfoEndDto);

    //출근내역찾기
    @Select("SELECT start_time FROM attendance_info " +
            "WHERE employee_id = #{employeeId} AND attendance_date = #{date}")
    LocalDateTime getStartTimeByEmployeeIdAndDate(String employeeId, LocalDate date);

    //퇴근내역찾기
    @Select("SELECT end_time FROM attendance_info " +
            "WHERE employee_id = #{employeeId} AND attendance_date = #{date}")
    LocalDateTime getEndTimeByEmployeeIdAndDate(String employeeId, LocalDate date);





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

//세션에서 attendance_info정보를 찾아오는걸로 변경
    //'지각' key값을 찾는 쿼리문이다
    @Select("SELECT * FROM attendance_status_category WHERE `key` = '지각'")
    AttendanceStatusCategoryDto findLateStatus();


    //attendance_info테이블에 대리키를 조회해 현재상태를 만약 근태이상이라고 있으면 이거를 인정한 지각이라는 데이터로 변경한다
    @Update("UPDATE attendance_info SET attendance_status_category = #{attendanceStatusCategory} WHERE attendance_info_id = #{attendanceInfoId}")
    int updateAttendanceStatus(AttendanceInfoDto attendanceInfoDto);
//    dto보단 key만 적는다

    //근태정보--승인 테이블에 승인을 한 내역을 남긴다
    @Insert("INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id) VALUES (#{attendanceInfoId}, NOW(), #{employeeId})")
    int insertAttendanceApproval(Long attendanceInfoId, String employeeId);



    //자신의 근태이상승인내역
    @Select("SELECT e.employee_id, e.name, a.attendance_approval_date " +
            "FROM attendance_approval a " +
            "JOIN employee e ON a.employee_id = e.employee_id " +
            "WHERE e.employee_id = #{employeeId}")
    List<AttendanceApprovalInfoDto> findApprovalInfoByMine(String employeeId);











}
