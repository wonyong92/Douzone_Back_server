package com.example.bootproject.service.service1;

import com.example.bootproject.vo.vo1.page.Page;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryRequestDto;
import com.example.bootproject.vo.vo1.response.AttendanceAppealMediateResponseDto;
import com.example.bootproject.vo.vo1.response.AttendanceApprovalUpdateResponseDto;
import com.example.bootproject.vo.vo1.response.RegularTimeAdjustmentHistoryResponseDto;

import java.util.List;


public interface ManagerService1 {

    //사원의 id와 근태관리자여부 true/false를 담음


    //정규출퇴근시간 설정 사원아이디는 위에 있는 사원아이디를 담기위해 작성
    RegularTimeAdjustmentHistoryResponseDto insertRegularTimeAdjustmentHistory
            (RegularTimeAdjustmentHistoryRequestDto dto, String employeeId);

    //타사원근태이상승인내역
    Page<List<AttendanceApprovalUpdateResponseDto>>managerGetApprovalInfoByEmployeeId(String employeeId, int page, String sort, String desc);


    //타사원의 조정요청내역
    Page<List<AttendanceAppealMediateResponseDto>>managerfindAttendanceInfoByMine(String employeeId,int page , String sort ,String desc);


    boolean employeeExists(String employeeId);
}
