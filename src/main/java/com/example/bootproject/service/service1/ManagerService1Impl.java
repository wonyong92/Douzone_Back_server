package com.example.bootproject.service.service1;

import com.example.bootproject.repository.mapper.ManagerMapper1;
import com.example.bootproject.vo.vo1.request.AttendanceApprovalUpdateRequestDto;
import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryRequestDto;
import com.example.bootproject.vo.vo1.response.AttendanceAppealMediateResponseDto;
import com.example.bootproject.vo.vo1.response.RegularTimeAdjustmentHistoryResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManagerService1Impl implements ManagerService1{

    private final ManagerMapper1 managerMapper1;



    //출퇴근시간설정
    @Override
    //정규출퇴근시간 설정 사원아이디는 위에 있는 사원아이디를 담기위해 작성
    public RegularTimeAdjustmentHistoryResponseDto insertRegularTimeAdjustmentHistory
            (RegularTimeAdjustmentHistoryRequestDto dto, String employeeId) {
        //현재 설정한 시간
        LocalDateTime regularTimeAdjustmentTime = LocalDateTime.now();


            dto.setEmployeeId(employeeId);
            dto.setRegularTimeAdjustmentTime(regularTimeAdjustmentTime);

            log.info("DTO Content: {}", dto);
            int requestInfo = managerMapper1.insertregulartimeadjustment(dto,employeeId);

            RegularTimeAdjustmentHistoryResponseDto responseDto = managerMapper1.selectregulartimeadjustment(employeeId);


            // 결과 반환
        if (responseDto != null) {
            // 결과가 존재하면 그대로 반환
            return responseDto;
        } else {
            // 결과가 없으면 로그를 남기고 null 반환 또는 예외 처리
            log.info("No data found for employeeId: {}", employeeId);
            return null; // 또는 적절한 예외를 던지거나 기타 처리
        }
//TODO 사원id는 controller에서받는다
        //TODO GENERIC KEY사용한다 아니면 데이터가 있느지 확인하고 count로 조회후 true로하든가
        //
    }

    //타사원에대한 근태승인내역 조회
    @Override
    public List<AttendanceApprovalUpdateRequestDto> getAttendanceApprovalInfoDto(String employeeId) {
        return managerMapper1.findApprovalInfoByEmployeeId(employeeId);
    }

    @Override
    public AttendanceAppealMediateResponseDto findAttendanceInfoByMine(String employeeId) {
        return managerMapper1.findAttendanceInfoByMine(employeeId);
    }


}
