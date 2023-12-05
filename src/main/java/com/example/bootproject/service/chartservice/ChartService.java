package com.example.bootproject.service.chartservice;

import com.example.bootproject.vo.vo1.response.Chart.ResponseVacationEmployeeChart;

public interface ChartService {

    public ResponseVacationEmployeeChart getEmployeeVacationChart(String employeeId);

     int getApprovalMonthUseVacation(int year,int month , String employeeId);

     int getRequestedMonthUseVacation(int year , int month, String employeeId);

     int getRejectedMonthUseVacation(int year, int month , String employeeId);



    int getApprovalMonthUseAttendance(int year, int month, String employeeId);
    int getNoApprovalMonthUseAttendance(int year, int month, String employeeId);

    int getApprovalRequestedAttendance(int year, int month , String employeeId);


    //아현 작업 아래 6개 - service
    int getAllEmployeesRequestedVacation(int year, int month);

    int getAllEmployeesApprovalVacation(int year, int month);

    int getAllEmployeesRejectedVacation(int year, int month);

    int getAllEmployeesAttendanceRequested(int year, int month);

    int getAllEmployeesAttendanceNormal(int year, int month);

    int getAllEmployeesAttendanceAbnormal(int year, int month);
}
