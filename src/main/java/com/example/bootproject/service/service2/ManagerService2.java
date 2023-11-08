//package com.example.bootproject.service.service2;
//
//import com.example.bootproject.vo.vo2.request.DefaultVacationRequestDto;
//import com.example.bootproject.vo.vo2.request.PagingRequestDto;
//import com.example.bootproject.vo.vo2.request.PagingRequestWithDateDto;
//import com.example.bootproject.vo.vo2.request.PagingRequestWithIdStatusDto;
//import com.example.bootproject.vo.vo2.response.DefaultVacationResponseDto;
//import com.example.bootproject.vo.vo2.response.SettingWorkTimeDto;
//import com.example.bootproject.vo.vo2.response.VacationQuantitySettingDto;
//import com.example.bootproject.vo.vo2.response.VacationRequestDto;
//import com.example.bootproject.vo.vo3.response.Page;
//
//import java.util.List;
//
//public interface ManagerService2 {
//    Page<List<VacationRequestDto>> getAllVacationHistory(PagingRequestWithDateDto pagingRequestWithDateDto);
//
//    Page<List<VacationRequestDto>> getHistoryVacationOfEmployee(PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto);
//
//    Page<List<SettingWorkTimeDto>> getSettingWorkTime(PagingRequestDto pagingRequestDto);
//
//    Page<List<VacationQuantitySettingDto>> getVacationSettingHistory(PagingRequestDto pagingRequestDto);
//
//    DefaultVacationResponseDto makeDefaultVacationResponse(DefaultVacationRequestDto dto);
//
//    int getDefaultSettingValue(String employeeId);
//
//    int getEmployeeCheck(String id);
//
//}
