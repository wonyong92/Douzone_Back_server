package com.example.bootproject.service.service1;


import com.example.bootproject.repository.mapper.EmployeeMapper1;
import com.example.bootproject.vo.vo1.request.*;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmployeeService1Impl implements EmployeeService1 {

        private final EmployeeMapper1 employeeMapper1;








        //출근기록
        @Override
        public AttendanceInfoResponseDto makeStartResponse(AttendanceInfoStartRequestDto dto, String employeeId) {
                LocalDate attendanceDate = LocalDate.now();
                LocalDateTime findStartTime = employeeMapper1.getStartTimeByEmployeeIdAndDate(employeeId, attendanceDate);

                if (findStartTime != null) {
                        // 출근 시간이 이미 기록되어 있는 경우
                        log.info("출근기록이있습니다");
                        return null;
                } else {
                        // 출근 시간이 아직 기록되지 않은 경우
                        LocalDateTime startTime = LocalDateTime.now();
                        dto.setEmployeeId(employeeId);
                        dto.setAttendanceDate(attendanceDate);
                        dto.setStartTime(startTime);

                        // DB에 출근 시간 기록
                        int attendanceInfo = employeeMapper1.startTimeRequest(dto);
                        if (attendanceInfo > 0) {
                                // 기록된 출근 정보를 바탕으로 AttendanceInfoEndRequestDto 가져오기
                              AttendanceInfoResponseDto responseDto = employeeMapper1.findattendanceInfo(employeeId,attendanceDate);
                                if (responseDto != null) {
                                        // 조회 성공
                                        return responseDto;
                                } else {
                                        // 조회 실패
                                        log.info("출근 정보를 조회하는데 실패하였습니다");
                                        return null;
                                }
                        } else {
                                log.info("기록에 실패하였습니다");
                                return null;
                        }
                }
        }

                /*
출근기록이 있으면 로그로 출근기록확인 null값 반환
400에러
아니면 200
만약 출근기록이 제대로 넘어오면 출근기록 리턴
400에러
기록된 출근정보를 바탕으로

 */


        //퇴근기록
        @Override
        public AttendanceInfoResponseDto makeEndResponse(AttendanceInfoEndRequestDto dto, String employeeId) {
                LocalDate attendanceDate=LocalDate.now();
                LocalDateTime findStartTime = employeeMapper1.getStartTimeByEmployeeIdAndDate(employeeId,attendanceDate);
                LocalDateTime findEndTime = employeeMapper1.getEndTimeByEmployeeIdAndDate(employeeId,attendanceDate);

                if(findStartTime == null){
                        log.info("출근기록이있습니다");
                        return null;
                } else if(findEndTime!= null){

                        log.info("퇴근기록이있습니다");
                        return null;
                }else{
                        LocalDateTime endTime = LocalDateTime.now();
                        dto.setEmployeeId(employeeId);
                        dto.setAttendanceDate(attendanceDate);
                        dto.setEndTime(endTime);

                        int endKey = employeeMapper1.endTimeRequest(dto);
                        if(endKey >0){
                                AttendanceInfoResponseDto responseDto = employeeMapper1.findattendanceInfo(employeeId,attendanceDate);
                                if(responseDto !=null){
                                        return responseDto;
                                }else{
                                        log.info("출근정보를 조회하는데 실패하였습니다");
                                        return null;
                                }
                        }
                        else {
                                log.info("기록에 실패하셨습니다");
                                return null;
                        }


                }

        }

        /*
 출근기록이 있으면 로그로 출근기록확인 null값 반환
아니면 200
만약 출근기록이 제대로 넘어오면 출근기록 리턴
400에러
기록된 출근정보를 바탕으로

 */


        //사원 년,월,일 사원근태정보검색
        @Override
        public List<AttendanceInfoRequestDto> getAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId) {
                return employeeMapper1.selectAttendanceByDate(attendanceDate,employeeId);
        }

        //사원 년,월 사원근태정보검색
        @Override
        public List<AttendanceInfoRequestDto> getAttendanceByMonthAndEmployee(LocalDate startDate, LocalDate endDate, String employeeId) {
                return employeeMapper1.selectAttendanceByMonthAndEmployee(startDate,endDate,employeeId);
        }


        //자신의 근태승인요청

        public AttendanceApprovalRequestDto approveAttendance(Long attendanceInfoId, String employeeId) {
                // 1. "지각" 상태 정보 가져오기
                AttendanceStatusCategoryRequestDto lateStatus = employeeMapper1.findLateStatus();

                // 2. 근태 상태 업데이트
                AttendanceInfoRequestDto attendanceInfoRequestDto = new AttendanceInfoRequestDto();
                attendanceInfoRequestDto.setAttendanceInfoId(attendanceInfoId);
                attendanceInfoRequestDto.setAttendanceStatusCategory(lateStatus.getKey());
                int updatedRows = employeeMapper1.updateAttendanceStatus(attendanceInfoRequestDto);

                // 3. 근태 승인 정보 삽입
                int insertedRows = employeeMapper1.insertAttendanceApproval(attendanceInfoId, employeeId);

                // 4. 근태 승인 정보를 DTO로 변환하여 반환
                AttendanceApprovalRequestDto attendanceApprovalDto = new AttendanceApprovalRequestDto();
                attendanceApprovalDto.setAttendanceInfoId(attendanceInfoId);
                attendanceApprovalDto.setAttendanceApprovalDate(LocalDate.now()); // 승인 날짜 설정
                attendanceApprovalDto.setEmployeeId(employeeId);

                return attendanceApprovalDto;
        }


        @Override
        public List<AttendanceApprovalRequestDto> findApprovalInfoByMine(String employeeId) {
                return employeeMapper1.findApprovalInfoByMine(employeeId);
        }


}