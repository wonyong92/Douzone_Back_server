package com.example.bootproject.system.scheduling;

import com.example.bootproject.repository.mapper3.attendanceinfo.AttendanceInfoMapper;
import com.example.bootproject.repository.mapper3.employee.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JobContainer {
    private final AttendanceInfoMapper attendanceInfoMapper;
    private final EmployeeMapper employeeMapper;

    @Scheduled(cron = "0 0 5 * * *")
    public void autoInsertAttedanceInfo() {

        int affected = attendanceInfoMapper.insertAttendanceInfo();
        log.info("auto insert attendance information affected result {} ", affected);

    }
}
