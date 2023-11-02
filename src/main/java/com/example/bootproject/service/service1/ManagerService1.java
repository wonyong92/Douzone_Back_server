package com.example.bootproject.service.service1;

import com.example.bootproject.vo.vo1.request.AttendanceApprovalRequestDto;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryRequestDto;
import com.example.bootproject.vo.vo1.response.RegularTimeAdjustmentHistoryResponseDto;

import java.util.List;


public interface ManagerService1 {

    //사원의 id와 근태관리자여부 true/false를 담음


    //정규출퇴근시간 설정 사원아이디는 위에 있는 사원아이디를 담기위해 작성
    RegularTimeAdjustmentHistoryResponseDto insertRegularTimeAdjustmentHistory
            (RegularTimeAdjustmentHistoryRequestDto dto, String employeeId);

    //근태이상승인요청 내역
    List<AttendanceApprovalRequestDto> getAttendanceApprovalInfoDto(String employeeId);

}
