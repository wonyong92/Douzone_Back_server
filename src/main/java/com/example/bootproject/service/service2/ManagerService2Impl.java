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

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerService2Impl implements  ManagerService2{

    private final ManagerMapper2 manMapper2;
    @Override
    public Page<List<VacationRequestDto>> getAllVacationHistory(PagingRequestWithDateDto pagingRequestWithDateDto) {
        Page<List<VacationRequestDto>> result = new Page<>();
        int size = result.getSize(); // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestWithDateDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호
        int totalRowCount = manMapper2.getAllVacationRequestCountByDate(pagingRequestWithDateDto.getDate()); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        String orderByCondition = pagingRequestWithDateDto.getSort(); // 정렬할 컬럼 이름
        if(orderByCondition=="name"){
            orderByCondition = "e."+orderByCondition;
        }
        else{
            orderByCondition="v."+orderByCondition;
        }
        List<VacationRequestDto> getData = manMapper2.getAllVacationHistory(size,orderByCondition,startRow,pagingRequestWithDateDto.getSortOrder(),pagingRequestWithDateDto.getDate()); // 현재 페이지에 대해서 size만큼 orderByCondition 정렬 조건에 맞추어 startRow부터 데이터를 가져온다
        log.info("manMapper2.getEmpInfo의 getData : {}",getData);


        result.setData(getData);
        if(pagingRequestWithDateDto.getCurrentPage()<lastPageNumber){
            result.setHasNext(true);
        }
        result.setSort(pagingRequestWithDateDto.getSort());
        result.setDesc(pagingRequestWithDateDto.getSortOrder());
        result.setPage(pagingRequestWithDateDto.getCurrentPage());
        result.setTotalElement(totalRowCount);


        return result;


    }
    @Override
    public Page<List<VacationRequestDto>> getEmpReqVacationHistory(PagingRequestWithIdDto pagingRequestWithIdDto) {
        Page<List<VacationRequestDto>> result = new Page<>();
        int size = result.getSize(); // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestWithIdDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호
        int totalRowCount = manMapper2.getEmpReqVaationHistoryCount(pagingRequestWithIdDto.getId()); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        String orderByCondition = pagingRequestWithIdDto.getSort(); // 정렬할 컬럼 이름
        if(orderByCondition=="name"){
            orderByCondition = "e."+orderByCondition;
        }
        else{
            orderByCondition="v."+orderByCondition;
        }
        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> getData =  manMapper2.getEmpReqVacationHistory(size,orderByCondition,startRow,pagingRequestWithIdDto.getSortOrder(), pagingRequestWithIdDto.getId());
        log.info("manMapper2.getEmpReqVacationHistory의 getData : {}",getData);

        result.setData(getData);
        if(pagingRequestWithIdDto.getCurrentPage()<lastPageNumber){
            result.setHasNext(true);
        }
        result.setSort(pagingRequestWithIdDto.getSort());
        result.setDesc(pagingRequestWithIdDto.getSortOrder());
        result.setPage(pagingRequestWithIdDto.getCurrentPage());
        result.setTotalElement(totalRowCount);

        return result;
    }
    @Override
    public List<SettingWorkTimeDto> getSettingWorkTime() {

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<SettingWorkTimeDto> result = manMapper2.getSettingWorkTime();

        log.info("manMapper2.getSettingWorkTime()의 result : {}",result);
        return result;
    }
    @Override
    public List<VacationQuantitySettingDto> getVacationSettingHistory() {

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationQuantitySettingDto> result= manMapper2.getVacationSettingHistory();

        log.info("manMapper2.getVacationSettingHistory()의 result : {}",result);
        return result;
    }

    @Override
    public List<VacationRequestDto> getHistoryOfRejectedVacationOfEmployee(String employeeId) {

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> result = manMapper2.getHistoryOfRejectedVacationOfEmployee(employeeId);

        log.info("manMapper2.getHistoryOfRejectedVacationOfEmployee(employeeId)의 result : {}",result);
        return result;
    }
}
