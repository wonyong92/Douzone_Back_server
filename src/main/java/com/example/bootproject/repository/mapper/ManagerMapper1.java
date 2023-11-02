package com.example.bootproject.repository.mapper;

import com.example.bootproject.vo.vo1.request.AttendanceApprovalUpdateRequestDto;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryRequestDto;
import com.example.bootproject.vo.vo1.response.AttendanceAppealMediateResponseDto;
import com.example.bootproject.vo.vo1.response.RegularTimeAdjustmentHistoryResponseDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ManagerMapper1 {


    //정규출퇴근시간 설정
    @Insert("INSERT INTO regular_time_adjustment_history (" +
            "target_date, adjusted_start_time, adjusted_end_time, reason, regular_time_adjustment_time, employee_id) " +
            "VALUES (#{dto.targetDate}, #{dto.adjustedStartTime}, #{dto.adjustedEndTime}, #{dto.reason}, #{dto.regularTimeAdjustmentTime}, #{dto.employeeId})")
    int insertregulartimeadjustment(@Param("dto") RegularTimeAdjustmentHistoryRequestDto dto, String employeeId);


    //정규출퇴근시간 내역
    @Select("SELECT " +
            "regular_time_adjustment_history_id, target_date, adjusted_start_time, adjusted_end_time, reason, regular_time_adjustment_time, employee_id " +
            "FROM regular_time_adjustment_history " +
            "WHERE employee_id = #{employeeId} " +
            "ORDER BY regular_time_adjustment_time DESC " +
            "LIMIT 1")
    RegularTimeAdjustmentHistoryResponseDto selectregulartimeadjustment(String employeeId );


    //타사원 근태이상승인내역
    @Select("SELECT e.employee_id, e.name, a.attendance_approval_date " +
            "FROM attendance_approval a " +
            "JOIN employee e ON a.employee_id = e.employee_id " +
            "WHERE e.employee_id = #{employeeId}")
    List<AttendanceApprovalUpdateRequestDto> findApprovalInfoByEmployeeId(String employeeId);

    //타사원의조정요청내역
    @Select("SELECT attendance_appeal_request_id, status, reason, attendance_info_id, appealed_start_time, appealed_end_time, employee_id, attendance_appeal_request_time, reason_for_rejection " +
            "FROM attendance_appeal_request " +
            "WHERE employee_id = #{employeeId}")
    AttendanceAppealMediateResponseDto findAttendanceInfoByMine(String employeeId);






}

