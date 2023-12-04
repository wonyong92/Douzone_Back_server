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
}
