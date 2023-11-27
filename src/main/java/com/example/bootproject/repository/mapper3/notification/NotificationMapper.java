package com.example.bootproject.repository.mapper3.notification;

import static com.example.bootproject.vo.vo1.response.Page.MESSAGE_PAGE_SIZE;
import com.example.bootproject.vo.vo1.response.notification.NotificationMessageResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Select("select * from notification_message where receiver = ${employeeId} and for_manager=false  limit "+ MESSAGE_PAGE_SIZE+" offset ${startRow} ")
    List<NotificationMessageResponseDto> findCurrentUnreadMessageOfEmployee(String employeeId,int startRow);
    @Select("select count(*) from notification_message where receiver = ${employeeId} and ISNULL(read_time) and  for_manager=false")
    int countUnreadMessageOfEmployee(String employeeId);
    @Update("update notification_message set read_time=now() where message_id=${messageId}")
    int makeReadMessage(Long messageId);

    @Select("select * from notification_message where receiver = ${managerId} and for_manager=true limit "+ MESSAGE_PAGE_SIZE+" offset 0 ")
    List<NotificationMessageResponseDto> findCurrentUnreadMessageOfManager(String managerId);
    @Select("select count(*) from notification_message where receiver = ${managerId} and ISNULL(read_time) and  for_manager=true")
    int countUnreadMessageOfManager(String managerId);
}
