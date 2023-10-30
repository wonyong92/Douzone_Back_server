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