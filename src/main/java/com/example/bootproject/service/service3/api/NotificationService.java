package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo1.response.Page;
import com.example.bootproject.vo.vo1.response.notification.NotificationMessageResponseDto;

import java.util.List;

public interface NotificationService {
    public int countUnreadMsgOfEmployee(String employeeId);
    public Page<List<NotificationMessageResponseDto>> getPagedUnreadMessageListOfEmployee(String employeeId, int page);

    public Page<List<NotificationMessageResponseDto>> getPagedUnreadMessageListOfManager(String managerId, int page);
}
