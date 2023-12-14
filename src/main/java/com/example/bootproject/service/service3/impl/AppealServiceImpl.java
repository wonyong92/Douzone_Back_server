package com.example.bootproject.service.service3.impl;

import com.example.bootproject.repository.mapper3.appeal.AppealMapper;
import com.example.bootproject.repository.mapper3.attendanceinfo.AttendanceInfoMapper;
import com.example.bootproject.repository.mapper3.regular_work_time.RegularWorkTimeMapper;
import com.example.bootproject.service.service3.api.AppealService;
import com.example.bootproject.vo.vo1.request.appeal.AppealProcessRequestDto;
import com.example.bootproject.vo.vo1.request.appeal.AppealRequestDto;
import com.example.bootproject.vo.vo1.response.appeal.AppealRequestResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static com.example.bootproject.system.StaticString.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {
    private final AttendanceInfoMapper attendanceInfoMapper;
    private final AppealMapper appealMapper;
    private final HttpSession session;
    private final RegularWorkTimeMapper regularWorkTimeMapper;
    /*
     *
     * 1. 로그인 정보 확인 - 로그인 확인 API 혹은 로직 사용
     * 2. post - form을 통해 전달 받은 데이터를 유효성 검증 수행
     * 3. 서비스로 입력 데이터 전달
     * 4. 서비스에서 attendance_info_id 에 대해서 조정 요청 튜플이 있는지 확인
     *     1. 분기점 1 : 기존 조정 요청이 있는 경우
     *         1. 중복된 요청임을 알리는 응답을 담은 응답을 전달
     *     2. 분기점 1 : 기존 조정 요청이 없는 경우
     *         1. 분기점 1 - A : attendance_info_id가 잘못된 경우
     *             - dto에 이미 요청이 있음을 담아서 전달
     *         2. 분기점 1 - A : 정상적인 attendance_info_id 인 경우
     *             - 신청 내역 테이블에 insert  수행
     *   - 최종 응답 : generated key를 이용하여 다시 신청 내역을 조회하여 ResponseDto에 담아서 전달
     */

    @Override
    public AppealRequestResponseDto makeAppealRequest(AppealRequestDto dto) {
        Integer attendanceInfoIdExist = attendanceInfoMapper.countByIdEmployeeId(dto.getAttendanceInfoId(), dto.getEmployeeId());
        if (attendanceInfoIdExist == null) {
            //TODO : 근태기록 자체를 찾지 못한 경우 응답 설정
            log.info("attendanceInfoId를 찾지 못함");
            return null;
        }
        if (appealMapper.checkAppealRequestExist(dto.getAttendanceInfoId()) > 0) {
            //TODO : 해당 근태기록에 대해 이미 조정 요청이 있는 경우 처리
            log.info("근태기록에 대해 이미 조정요청이 존재함");
            return null;
        }
        appealMapper.makeRequest(dto);

        log.info("해당 근태 정보(id {})의 현재 상태를 조정 요청 중({})으로 변경", dto.getAttendanceInfoId(), APPEAL_REQUEST_STATE_REQUESTED);
        attendanceInfoMapper.updateAttendanceInfoStatus(APPEAL_REQUEST_STATE_REQUESTED, dto.getAttendanceInfoId());
        AppealRequestResponseDto result = appealMapper.findById(dto.getAttendanceAppealRequestId());
        log.info(" AppealRequestResponseDto result {} {} ",dto.getAttendanceAppealRequestId(), result);
        return result;
    }


    @Override
    public AppealRequestResponseDto processAppealRequest(AppealProcessRequestDto dto) {
        //        1. 사유의 데이터가 null인지 확인
//
//            ⇒ 사유의 데이터가 null : 응답 코드 400
//
//            ⇒ 사유의 데이터가 not null : 응답 코드 200
//
//        3. 가져온 데이터를 분석하여 연차 사용 신청 내역 테이블을 업데이트 하거나 생략
        /*결정이 거절인 경우*/
        AppealRequestResponseDto old = appealMapper.findById(dto.getAttendanceAppealRequestId());
        if (old == null) {
            log.info("근태 정보를 찾을 수 없습니다");
            return null;
        }
        if (!old.getStatus().equals(APPEAL_REQUEST_STATE_REQUESTED)) {
            log.info("이미 처리된 연차 요청은 처리 할 수 없습니다");
            return null;
        }

        log.info("{} {}", old.getEmployeeId(), dto.getEmployeeId());
        if (old.getEmployeeId().equals(dto.getEmployeeId())) {
            log.info("자신의 연차 요청은 처리 할 수 없습니다");
            return null;
        }
        /*요청 번호에 문제가 있는 경우 null 반환*/
        if (old != null) {
            log.info("요청 처리 쓰기 수행 - 기존 데이터 {}", old);
            LocalTime startTime = LocalTime.parse(old.getAppealedStartTime());
            LocalTime endTime = LocalTime.parse(old.getAppealedEndTime());
            Map<String, String> regularWorkTime= regularWorkTimeMapper.getRegularStartEndTime(LocalDate.now().getYear());
            LocalTime regularStartTime= ((Time)(Object)regularWorkTime.get("adjusted_start_time")).toLocalTime();
            LocalTime regularEndTime= ((Time)(Object)regularWorkTime.get("adjusted_end_time")).toLocalTime();

            if (startTime == null && endTime == null) {
                //결근
                attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_ABSENT, old.getAttendanceInfoId());

                log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", old.getAttendanceInfoId(), startTime, endTime, "결근");
            } else if (endTime.isBefore(regularEndTime) && startTime.isBefore(regularStartTime)) {
                //정상 출근 +조기 퇴근
                attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_NORMAL_START_EARLY_END, old.getAttendanceInfoId());
                log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", old.getAttendanceInfoId(), startTime, endTime, "정상 출근 +조기 퇴근");
            } else if (endTime.isBefore(regularEndTime) && startTime.isAfter(regularStartTime)) {
                //지각 출근 +조기 퇴근
                attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_LATE_START_EARLY_END, old.getAttendanceInfoId());
                log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", old.getAttendanceInfoId(), startTime, endTime, "지각 출근 +조기 퇴근");
            } else if (endTime.isAfter(regularEndTime) && startTime.isAfter(regularStartTime)) {
                //지각 출근 + 정규 퇴근
                attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_LATE_START_NORMAL_END, old.getAttendanceInfoId());
                log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", old.getAttendanceInfoId(), startTime, endTime, "지각 출근 + 정규 퇴근");
            } else if (endTime.isAfter(regularEndTime) && startTime.isBefore(regularStartTime)) {
                //정규 출근 + 정규 퇴근
                attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_NORMAL, old.getAttendanceInfoId());
                log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", old.getAttendanceInfoId(), startTime, endTime, "정규 출근 + 정규 퇴근");
            } else if (startTime.isBefore(regularStartTime) && (endTime == null || endTime.isAfter(regularEndTime))) {
                attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_NORMAL_START_NULL_END, old.getAttendanceInfoId());
                log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", old.getAttendanceInfoId(), startTime, endTime, "정상 출근 + 야근 상태");
                //정상 출근 + 야근 상태
            } else if (startTime.isAfter(regularStartTime) && (endTime == null || endTime.isAfter(regularEndTime))) {
                //지각 출근 + 야근 상태
                attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_LATE_START_NULL_END, old.getAttendanceInfoId());
                log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", old.getAttendanceInfoId(), startTime, endTime, "지각 출근 + 야근 상태");
            }


            appealMapper.process(dto);
            if (dto.getStatus().equals(APPEAL_REQUEST_STATE_PERMITTED))
                dto.setReasonForRejection("permitted");
            AppealRequestResponseDto result = appealMapper.findById(dto.getAttendanceAppealRequestId());
            log.info("요청 처리 쓰기 수행 결과 {}", result);
            return result;
        }
        log.info("요청 번호로 요청을 찾을 수 없음");
        return null;
    }
}
