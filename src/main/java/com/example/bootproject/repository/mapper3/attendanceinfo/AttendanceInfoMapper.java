package com.example.bootproject.repository.mapper3.attendanceinfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AttendanceInfoMapper {
    @Select("select count(*) from attendance_info where attendance_info_id = #{id}")
    Integer countById(Long id);
}
