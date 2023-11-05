package com.example.bootproject.service.service1;


import com.example.bootproject.repository.mapper.EmployeeMapper1;

import com.example.bootproject.vo.vo1.request.*;
import com.example.bootproject.vo.vo1.response.AttendanceAppealMediateResponseDto;
import com.example.bootproject.vo.vo1.response.AttendanceApprovalResponseDto;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import com.example.bootproject.vo.vo1.response.EmployeeSearchResponseDto;
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




        //TODO 예외처리 컨트롤러로 보내기
        //출근요청
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

        //퇴근요청
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


//        사원 년,월,일 사원근태정보검색
        @Override
        public List<AttendanceInfoResponseDto> getAttendanceByDateAndEmployee(LocalDate attendanceDate, String employeeId) {
                return employeeMapper1.selectAttendanceByDate(attendanceDate,employeeId);
        }
        //사원 년,월 사원근태정보검색
        @Override
        public List<AttendanceInfoResponseDto> getAttendanceByMonthAndEmployee(int year, int month, String employeeId) {
                return employeeMapper1.selectAttendanceByMonthAndEmployee(year , month ,employeeId);
        }

        //자신의 근태승인요청

        public AttendanceApprovalResponseDto approveAttendance(
                                                               String employeeId, Long attendanceInfoId) {

                AttendanceStatusCategoryRequestDto lateStatus = employeeMapper1.findLateStatus();
                log.info("지각 상태 정보를 가져왔습니다: {}", lateStatus);

                // 2. 근태 상태 업데이트
                AttendanceApprovalUpdateRequestDto updateRequestDto = new AttendanceApprovalUpdateRequestDto(
                        lateStatus.getKey(),
                        attendanceInfoId
                );
                int updatedRows = employeeMapper1.updateAttendanceStatus(updateRequestDto);
                log.info("근태 상태를 업데이트 했습니다. 업데이트된 행의 수: {}", updatedRows);

                // 3. 근태 승인 정보 삽입
                AttendanceApprovalInsertRequestDto insertRequestDto = new AttendanceApprovalInsertRequestDto(
                        attendanceInfoId,
                        employeeId
                );
                int insertedRows = employeeMapper1.insertAttendanceApproval(insertRequestDto);
                log.info("근태 승인 정보를 삽입했습니다. 삽입된 행의 수: {}", insertedRows);

                // 4. 근태 승인 정보를 검색하여 반환
                return employeeMapper1.findAttendanceApproval(employeeId, attendanceInfoId);
        }

        //todo  2번조건이 성립되면 3번으로 넘어가게

        //자신의 근태 승인내역
        @Override
        public List<AttendanceApprovalUpdateRequestDto> findApprovalInfoByMine(String employeeId) {
                List<AttendanceApprovalUpdateRequestDto> approvalInfoList = employeeMapper1.findApprovalInfoByMine(employeeId);

                // 로그를 남깁니다.
                log.info("사원 ID {}에 대한 승인 정보 목록: {}", employeeId, approvalInfoList);

                return approvalInfoList;
        }

        // 본인의 조정요청이력조회
        @Override
        public AttendanceAppealMediateResponseDto findAttendanceInfoByMine(String employeeId) {
                AttendanceAppealMediateResponseDto attendanceInfo = employeeMapper1.findAttendanceInfoByMine(employeeId);

                // 로그를 남깁니다.
                log.info("사원 ID {}에 대한 조정요청 목록: {}", employeeId, attendanceInfo);

                return attendanceInfo;
        }

        //사원검색
        @Override
        public List<EmployeeSearchResponseDto> searchByEmployeeIdOrName(String searchParameter) {
                // 숫자만 있는지 검사합니다.
                if (searchParameter.matches("\\d+")) {
                        // 숫자일 경우, employeeId로 검색
                        return employeeMapper1.searchEmployeeEmployeeId(searchParameter);
                } else {
                        // 문자열일 경우, name으로 검색
                        return employeeMapper1.searchEmployeeName(searchParameter);
                }
        }










}