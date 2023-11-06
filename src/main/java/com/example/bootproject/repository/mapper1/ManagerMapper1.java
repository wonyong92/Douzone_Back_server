package com.example.bootproject.repository.mapper1;

import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryRequestDto;
import com.example.bootproject.vo.vo1.response.AttendanceAppealMediateResponseDto;
import com.example.bootproject.vo.vo1.response.AttendanceApprovalUpdateResponseDto;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import com.example.bootproject.vo.vo1.response.RegularTimeAdjustmentHistoryResponseDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ManagerMapper1 {


    //정규출퇴근시간 설정
    @Insert("INSERT INTO regular_time_adjustment_history (" + "target_date, adjusted_start_time, adjusted_end_time, reason, regular_time_adjustment_time, employee_id) " + "VALUES (#{dto.targetDate}, #{dto.adjustedStartTime}, #{dto.adjustedEndTime}, #{dto.reason}, #{dto.regularTimeAdjustmentTime}, #{dto.employeeId})")
    int insertregulartimeadjustment(@Param("dto") RegularTimeAdjustmentHistoryRequestDto dto, String employeeId);


    //정규출퇴근시간 내역
    @Select("SELECT " + "regular_time_adjustment_history_id, target_date, adjusted_start_time, adjusted_end_time, reason, regular_time_adjustment_time, employee_id " + "FROM regular_time_adjustment_history " + "WHERE employee_id = #{employeeId} " + "ORDER BY regular_time_adjustment_time DESC " + "LIMIT 1")
    RegularTimeAdjustmentHistoryResponseDto selectregulartimeadjustment(String employeeId);


    //타사원 근태이상승인내역
    @Select("SELECT * FROM attendance_approval WHERE employee_id = #{employeeId} ORDER BY ${sort} ${desc} LIMIT #{size} OFFSET #{startRow}")
    List<AttendanceApprovalUpdateResponseDto> getAllEmployeeByEmployeeId(@Param("employeeId") String employeeId, @Param("sort") String sort, @Param("desc") String desc, @Param("size") int size, @Param("startRow") int startRow);

    @Select("SELECT COUNT(*) FROM attendance_approval WHERE employee_id = #{employeeId}")
    int countApprovalInfoByEmployeeId(@Param("employeeId") String employeeId);

    //타사원의조정요청내역
    @Select("SELECT attendance_appeal_request_id, status, reason, attendance_info_id, appealed_start_time, appealed_end_time, employee_id, attendance_appeal_request_time, reason_for_rejection FROM attendance_appeal_request WHERE employee_id = #{employeeId} ORDER BY ${sort} ${desc} LIMIT #{size} OFFSET #{startRow}")
    List<AttendanceAppealMediateResponseDto> managerfindAttendanceAppealByEmployeeId(@Param("employeeId") String employeeId, @Param("sort") String sort, @Param("desc") String desc, @Param("size") int size, @Param("startRow") int startRow);

    //본인의 조정 요청 이력 갯수확인
    @Select("SELECT COUNT(*) FROM attendance_appeal_request WHERE employee_id = #{employeeId}")
    int countAttendanceAppealByEmployeeId(@Param("employeeId") String employeeId);


    //사원년월일 사원근태정보검색
    @Select("SELECT " + "attendance_info_id, " + "attendance_status_category, " + "employee_id, " + "start_time, " + "end_time, " + "attendance_date " + "FROM attendance_info " + "WHERE attendance_date = #{attendanceDate} AND employee_id = #{employeeId} " + "ORDER BY ${sort} ${desc} " + // sort 파라미터가 'attendance_date'가 되도록 확실히 하세요.
            "LIMIT #{size} OFFSET #{startRow}")
    List<AttendanceInfoResponseDto> selectmanagerAttendanceByDate(@Param("attendanceDate") LocalDate attendanceDate, @Param("employeeId") String employeeId, @Param("sort") String sort, @Param("desc") String desc, @Param("size") int size, @Param("startRow") int startRow);


    //사원년월 사원근태정보검색
    @Select("SELECT " + "attendance_info_id, " + "attendance_status_category, " + "employee_id, " + "start_time, " + "end_time, " + "attendance_date " + "FROM attendance_info " + "WHERE employee_id = #{employeeId} " + "AND YEAR(attendance_date) = #{year} " + "AND MONTH(attendance_date) = #{month} " + "ORDER BY ${sort} ${desc} " + // 여기에 공백 추가
            "LIMIT #{size} OFFSET #{startRow}")
    List<AttendanceInfoResponseDto> selectmanagerAttendanceByMonthAndEmployee(int year, int month, String employeeId, String sort, String desc, int size, int startRow);

    @Select({"SELECT COUNT(*) FROM attendance_info WHERE employee_id = #{employeeId} AND YEAR(attendance_date) = #{year} AND MONTH(attendance_date) = #{month} "})
    int managercountAttendanceInfoByEmployeeId(@Param("employeeId") String employeeId, int year, int month);

    @Select({"SELECT COUNT(*) FROM attendance_info WHERE employee_id = #{employeeId} AND attendance_date = #{attendanceDate} "})
    int managercountAttendanceInfoByMonthEmployeeId(@Param("employeeId") String employeeId, LocalDate attendanceDate);

    @Select("SELECT EXISTS(SELECT 1 FROM employee WHERE employee_id = #{employeeId})")
    boolean existsById(String employeeId);


}

