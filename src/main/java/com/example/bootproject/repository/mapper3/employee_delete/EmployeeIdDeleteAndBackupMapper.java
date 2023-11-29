package com.example.bootproject.repository.mapper3.employee_delete;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeIdDeleteAndBackupMapper {
    @Delete("delete from employee where employee_id=#{employeeId}")
    int deleteEmployeeByEmployeeId(String employeeId);
    @Insert("Insert into back_employee (select * from employee where employee_id = #{employeeId})")
    int backupEmployeeTableEntity(String employeeId);
    @Insert("Insert into back_attendance_appeal_request (select * from attendance_appeal_request where employee_id = #{employeeId})")
    int backupAttendanceAppealRequestTableEntity(String employeeId);
    @Insert("Insert into back_attendance_approval (select * from attendance_approval where employee_id = #{employeeId})")
    int backupAttendanceApprovalTableEntity(String employeeId);
    @Insert("Insert into back_attendance_info (select * from attendance_info where employee_id = #{employeeId})")
    int backupAttendanceInfoTableEntity(String employeeId);
    @Insert("Insert into back_auth (select * from auth where login_id = #{employeeId})")
    int backupAuthTableEntity(String employeeId);
    @Insert("Insert into back_notification_message (select * from notification_message where notification_message.receiver = #{employeeId})")
    int backupNotificationMessageTableEntity(String employeeId);
    @Insert("Insert into back_regular_time_adjustment_history (select * from regular_time_adjustment_history where employee_id = #{employeeId})")
    int backupRegularTimeAdjustmentHistoryTableEntity(String employeeId);
    @Insert("Insert into back_vacation_adjusted_history (select * from vacation_adjusted_history where employee_id = #{employeeId})")
    int backupVacationAdjustedHistoryTableEntity(String employeeId);
    @Insert("Insert into back_vacation_quantity_setting (select * from vacation_quantity_setting where employee_id = #{employeeId})")
    int backupVacationQuantitySettingTableEntity(String employeeId);
    @Insert("Insert into back_vacation_request (select * from vacation_request where employee_id = #{employeeId})")
    int backupVacationRequestTableEntity(String employeeId);

}
