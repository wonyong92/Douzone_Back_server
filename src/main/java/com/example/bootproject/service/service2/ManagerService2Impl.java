package com.example.bootproject.service.service2;

import com.example.bootproject.repository.mapper.mapper2.ManagerMapper2;
import com.example.bootproject.vo.vo2.request.DefaultVacationRequestDto;
import com.example.bootproject.vo.vo2.response.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.bootproject.vo.vo2.response.Page.PAGE_SIZE;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManagerService2Impl implements  ManagerService2{

    private final ManagerMapper2 manMapper2;
    @Override
    public Page<List<VacationRequestDto>> getAllVacationHistory(PagingRequestWithDateDto pagingRequestWithDateDto) {

        int size = PAGE_SIZE; // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestWithDateDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호

        String orderByCondition = pagingRequestWithDateDto.getSort(); // 정렬할 컬럼 이름
//        orderByCondition = (orderByCondition.equals("name")?"E.":"V.")+orderByCondition;
        log.info("size, orderByCondition,startRow,sortOrder : {},{},{},{}",size,orderByCondition,startRow,pagingRequestWithDateDto.getSortOrder());
        List<VacationRequestDto> getData = manMapper2.getAllVacationHistory(size,orderByCondition,startRow,pagingRequestWithDateDto.getSortOrder(),pagingRequestWithDateDto.getDate()); // 현재 페이지에 대해서 size만큼 orderByCondition 정렬 조건에 맞추어 startRow부터 데이터를 가져온다
        log.info("manMapper2.getEmpInfo의 getData : {}",getData);

        if(getData.isEmpty()){
            Page<List<VacationRequestDto>> pageObj = new Page();
            pageObj.setData(new ArrayList<>());
            return pageObj;
        }

        int totalRowCount = manMapper2.getAllVacationRequestCountByDate(pagingRequestWithDateDto.getDate()); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestWithDateDto.getCurrentPage()<lastPageNumber?true:false);

        Page<List<VacationRequestDto>> result = new Page<>(getData,isLastPage, pagingRequestWithDateDto.getSort(),pagingRequestWithDateDto.getSortOrder(),pagingRequestWithDateDto.getCurrentPage(),totalRowCount);

        return result;
    }

    @Override
    public Page<List<VacationRequestDto>> getHistoryVacationOfEmployee(PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto) {
        int size = PAGE_SIZE; // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestWithIdStatusDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호
        String orderByCondition = pagingRequestWithIdStatusDto.getSort(); // 정렬할 컬럼 이름

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> getData =  manMapper2.getHistoryOfVacationOfEmployee(size,orderByCondition,startRow,pagingRequestWithIdStatusDto.getSortOrder(), pagingRequestWithIdStatusDto.getId(),pagingRequestWithIdStatusDto.getStatus());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine()의 getData : {}",getData);

        if(getData.isEmpty()){
            Page<List<VacationRequestDto>> pageObj = new Page();
            pageObj.setData(new ArrayList<>());
            return pageObj;
        }

        int totalRowCount = manMapper2.getHistoryOfVacationOfEmployeeTotalRow(pagingRequestWithIdStatusDto.getId(),pagingRequestWithIdStatusDto.getStatus()); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestWithIdStatusDto.getCurrentPage()<lastPageNumber?true:false);

        Page<List<VacationRequestDto>> result = new Page<>(getData,isLastPage,pagingRequestWithIdStatusDto.getSort(),pagingRequestWithIdStatusDto.getSortOrder(),pagingRequestWithIdStatusDto.getCurrentPage(),totalRowCount);
        return result;
    }



    @Override
    public Page<List<SettingWorkTimeDto>> getSettingWorkTime(PagingRequestDto pagingRequestDto) {
        int size = PAGE_SIZE; // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호
        String orderByCondition = pagingRequestDto.getSort(); // 정렬할 컬럼 이름

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<SettingWorkTimeDto> getData =  manMapper2.getSettingWorkTime(size,orderByCondition,startRow,pagingRequestDto.getSortOrder());
        log.info("manMapper2.getSettingWorkTime의 getData : {}",getData);

        if(getData.isEmpty()){
            Page<List<SettingWorkTimeDto>> pageObj = new Page();
            pageObj.setData(new ArrayList<>());
            return pageObj;
        }

        int totalRowCount = manMapper2.getSettingWorkTimeCount(); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestDto.getCurrentPage()<lastPageNumber?true:false);

        Page<List<SettingWorkTimeDto>> result = new Page<>(getData,isLastPage,pagingRequestDto.getSort(),pagingRequestDto.getSortOrder(),pagingRequestDto.getCurrentPage(),totalRowCount);
        return result;
    }



    @Override
    public Page<List<VacationQuantitySettingDto>> getVacationSettingHistory(PagingRequestDto pagingRequestDto) {
        int size = PAGE_SIZE; // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호
        String orderByCondition = pagingRequestDto.getSort(); // 정렬할 컬럼 이름

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationQuantitySettingDto> getData =  manMapper2.getVacationSettingHistory(size,orderByCondition,startRow,pagingRequestDto.getSortOrder());
        log.info("manMapper2.getVacationSettingHistory의 getData : {}",getData);

        if(getData.isEmpty()){
            Page<List<VacationQuantitySettingDto>> pageObj = new Page();
            pageObj.setData(new ArrayList<>());
            return pageObj;
        }

        int totalRowCount = manMapper2.getVacationSettingHistoryCount(); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestDto.getCurrentPage()<lastPageNumber?true:false);

        Page<List<VacationQuantitySettingDto>> result = new Page<>(getData,isLastPage,pagingRequestDto.getSort(),pagingRequestDto.getSortOrder(),pagingRequestDto.getCurrentPage(),totalRowCount);
        return result;



    }

    @Override
    public DefaultVacationResponseDto makeDefaultVacationResponse(DefaultVacationRequestDto dto) { //dto에는 1년 미만 일 때 개수, 1년 이상일 때 개수, 대상 날짜의 데이터만 존재함

        manMapper2.insertDefaultVacation(dto); //데이터 insert

        DefaultVacationResponseDto result = manMapper2.getDefaultVacationResponseDto(dto.getSettingKey());
        log.info("manMapper2.getDefaultVacationResponseDto(dto.getSettingKey())의 result : {}",result);


        return result; // 데이터 select해서 반환

    }

    @Override
    public int getDefaultSettingValue(String employeeId) {
       int year = manMapper2.getHireYear(employeeId); //입사 연도 들고옴
       log.info("getHireYear(employeeId) : {}",year);

       int setting = manMapper2.getDefaultSettingVacationValue(year); //입사 연도에 따른 기본 연차 설정값 들고옴
       log.info("manMapper2.getDefaultSettingVacationValue(year) : {}",setting);

       /* 조정된 연차 개수를 들고 와서, 기본 연차 설정 값과 더한 결과*/
        // 만약 조정된 것이 없다면? 0이 리턴되도록
       int thisYearSettingVacationValue = setting+manMapper2.getVacationAdjustedHistory(employeeId);
       log.info("조정된 연차 개수 데이터 : {}",manMapper2.getVacationAdjustedHistory(employeeId));
       log.info("기본 연차 설정 값 + 조정된 연차 개수 데이터 : {}",thisYearSettingVacationValue);
       //

        /* 연차 신청 결과 승인인 튜플 중 vacation_quantity의 총합 */
       int approveVacationSum = manMapper2.getApproveVacationQuantity(employeeId);
       log.info("올해 연차 승인 개수 합 : {}", approveVacationSum);

       return thisYearSettingVacationValue-approveVacationSum; //기본값 + 조정값 - 승인 튜플 수
    }


}
