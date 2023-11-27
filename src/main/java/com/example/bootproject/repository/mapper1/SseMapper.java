package com.example.bootproject.repository.mapper1;

import com.example.bootproject.vo.vo1.request.sse.SseMessageInsertDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SseMapper {
    @Insert("insert into notification_message(receiver, message, receive_time, read_time, link_to, identifier, for_manager) values (#{receiver},#{message},now(),null,#{linkTo},#{identifier},false)")
    @Options(useGeneratedKeys = true,keyProperty = "messageId")
    int addUnreadMsgOfEmployee(SseMessageInsertDto dto);
    @Select("select employee_id from employee where attendance_manager = true")
    List<String> getManagerId();
    @Select("select employee_id from attendance_appeal_request where attendance_appeal_request_id=#{appealRequestId}")
    String findEmployeeIdByAttendanceAppealRequestId(Long appealRequestId);

    @Insert("insert into notification_message(receiver, message, receive_time, read_time, link_to, identifier, for_manager) values (#{receiver},#{message},now(),null,#{linkTo},#{identifier},true)")
    @Options(useGeneratedKeys = true,keyProperty = "messageId")
    int addUnreadMsgOfManager(SseMessageInsertDto dto);
}
