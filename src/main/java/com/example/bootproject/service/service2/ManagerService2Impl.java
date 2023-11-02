package com.example.bootproject.service.service2;

import com.example.bootproject.repository.mapper.mapper2.ManagerMapper2;
import com.example.bootproject.vo.vo2.response.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.bootproject.vo.vo2.response.Page.PAGE_SIZE;

@Service
@RequiredArgsConstructor
@Slf4j
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
           // return 빈 리스트의 Page
            return new Page<List<VacationRequestDto>>();
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
            return new Page<List<VacationRequestDto>>();
        }

        int totalRowCount = manMapper2.getHistoryOfVacationOfEmployeeTotalRow(pagingRequestWithIdStatusDto.getId(),pagingRequestWithIdStatusDto.getStatus()); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestWithIdStatusDto.getCurrentPage()<lastPageNumber?true:false);

        Page<List<VacationRequestDto>> result = new Page<>(getData,isLastPage,pagingRequestWithIdStatusDto.getSort(),pagingRequestWithIdStatusDto.getSortOrder(),pagingRequestWithIdStatusDto.getCurrentPage(),totalRowCount);
        return result;
    }



    @Override
    public Page<List<SettingWorkTimeDto>> getSettingWorkTime(PagingRequestDto pagingRequestDto) {

        Page<List<SettingWorkTimeDto>> result = new Page<>();
        int size = result.getSize(); // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호
        int totalRowCount = manMapper2.getSettingWorkTimeCount(); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        String orderByCondition = pagingRequestDto.getSort(); // 정렬할 컬럼 이름
        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<SettingWorkTimeDto> getData =  manMapper2.getSettingWorkTime(size,orderByCondition,startRow,pagingRequestDto.getSortOrder());
        log.info("manMapper2.getSettingWorkTime의 getData : {}",getData);

        result.setData(getData);
        if(pagingRequestDto.getCurrentPage()<lastPageNumber){
            result.setHasNext(true);
        }
        result.setSort(pagingRequestDto.getSort());
        result.setDesc(pagingRequestDto.getSortOrder());
        result.setPage(pagingRequestDto.getCurrentPage());
        result.setTotalElement(totalRowCount);

        return result;

    }
    @Override
    public Page<List<VacationQuantitySettingDto>> getVacationSettingHistory(PagingRequestDto pagingRequestDto) {

        Page<List<VacationQuantitySettingDto>> result = new Page<>();
        int size = result.getSize(); // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호

        int totalRowCount = manMapper2.getVacationSettingHistoryCount(); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        String orderByCondition = pagingRequestDto.getSort(); // 정렬할 컬럼 이름
        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationQuantitySettingDto> getData =  manMapper2.getVacationSettingHistory(size,orderByCondition,startRow,pagingRequestDto.getSortOrder());
        log.info("manMapper2.getVacationSettingHistory의 getData : {}",getData);

        result.setData(getData);
        if(pagingRequestDto.getCurrentPage()<lastPageNumber){
            result.setHasNext(true);
        }
        result.setSort(pagingRequestDto.getSort());
        result.setDesc(pagingRequestDto.getSortOrder());
        result.setPage(pagingRequestDto.getCurrentPage());
        result.setTotalElement(totalRowCount);

        return result;

    }


}
