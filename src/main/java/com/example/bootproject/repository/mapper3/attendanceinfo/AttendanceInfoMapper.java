package com.example.bootproject.repository.mapper3.attendanceinfo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AttendanceInfoMapper {
    @Select("select count(*) from attendance_info where attendance_info_id = #{id}")
    Integer countById(Long id);

    @Insert(value = "insert into douzone_test.attendance_info ( employee_id, attendance_status_category,start_time, end_time, attendance_date) select employee_id, 'pending' as attendacne_status_category, null as start_time, null as end_time, curdate() as attendacne_date from (select * from attendance_info right join employee using(employee_id)) as employee_attendace_info_join where attendance_info_id is null")
    int insertAttendanceInfo();
}
