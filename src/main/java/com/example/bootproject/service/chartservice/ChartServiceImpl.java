package com.example.bootproject.service.chartservice;

import com.example.bootproject.repository.chartmapper.ChartMapper;
import com.example.bootproject.repository.mapper1.ManagerMapper1;
import com.example.bootproject.vo.vo1.response.Chart.ResponseVacationEmployeeChart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChartServiceImpl implements  ChartService{

    private final ChartMapper chartMapper;

    private final ManagerMapper1 managerMapper1;


    @Override
    public ResponseVacationEmployeeChart getEmployeeVacationChart(String employeeId) {
        int year = managerMapper1.getHireYear(employeeId);
        log.info("입사년도 가져오는 데이터" , year);

        int setting = managerMapper1.getDefaultSettingVacationValue(year);
        log.info("입사년도에 따른 기본 연차 설정값" ,setting);

        int totalVacation = setting+ managerMapper1.getVacationAdjustedHistory(employeeId);
        log.info("조정된 연차 개수 데이터:{}", managerMapper1.getVacationAdjustedHistory(employeeId));
        log.info("기본 연차 설정 값 + 조정된 연차 개수 데이터:{}" , totalVacation);

        int useVacation = managerMapper1.getApproveVacationQuantity(employeeId);
        log.info("올헤 연차 승인 개수 합: {}",useVacation);


        return new ResponseVacationEmployeeChart(totalVacation,useVacation);
    }

    @Override
    public int getApprovalMonthUseVacation(int year, int month, String employeeId) {
        log.info("넘어오는데이터" , year,month,employeeId);
        return chartMapper.countApprovalVacationMonthDays(year,month,employeeId);
    }

    @Override
    public int getRequestedMonthUseVacation(int year, int month, String employeeId) {
        log.info("넘어오는데이터" , year,month,employeeId);
        return chartMapper.countRequestedVacationMonthDays(year,month,employeeId);
    }

    @Override
    public int getRejectedMonthUseVacation(int year, int month, String employeeId) {
        log.info("넘어오는 데이터" , year,month,employeeId);
        return chartMapper.countRejectedVacationMonthDays(year, month,employeeId);
    }

    @Override
    public int getApprovalMonthUseAttendance(int year, int month, String employeeId) {
        return chartMapper.countNormalAttendance(employeeId,year,month);
    }

    @Override
    public int getNoApprovalMonthUseAttendance(int year, int month, String employeeId) {
        return chartMapper.countAbnormalAttendance(employeeId,year,month);
    }

    @Override
    public int getApprovalRequestedAttendance(int year, int month, String employeeId) {
        return chartMapper.countRequestedAttendance(employeeId,year,month);
    }


    //아현 작업 아래 6개 - serviceimpl
    @Override
    public int getAllEmployeesRequestedVacation(int year, int month) {
        return chartMapper.getAllEmployeesRequestedVacation(year,month);
    }

    @Override
    public int getAllEmployeesApprovalVacation(int year, int month) {
        return chartMapper.getAllEmployeesApprovalVacation(year,month);
    }

    @Override
    public int getAllEmployeesRejectedVacation(int year, int month) {
        return chartMapper.getAllEmployeesRejectedVacation(year,month);
    }

    @Override
    public int getAllEmployeesAttendanceRequested(int year, int month) {
        return chartMapper.getAllEmployeesAttendanceRequested(year,month);
    }

    @Override
    public int getAllEmployeesAttendanceNormal(int year, int month) {
        return chartMapper.getAllEmployeesAttendanceNormal(year,month);
    }

    @Override
    public int getAllEmployeesAttendanceAbnormal(int year, int month) {
        return chartMapper.getAllEmployeesAttendanceAbnormal(year,month);
    }


}
