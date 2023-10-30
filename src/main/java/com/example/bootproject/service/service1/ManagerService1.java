package com.example.bootproject.service.service1;

import com.example.bootproject.repository.mapper.ManagerMapper1;
import com.example.bootproject.vo.vo1.request.AttendanceApprovalInfoDto;
import com.example.bootproject.vo.vo1.request.EmployeeRequest;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface ManagerService1 {

    //사원의 id와 근태관리자여부 true/false를 담음
    EmployeeRequest findattendancemanager(String employeeId,boolean attendance_manager);

    //정규출퇴근시간 설정 사원아이디는 위에 있는 사원아이디를 담기위해 작성
    RegularTimeAdjustmentHistoryDto insertRegularTimeAdjustmentHistory
            (RegularTimeAdjustmentHistoryDto dto, String employeeId);

    //근태이상승인요청 내역
    List<AttendanceApprovalInfoDto> getAttendanceApprovalInfoDto(String employeeId);

}
