package com.example.bootproject.service.service1;


import com.example.bootproject.repository.mapper.EmployeeMapper1;
import com.example.bootproject.vo.vo1.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService1Impl implements EmployeeService1{

        private final EmployeeMapper1 employeeMapper1;




        //출근기록
        @Transactional
        public void startTime(String employeeId) {
                // 현재 날짜에 해당하는 출근 기록을 확인
                LocalDateTime existingStartTime = employeeMapper1.getStartTimeByEmployeeIdAndDate(employeeId, LocalDate.now());
                if (existingStartTime != null) {
                        throw new IllegalStateException("이미 출근 기록이 존재합니다.");
                }

                // 출근 시간 기록
                LocalDateTime startTime = LocalDateTime.now();
                employeeMapper1.startTime(employeeId, startTime);
        }

        /*
    세션에서 employeeId 가져온다 지금은 하드코딩중
    사원id데이터 형식이 이상하게 넘어올경우 오류코드
    starttime시간 내역을 확인하는mapper을 들고와서 비교한다
    starttime이 있으면 출근기록을남김니다
    모든 조건 성공시 200 응답코드
    */



        //퇴근기록
        @Override
        public void updateEndTime(String employee_id) {
                LocalDateTime localTime = LocalDateTime.now();
                employeeMapper1.updateEndTime(employee_id,localTime);
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