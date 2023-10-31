package com.example.bootproject.service.service1;


import com.example.bootproject.repository.mapper.EmployeeMapper1;
import com.example.bootproject.vo.vo1.request.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService1Impl implements EmployeeService1{

        private final EmployeeMapper1 employeeMapper1;




        //출근기록
        @Transactional
        public AttendanceInfoStartDto startTime(String employeeId) {
                LocalDate attendanceDate = LocalDate.now();
                LocalDateTime startStartTime = employeeMapper1.getStartTimeByEmployeeIdAndDate(employeeId,attendanceDate);

                if (startStartTime != null) {

                        log.info("출석시간이 있습니다");
                         return null;
                }
                LocalDateTime startTime = LocalDateTime.now();

                AttendanceInfoStartDto attendanceInfoStartDto = new AttendanceInfoStartDto();
                attendanceInfoStartDto.setEmployeeId(employeeId);
                attendanceInfoStartDto.setAttendanceDate(attendanceDate);
                attendanceInfoStartDto.setStartTime(startTime);

                int result = employeeMapper1.startTime(attendanceInfoStartDto);

                return attendanceInfoStartDto;
        }

        @Transactional
        public AttendanceInfoEndDto endTime(String employeeId) {
                AttendanceInfoEndDto  attendanceInfoEndDto = new AttendanceInfoEndDto();
                LocalDate currentDate = LocalDate.now();
                LocalDateTime existingEndTime = employeeMapper1.getEndTimeByEmployeeIdAndDate(employeeId, currentDate);
                LocalDateTime findstartTime = employeeMapper1.getStartTimeByEmployeeIdAndDate(employeeId,currentDate);

                if (existingEndTime != null) {
                        log.info("퇴근기록이있습니다");
                        return null;
                }

                if(findstartTime == null){
                        log.info("출근기록이없습니다");
                        return null;
                }

                LocalDateTime endTime = LocalDateTime.now();
                attendanceInfoEndDto.setEmployeeId(employeeId);
                attendanceInfoEndDto.setAttendanceDate(currentDate);
                attendanceInfoEndDto.setEndTime(endTime);

                int affectedRows = employeeMapper1.endTime(attendanceInfoEndDto);

                //데이터가 비어있는지 확인
                if (affectedRows == 0) {
                        log.info("결과가 비어 있습니다.");
                } else {
                        log.info("결과가 비어 있지 않습니다. 영향을 받은 행의 수: {}", affectedRows);
                }


                return attendanceInfoEndDto;
        }


        /*
    세션에서 employeeId 가져온다 지금은 하드코딩중
    사원id데이터 형식이 이상하게 넘어올경우 오류코드
    starttime시간 내역을 확인하는mapper을 들고와서 비교한다
    starttime이 있으면 출근기록을남김니다
    데이터가 비어있는지확인 비어있으면 로그
    성공하면 attendanceInfoEndDto반환
    */


        //사원 년,월,일 사원근태정보검색
        @Override
        public List<AttendanceInfoDto> getAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId) {
                return employeeMapper1.selectAttendanceByDate(attendanceDate,employeeId);
        }

        //사원 년,월 사원근태정보검색
        @Override
        public List<AttendanceInfoDto> getAttendanceByMonthAndEmployee(LocalDate startDate, LocalDate endDate, String employeeId) {
                return employeeMapper1.selectAttendanceByMonthAndEmployee(startDate,endDate,employeeId);
        }


        //자신의 근태승인요청
        @Override
        @Transactional
        public void approveAttendance(Long attendanceInfoId, String employeeId) {
                // 1. "지각" 상태 정보 가져오기
                AttendanceStatusCategoryDto lateStatus = employeeMapper1.findLateStatus();

                // 2. 근태 상태 업데이트
                AttendanceInfoDto attendanceInfoDto = new AttendanceInfoDto();
                attendanceInfoDto.setAttendanceInfoId(attendanceInfoId);
                attendanceInfoDto.setAttendanceStatusCategory(lateStatus.getKey());
                int updatedRows = employeeMapper1.updateAttendanceStatus(attendanceInfoDto);

                // 3. 근태 승인 정보 삽입
                int insertedRows = employeeMapper1.insertAttendanceApproval(attendanceInfoId, employeeId);
        }

        @Override
        public List<AttendanceApprovalInfoDto> findApprovalInfoByMine(String employeeId) {
                return employeeMapper1.findApprovalInfoByMine(employeeId);
        }


}