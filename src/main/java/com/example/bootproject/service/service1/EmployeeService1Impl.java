package com.example.bootproject.service.service1;


import com.example.bootproject.repository.mapper.EmployeeMapper1;
import com.example.bootproject.vo.vo1.request.AttendanceApprovalInfoDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoDto;
import com.example.bootproject.vo.vo1.request.AttendanceStatusCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeService1Impl implements EmployeeService1{

        private final EmployeeMapper1 employeeMapper1;

        @Autowired
        public EmployeeService1Impl(EmployeeMapper1 employeeMapper1) {
                this.employeeMapper1 = employeeMapper1;
        }


        //출근기록
        @Override
        public void updateStartTime(String employee_id) {
                LocalDateTime localTime = LocalDateTime.now();

                employeeMapper1.updateStartTime(employee_id,localTime);
        }

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
        public void approveAttendance(String employeeId, LocalDate attendanceDate) {
                // 1. 근태 정보 ID 찾기
                Long attendanceInfoId = employeeMapper1.findAttendanceInfoIdByEmployeeIdAndDate(employeeId, attendanceDate);
                if (attendanceInfoId == null) {
                        throw new RuntimeException("Attendance record not found for employee " + employeeId + " on date " + attendanceDate);
                }

                // 2. "지각" 상태 정보 가져오기
                AttendanceStatusCategoryDto lateStatus = employeeMapper1.findLateStatus();
                if (lateStatus == null) {
                        throw new RuntimeException("Late status not found in the database.");
                }

                // 3. 근태 상태 업데이트
                AttendanceInfoDto attendanceInfoDto = new AttendanceInfoDto();
                attendanceInfoDto.setAttendanceInfoId(attendanceInfoId);
                attendanceInfoDto.setAttendanceStatusCategory(lateStatus.getKey());
                int updatedRows = employeeMapper1.updateAttendanceStatus(attendanceInfoDto);
                if (updatedRows == 0) {
                        throw new RuntimeException("Failed to update attendance status for employee " + employeeId + " on date " + attendanceDate);
                }

                // 4. 근태 승인 정보 삽입
                int insertedRows = employeeMapper1.insertAttendanceApproval(attendanceInfoId, employeeId);
                if (insertedRows == 0) {
                        throw new RuntimeException("Failed to insert attendance approval for employee " + employeeId + " on date " + attendanceDate);
                }
        }




}