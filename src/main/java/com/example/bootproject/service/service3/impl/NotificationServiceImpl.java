package com.example.bootproject.service.service3.impl;

import com.example.bootproject.repository.mapper3.notification.NotificationMapper;
import com.example.bootproject.service.service3.api.NotificationService;
import com.example.bootproject.vo.vo1.response.Page;
import com.example.bootproject.vo.vo1.response.notification.NotificationMessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.bootproject.vo.vo1.response.Page.MESSAGE_PAGE_SIZE;
import static com.example.bootproject.vo.vo1.response.Page.makeEmptyList;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationMapper notificationMapper;

    public Page<List<NotificationMessageResponseDto>> getPagedUnreadMessageListOfEmployee(String employeeId, int page){
        List<NotificationMessageResponseDto> data = notificationMapper.findCurrentUnreadMessageOfEmployee(employeeId,(page-1)*MESSAGE_PAGE_SIZE);
        if(!data.isEmpty()){
            int totalElement = notificationMapper.countUnreadMessageOfEmployee(employeeId);
            boolean hasNext = Page.MESSAGE_PAGE_SIZE*page<totalElement;
            return new Page<>(data,Page.MESSAGE_PAGE_SIZE,hasNext,"","",page,totalElement);
        }
        return (Page<List<NotificationMessageResponseDto>>) makeEmptyList();
    }

    public Page<List<NotificationMessageResponseDto>> getPagedUnreadMessageListOfManager(String managerId, int page){
        List<NotificationMessageResponseDto> data = notificationMapper.findCurrentUnreadMessageOfManager(managerId);
        if(!data.isEmpty()){
            int totalElement = notificationMapper.countUnreadMessageOfManager(managerId);
            boolean hasNext = Page.MESSAGE_PAGE_SIZE*page<totalElement;
            return new Page<>(data,Page.MESSAGE_PAGE_SIZE,hasNext,"","",page,totalElement);
        }
        return (Page<List<NotificationMessageResponseDto>>) makeEmptyList();
    }

    public int countUnreadMsgOfEmployee(String employeeId){
        return notificationMapper.countUnreadMessageOfEmployee(employeeId);
    }


}
