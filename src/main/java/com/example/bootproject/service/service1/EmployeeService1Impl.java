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




        //출근시간데이터확인
        @Override
        public LocalDateTime getStartTimeByEmployeeIdAndDate(String employeeId, LocalDate date) {
                return employeeMapper1.getStartTimeByEmployeeIdAndDate(employeeId, date);
        }

        //퇴근시간데이터확인
        @Override
        public LocalDateTime getEndTimeByEmployeeIdAndDate(String employeeId, LocalDate date) {
                return employeeMapper1.getEndTimeByEmployeeIdAndDate(employeeId,date);
        }

        //출근시간요청
        @Override
        public int startTime(AttendanceInfoStartDto attendanceInfoStartDto) {
                return employeeMapper1.startTime(attendanceInfoStartDto);
        }

        //퇴근시간요청
        @Override
        public int endTime(AttendanceInfoEndDto attendanceInfoEndDto) {
                return employeeMapper1.endTime(attendanceInfoEndDto);
        }


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
        @Transactional
        public AttendanceApprovalDto approveAttendance(Long attendanceInfoId, String employeeId) {
                // 1. "지각" 상태 정보 가져오기
                AttendanceStatusCategoryDto lateStatus = employeeMapper1.findLateStatus();

                // 2. 근태 상태 업데이트
                AttendanceInfoDto attendanceInfoDto = new AttendanceInfoDto();
                attendanceInfoDto.setAttendanceInfoId(attendanceInfoId);
                attendanceInfoDto.setAttendanceStatusCategory(lateStatus.getKey());
                int updatedRows = employeeMapper1.updateAttendanceStatus(attendanceInfoDto);

                // 3. 근태 승인 정보 삽입
                int insertedRows = employeeMapper1.insertAttendanceApproval(attendanceInfoId, employeeId);

                // 4. 근태 승인 정보를 DTO로 변환하여 반환
                AttendanceApprovalDto attendanceApprovalDto = new AttendanceApprovalDto();
                attendanceApprovalDto.setAttendanceInfoId(attendanceInfoId);
                attendanceApprovalDto.setAttendanceApprovalDate(LocalDate.now()); // 승인 날짜 설정
                attendanceApprovalDto.setEmployeeId(employeeId);

                return attendanceApprovalDto;
        }


        @Override
        public List<AttendanceApprovalDto> findApprovalInfoByMine(String employeeId) {
                return employeeMapper1.findApprovalInfoByMine(employeeId);
        }


}