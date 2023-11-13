//package com.example.bootproject.service.service2;
//
//import com.example.bootproject.vo.vo1.request.DefaultVacationRequestDto;
//import com.example.bootproject.vo.vo1.request.PagingRequestDto;
//import com.example.bootproject.vo.vo1.request.PagingRequestWithDateDto;
//import com.example.bootproject.vo.vo1.request.PagingRequestWithIdStatusDto;
//import com.example.bootproject.vo.vo1.response.DefaultVacationResponseDto;
//import com.example.bootproject.vo.vo1.response.SettingWorkTimeDto;
//import com.example.bootproject.vo.vo1.response.VacationQuantitySettingDto;
//import com.example.bootproject.vo.vo1.response.VacationRequestDto;
//import com.example.bootproject.vo.vo1.response.Page;
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
