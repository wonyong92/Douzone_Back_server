package com.example.bootproject.repository.mapper3.regular_work_time;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface RegularWorkTimeMapper {
    @Select("select adjusted_start_time, adjusted_end_time from regular_time_adjustment_history where year(target_date) = #{year} order by regular_time_adjustment_history_id desc limit 1")
    Map<String, String> getRegularStartEndTime(int year);
}
