package com.example.bootproject.service.service1;

import com.example.bootproject.vo.vo1.request.AttendanceApprovalInsertRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceApprovalUpdateRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoEndRequestDto;
import com.example.bootproject.vo.vo1.request.AttendanceInfoStartRequestDto;
import com.example.bootproject.vo.vo1.response.AttendanceApprovalResponseDto;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;


import java.time.LocalDate;
import java.util.List;

public interface EmployeeService1 {


    //출근요청
    AttendanceInfoResponseDto makeStartResponse(AttendanceInfoStartRequestDto dto, String employeeId);



    //퇴근요청
    AttendanceInfoResponseDto makeEndResponse(AttendanceInfoEndRequestDto dto , String employeeId);


    //사원년월일검색
    List<AttendanceInfoResponseDto> getAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId);

    //사원년월검색
    List<AttendanceInfoResponseDto> getAttendanceByMonthAndEmployee(int year , int month, String employeeId);




    //자신의 근태승인요청
    AttendanceApprovalResponseDto approveAttendance(AttendanceApprovalUpdateRequestDto updaterequestdto , AttendanceApprovalInsertRequestDto insertrequestdto,String employeeId, Long attendanceInfoId);

    //자신의근태이상승인내역
    List<AttendanceApprovalUpdateRequestDto> findApprovalInfoByMine(String employeeId);




}