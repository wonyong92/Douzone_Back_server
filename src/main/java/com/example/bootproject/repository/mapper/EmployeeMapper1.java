package com.example.bootproject.repository.mapper;

import com.example.bootproject.vo.vo1.request.AttendanceApprovalRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoEndRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoStartRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceStatusCategoryRequestDto;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EmployeeMapper1 {


    //출근기록
    @Insert("INSERT INTO attendance_info (employee_id, attendance_date, start_time) " +
            "VALUES (#{dto.employeeId}, #{dto.attendanceDate}, #{dto.startTime}) " +
            "ON DUPLICATE KEY UPDATE start_time = VALUES (start_time)")
    int startTimeRequest(@Param("dto") AttendanceInfoStartRequestDto dto);


    //응답 근태정보테이블
    @Select("SELECT regular_time_adjustment_history_id, target_date, adjusted_start_time, adjusted_end_time, reason, regular_time_adjustment_time, employee_id " +
            "FROM regular_time_adjustment_history " +
            "WHERE employee_id = #{employeeId} AND target_date = #{AttendanceDate}")
    AttendanceInfoResponseDto findattendanceInfo(String employeeId , LocalDate AttendanceDate);


    //출근내역찾기
    @Select("SELECT start_time FROM attendance_info " +
            "WHERE employee_id = #{employeeId} AND attendance_date = #{date}")
    LocalDateTime getStartTimeByEmployeeIdAndDate(String employeeId, LocalDate date);



    //퇴근기록
    @Insert("UPDATE attendance_info SET end_time = #{dto.endTime} " +
            "WHERE employee_id = #{dto.employeeId} AND attendance_date = #{dto.attendanceDate}")
    int endTimeRequest(@Param("dto") AttendanceInfoEndRequestDto dto);

    //퇴근내역찾기
    @Select("SELECT end_time FROM attendance_info " +
            "WHERE employee_id = #{employeeId} AND attendance_date = #{date}")
    LocalDateTime getEndTimeByEmployeeIdAndDate(String employeeId, LocalDate date);





    //사원년월일 사원근태정보검색
    @Select("SELECT " +
            "attendance_info_id,"+
            "employee_id, " +
            "attendance_date, " +
            "attendance_status_category, " +
            "start_time, " +
            "end_time " +
            "FROM attendance_info " +
            "WHERE attendance_date = #{attendanceDate} AND employee_id = #{employeeId} " +
            "ORDER BY employee_id")
    List<AttendanceInfoResponseDto> selectAttendanceByDate(LocalDate attendanceDate , String employeeId);



    //사원년월 사원근태정보검색
    @Select("SELECT " +
            "attendance_info_id, " +
            "employee_id, " +
            "attendance_date, " +
            "attendance_status_category, " +
            "start_time, " +
            "end_time " +
            "FROM attendance_info " +
            "WHERE employee_id = #{employeeId} " +
            "AND YEAR(attendance_date) = #{year} " +
            "AND MONTH(attendance_date) = #{month} " +
            "ORDER BY attendance_date")
    List<AttendanceInfoResponseDto> selectAttendanceByMonthAndEmployee(int year, int month, String employeeId);


//세션에서 attendance_info정보를 찾아오는걸로 변경
    //'지각' key값을 찾는 쿼리문이다
    @Select("SELECT * FROM attendance_status_category WHERE `key` = '지각'")
    AttendanceStatusCategoryRequestDto findLateStatus();


    //attendance_info테이블에 대리키를 조회해 현재상태를 만약 근태이상이라고 있으면 이거를 인정한 지각이라는 데이터로 변경한다
    @Update("UPDATE attendance_info SET attendance_status_category = #{dto.attendanceStatusCategory} WHERE attendance_info_id = #{attendanceInfoId}")
    int updateAttendanceStatus(@Param("dto") AttendanceInfoResponseDto attendanceInfoResponseDto);
//    dto보단 key만 적는다

    //근태정보--승인 테이블에 승인을 한 내역을 남긴다
    @Insert("INSERT INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id) VALUES (#{attendanceInfoId}, NOW(), #{employeeId})")
    int insertAttendanceApproval(Long attendanceInfoId, String employeeId);



    //자신의 근태이상승인내역
    @Select("SELECT employee_id, name, attendance_approval_date " +
            "FROM attendance_approval  " +
            "JOIN employee USING (employee_id) " +
            "WHERE employee_id = #{employeeId}")
    List<AttendanceApprovalRequestDto> findApprovalInfoByMine(String employeeId);


}
