package com.example.bootproject.repository.chartmapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import static com.example.bootproject.system.StaticString.*;

@Mapper
public interface ChartMapper {


    //승인된 연차갯수
    @Select("SELECT SUM(CASE WHEN MONTH(vacation_start_date) = MONTH(vacation_end_date) THEN DATEDIFF(vacation_end_date, vacation_start_date) + 1 WHEN MONTH(vacation_end_date) = #{month} AND YEAR(vacation_end_date) = #{year} THEN DATEDIFF(vacation_end_date, CONCAT(#{year},'-',#{month},'-01')) + 1 WHEN MONTH(vacation_start_date) = #{month} - 1 AND YEAR(vacation_start_date) = #{year} - 1 THEN DATEDIFF(CONCAT(#{year},'-',#{month},'-01'), vacation_start_date) ELSE 0 END) FROM vacation_request WHERE vacation_request_state_category_key = '" + VACATION_REQUEST_STATUS_CATEGORY_APPROVAL + "' AND employee_id = #{employeeId} AND (YEAR(vacation_start_date) = #{year} OR YEAR(vacation_end_date) = #{year}) AND (MONTH(vacation_start_date) = #{month} OR MONTH(vacation_end_date) = #{month})")
    int countApprovalVacationMonthDays(int year, int month, String employeeId);


    //반려된 연차갯수
    @Select("SELECT SUM(CASE WHEN MONTH(vacation_start_date) = MONTH(vacation_end_date) THEN DATEDIFF(vacation_end_date, vacation_start_date) + 1 WHEN MONTH(vacation_end_date) = #{month} AND YEAR(vacation_end_date) = #{year} THEN DATEDIFF(vacation_end_date, CONCAT(#{year},'-',#{month},'-01')) + 1 WHEN MONTH(vacation_start_date) = #{month} - 1 AND YEAR(vacation_start_date) = #{year} - 1 THEN DATEDIFF(CONCAT(#{year},'-',#{month},'-01'), vacation_start_date) ELSE 0 END) FROM vacation_request WHERE vacation_request_state_category_key = '" + VACATION_REQUEST_STATUS_CATEGORY_REQUESTED + "' AND employee_id = #{employeeId} AND (YEAR(vacation_start_date) = #{year} OR YEAR(vacation_end_date) = #{year}) AND (MONTH(vacation_start_date) = #{month} OR MONTH(vacation_end_date) = #{month})")
    int countRequestedVacationMonthDays(int year, int month, String employeeId);


    //반려된 연처 갯수
    @Select("SELECT SUM(CASE WHEN MONTH(vacation_start_date) = MONTH(vacation_end_date) THEN DATEDIFF(vacation_end_date, vacation_start_date) + 1 WHEN MONTH(vacation_end_date) = #{month} AND YEAR(vacation_end_date) = #{year} THEN DATEDIFF(vacation_end_date, CONCAT(#{year},'-',#{month},'-01')) + 1 WHEN MONTH(vacation_start_date) = #{month} - 1 AND YEAR(vacation_start_date) = #{year} - 1 THEN DATEDIFF(CONCAT(#{year},'-',#{month},'-01'), vacation_start_date) ELSE 0 END) FROM vacation_request WHERE vacation_request_state_category_key = '" + VACATION_REQUEST_STATUS_CATEGORY_REJECTED + "' AND employee_id = #{employeeId} AND (YEAR(vacation_start_date) = #{year} OR YEAR(vacation_end_date) = #{year}) AND (MONTH(vacation_start_date) = #{month} OR MONTH(vacation_end_date) = #{month})")
    int countRejectedVacationMonthDays(int year, int month, String employeeId);

    @Select("SELECT COUNT(*) FROM attendance_info WHERE employee_id = #{employeeId} AND attendance_status_category = '" +  ATTENDANCE_INFO_STATUS_ABSENT  + "' AND YEAR(attendance_date) = #{year} AND MONTH(attendance_date) = #{month}")
    int countAbnormalAttendance( String employeeId,  int year,  int month);

    @Select("SELECT COUNT(*) FROM attendance_info WHERE employee_id = #{employeeId} AND attendance_status_category = '"+  ATTENDANCE_INFO_STATUS_NORMAL+"' AND YEAR(attendance_date) = #{year} AND MONTH(attendance_date) = #{month}")
    int countNormalAttendance( String employeeId, int year,  int month);

    @Select("SELECT COUNT(*) FROM attendance_info WHERE  employee_id = #{employeeId} AND attendance_status_category = '" + ATTENDANCE_INFO_STATUS_REQUESTED+"' AND YEAR(attendance_date) = #{year} AND MONTH(attendance_date) = #{month}")
    int countRequestedAttendance (String employeeId , int year, int month);








}
