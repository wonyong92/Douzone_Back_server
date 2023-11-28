package com.example.bootproject.repository.mapper3.notification;

import com.example.bootproject.vo.vo1.request.sse.SseMessageInsertDto;
import com.example.bootproject.vo.vo1.response.notification.NotificationMessageResponseDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static com.example.bootproject.vo.vo1.response.Page.MESSAGE_PAGE_SIZE;

@Mapper
public interface NotificationMapper {
    @Select("select * from notification_message where receiver = ${employeeId} and for_manager=false  limit " + MESSAGE_PAGE_SIZE + " offset ${startRow} ")
    List<NotificationMessageResponseDto> findCurrentUnreadMessageOfEmployee(String employeeId, int startRow);

    @Select("select count(*) from notification_message where receiver = ${employeeId} and ISNULL(read_time) and  for_manager=false")
    int countUnreadMessageOfEmployee(String employeeId);

    @Update("update notification_message set read_time=now() where message_id=${messageId}")
    int makeReadMessage(Long messageId);

    @Select("select * from notification_message where receiver = ${managerId} and for_manager=true limit " + MESSAGE_PAGE_SIZE + " offset 0 ")
    List<NotificationMessageResponseDto> findCurrentUnreadMessageOfManager(String managerId);

    @Select("select count(*) from notification_message where receiver = ${managerId} and ISNULL(read_time) and  for_manager=true")
    int countUnreadMessageOfManager(String managerId);

    @Insert("insert into notification_message(receiver, message, receive_time, read_time, link_to, identifier, for_manager) values (#{receiver},#{message},now(),null,#{linkTo},#{identifier},false)")
    @Options(useGeneratedKeys = true, keyProperty = "messageId")
    int addUnreadMsgOfEmployee(SseMessageInsertDto dto);

    @Select("select employee_id from employee where attendance_manager = true")
    List<String> getManagerId();

    @Select("select employee_id from attendance_appeal_request where attendance_appeal_request_id=#{appealRequestId}")
    String findEmployeeIdByAttendanceAppealRequestId(Long appealRequestId);

    @Insert("insert into notification_message(receiver, message, receive_time, read_time, link_to, identifier, for_manager) values (#{receiver},#{message},now(),null,#{linkTo},#{identifier},true)")
    @Options(useGeneratedKeys = true, keyProperty = "messageId")
    int addUnreadMsgOfManager(SseMessageInsertDto dto);
}
