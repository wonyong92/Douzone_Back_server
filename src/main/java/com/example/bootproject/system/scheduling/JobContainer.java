package com.example.bootproject.system.scheduling;

import com.example.bootproject.repository.mapper3.attendanceinfo.AttendanceInfoMapper;
import com.example.bootproject.repository.mapper3.employee.EmployeeMapper;
import com.example.bootproject.vo.vo3.response.attendance.AttendanceCheckResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.bootproject.system.StaticString.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JobContainer {

    private final AttendanceInfoMapper attendanceInfoMapper;
    private final EmployeeMapper employeeMapper;

    @Scheduled(cron = "0 30 5 * * *")
    public void autoInsertAttedanceInfo() {
        int affected = attendanceInfoMapper.insertAttendanceInfo();
        log.info("auto insert attendance information affected result {} ", affected);
    }

    @Scheduled(cron = "0 30 19 * * *")
    public void autoCheckAttendanceStatus() {
        log.info("auto check attendance information affected start");
        List<AttendanceCheckResponse> infos = attendanceInfoMapper.getListAttendanceInfoOfTodayAfterAutoInsert();
        AtomicInteger[] affected = new AtomicInteger[1];
        affected[0] = new AtomicInteger(0);
        //Todo : 정규 출근 시간, 정규 퇴근 시간 불러오기
        LocalDateTime regularEndTime = LocalDateTime.now().minusHours(1L);
        LocalDateTime regularStartTime = LocalDateTime.now().minusHours(9L);
        infos.stream().forEach(
                info -> {
                    Long attendanceInfoId = info.getAttendanceInfoId();
                    LocalDateTime startTime = info.getStartTime();
                    LocalDateTime endTime = info.getEndTime();
                    if (startTime == null && endTime == null) {
                        //결근
                        attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_ABSENT, info.getAttendanceInfoId());
                        log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", attendanceInfoId, startTime, endTime, "결근");
                    } else if (endTime.isBefore(regularEndTime) && startTime.isBefore(regularStartTime)) {
                        //정상 출근 +조기 퇴근
                        attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_NORMAL_START_EARLY_END, info.getAttendanceInfoId());
                        log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", attendanceInfoId, startTime, endTime, "정상 출근 +조기 퇴근");
                    } else if (endTime.isBefore(regularEndTime) && startTime.isAfter(regularStartTime)) {
                        //지각 출근 +조기 퇴근
                        attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_LATE_START_EARLY_END, info.getAttendanceInfoId());
                        log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", attendanceInfoId, startTime, endTime, "지각 출근 +조기 퇴근");
                    } else if (endTime.isAfter(regularEndTime) && startTime.isAfter(regularStartTime)) {
                        //지각 출근 + 정규 퇴근
                        attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_LATE_START_NORMAL_END, info.getAttendanceInfoId());
                        log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", attendanceInfoId, startTime, endTime, "지각 출근 + 정규 퇴근");
                    } else if (endTime.isAfter(regularEndTime) && startTime.isBefore(regularStartTime)) {
                        //정규 출근 + 정규 퇴근
                        attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_NORMAL, info.getAttendanceInfoId());
                        log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", attendanceInfoId, startTime, endTime, "정규 출근 + 정규 퇴근");
                    } else if (startTime.isBefore(regularStartTime) && (endTime == null || endTime.isAfter(regularEndTime))) {
                        attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_NORMAL_START_NULL_END, info.getAttendanceInfoId());
                        log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", attendanceInfoId, startTime, endTime, "정상 출근 + 야근 상태");
                        //정상 출근 + 야근 상태
                    } else if (startTime.isAfter(regularStartTime) && (endTime == null || endTime.isAfter(regularEndTime))) {
                        //지각 출근 + 야근 상태
                        attendanceInfoMapper.updateAttendanceInfoStatus(ATTENDANCE_INFO_STATUS_LATE_START_NULL_END, info.getAttendanceInfoId());
                        log.info("attendanceInfoId = {}, startTime = {} endTime = {} result = {}", attendanceInfoId, startTime, endTime, "지각 출근 + 야근 상태");
                    }
                    affected[0].incrementAndGet();
                }
        );
        /*
         * 근태 이상 종류
         *
         * 결근
         * 지각 - 출근과 합치기
         * 지각 + 조기퇴근
         * 정상 출근 + 조기 퇴근
         * 야근 상태
         * */
        log.info("auto check attendance information affected result {} ", affected[0].get());
    }

}
