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


        //출근시간데이터확인
//        @Override
//        public AttendanceInfoStartRequestDto makestartTime(String employeeId) {
//                LocalDate attendanceDate = LocalDate.now();
//                LocalDateTime startStartTime = employeeMapper1.getStartTimeByEmployeeIdAndDate(employeeId,attendanceDate);
//
//                if (startStartTime != null) {
//
//                        log.info("출석시간이 있습니다");
//                        return null;
//                }else {
//
//                }
//                LocalDateTime startTime = LocalDateTime.now();
//
//                AttendanceInfoStartRequestDto attendanceInfoStartDto = new AttendanceInfoStartRequestDto();
//                attendanceInfoStartDto.setEmployeeId(employeeId);
//                attendanceInfoStartDto.setAttendanceDate(attendanceDate);
//                attendanceInfoStartDto.setStartTime(startTime);
//
//                int result = Math.toIntExact(employeeMapper1.startTime(attendanceInfoStartDto));
//
//                return attendanceInfoStartDto;
//        }





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
                        int attendanceKey = employeeMapper1.startTimeRequest(dto);
                        if (attendanceKey > 0) {
                                // 기록된 출근 정보를 바탕으로 AttendanceInfoResponseDto 가져오기
                                AttendanceInfoResponseDto responseDto = employeeMapper1.findByAttendanceKey(employeeId,attendanceDate);
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

//        @Override
//                public AttendanceInfoEndRequestDto endTime (String employeeId){
//                        AttendanceInfoEndRequestDto attendanceInfoEndRequestDto = new AttendanceInfoEndRequestDto();
//                        LocalDate currentDate = LocalDate.now();
//                        LocalDateTime existingEndTime = employeeMapper1.getEndTimeByEmployeeIdAndDate(employeeId, currentDate);
//                        LocalDateTime findstartTime = employeeMapper1.getStartTimeByEmployeeIdAndDate(employeeId, currentDate);
//
//                        if (existingEndTime != null) {
//                                log.info("퇴근기록이있습니다");
//                                return null;
//                        }
//
//                        if (findstartTime == null) {
//                                log.info("출근기록이없습니다");
//                                return null;
//                        }
//
//                        LocalDateTime endTime = LocalDateTime.now();
//                        attendanceInfoEndRequestDto.setEmployeeId(employeeId);
//                        attendanceInfoEndRequestDto.setAttendanceDate(currentDate);
//                        attendanceInfoEndRequestDto.setEndTime(endTime);
//
//                        int affectedRows = employeeMapper1.endTime(attendanceInfoEndRequestDto);
//
//                        //데이터가 비어있는지 확인
//                        if (affectedRows == 0) {
//                                log.info("결과가 비어 있습니다.");
//                        } else {
//                                log.info("결과가 비어 있지 않습니다. 영향을 받은 행의 수: {}", affectedRows);
//                        }
//
//
//                        return attendanceInfoEndRequestDto;
//                }
//        }


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
        @Transactional
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