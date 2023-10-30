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

@Service
public class ManagerService1Impl implements ManagerService1{

    private final ManagerMapper1 managerMapper1;

    @Autowired
    public ManagerService1Impl(ManagerMapper1 managerMapper1) {
        this.managerMapper1 = managerMapper1;

    }




    //사원의 id와 근태관리자여부 true/false를 담음
    @Override
    public EmployeeRequest findattendancemanager(String employee_id, boolean attendance_manager) {
        return  managerMapper1.findattendancemanager(employee_id,attendance_manager);

    }





    //정규출퇴근시간 설정 사원아이디는 위에 있는 사원아이디를 담기위해 작성
    public RegularTimeAdjustmentHistoryDto insertRegularTimeAdjustmentHistory
            (RegularTimeAdjustmentHistoryDto dto, String employee_id) {
        LocalDateTime regularTimeAdjustmentTime = LocalDateTime.now();

        // 대상 사원이 출석 관리자인지 확인
        EmployeeRequest targetEmployee = managerMapper1.findattendancemanager(employee_id, true);

        if (targetEmployee != null && targetEmployee.isAttendanceManager()) {
            // 대상 사원의 employee_id를 설정
            dto.setEmployeeId(employee_id);
            // 현재 시간을 설정
            dto.setRegularTimeAdjustmentTime(regularTimeAdjustmentTime);

            // DB에 데이터 삽입
            managerMapper1.insertregulartimeadjustmenthistory(dto);

            // 결과 반환
            return dto;
        } else {
            throw new IllegalStateException("Target employee is not an attendance manager or does not exist.");
        }
    }

    //타사원에대한 근태승인내역 조회
    @Override
    public List<AttendanceApprovalInfoDto> getAttendanceApprovalInfoDto(String employeeId) {
        return managerMapper1.findApprovalInfoByEmployeeId(employeeId);
    }


}
