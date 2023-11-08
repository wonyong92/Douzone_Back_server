package com.example.bootproject.repository.mapper3.attendanceinfo;

import com.example.bootproject.vo.vo3.response.attendance.AttendanceCheckResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

import static com.example.bootproject.system.StaticString.ATTENDANCE_INFO_STATUS_PENDING;

@Mapper
public interface AttendanceInfoMapper {
    @Select("select count(*) from attendance_info where attendance_info_id = #{id}")
    Integer countById(Long id);

    @Select("select attendance_info_id from attendance_info where attendance_info_id = #{id} and employee_id=#{employeeId}")
    Integer countByIdEmployeeId(Long id, String employeeId);

    @Insert(value = "insert into douzone_test.attendance_info ( employee_id, attendance_status_category,start_time, end_time, attendance_date) select employee_id, '" + ATTENDANCE_INFO_STATUS_PENDING + "' as attendacne_status_category, null as start_time, null as end_time, curdate() as attendacne_date from (select * from (select * from attendance_info where attendance_date = curdate())as info right join employee using(employee_id)) as employee_attendace_info_join where attendance_info_id is null")
    int insertAttendanceInfo();

    @Select("select attendance_info_id, attendance_status_category, start_time, end_time from attendance_info where attendance_date = curdate()")
    List<AttendanceCheckResponse> getListAttendanceInfoOfTodayAfterAutoInsert();

    @Update("update attendance_info set attendance_status_category = #{status} where attendance_info_id=#{attendanceInfoId}")
    int updateAttendanceInfoStatus(String status, Long attendanceInfoId);
}
