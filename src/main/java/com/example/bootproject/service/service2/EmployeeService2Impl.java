package com.example.bootproject.service.service2;

import com.example.bootproject.repository.mapper.mapper2.EmployeeMapper2;
import com.example.bootproject.vo.vo2.response.EmployeeDto;
import com.example.bootproject.vo.vo2.response.Page;
import com.example.bootproject.vo.vo2.response.PagingRequestWithIdDto;
import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService2Impl implements  EmployeeService2{

    private final EmployeeMapper2 empMapper2;

    @Override
    public Page<List<VacationRequestDto>> getHistoryOfUsedVacationOfMine(PagingRequestWithIdDto pagingRequestWithIdDto) {

        Page<List<VacationRequestDto>> result = new Page<>();
        int size = result.getSize(); // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestWithIdDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호
        int totalRowCount = empMapper2.getHistoryOfUsedVacationOfMineTotalRow(pagingRequestWithIdDto.getId()); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        String orderByCondition = pagingRequestWithIdDto.getSort(); // 정렬할 컬럼 이름
        if(orderByCondition=="name"){
            orderByCondition = "e."+orderByCondition;
        }
        else{
            orderByCondition="v."+orderByCondition;
        }


        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> getData = empMapper2.getHistoryOfUsedVacationOfMine(size,orderByCondition,startRow,pagingRequestWithIdDto.getSortOrder(), pagingRequestWithIdDto.getId());
        log.info("empMapper2.getHistoryOfUsedVacationOfMine()의 getData : {}",getData);
        result.setData(getData);
        if(pagingRequestWithIdDto.getCurrentPage()<lastPageNumber){
            result.setHasNext(true);
        }
        result.setSort(pagingRequestWithIdDto.getSort());
        result.setDesc(pagingRequestWithIdDto.getSortOrder());
        result.setPage(pagingRequestWithIdDto.getCurrentPage());
        result.setTotalElement(totalRowCount);

        log.info("empMapper2.getHistoryOfUsedVacationOfMine의 getData : {}",result.getData());
        log.info("empMapper2.getHistoryOfUsedVacationOfMine의 getSize : {}",result.getSize());
        log.info("empMapper2.getHistoryOfUsedVacationOfMine의 getHasNext : {}",result.isHasNext());
        log.info("empMapper2.getHistoryOfUsedVacationOfMine의 getSort : {}",result.getSort());
        log.info("empMapper2.getHistoryOfUsedVacationOfMine의 getDesc : {}",result.getDesc());
        log.info("empMapper2.getHistoryOfUsedVacationOfMine의 getPage : {}",result.getPage());
        log.info("empMapper2.getHistoryOfUsedVacationOfMine의 getTotalElement : {}",result.getTotalElement());



        return result;
    }

    @Override
    public Page<List<VacationRequestDto>> getHistoryOfRejectedVacationOfMine(PagingRequestWithIdDto pagingRequestWithIdDto) {
        Page<List<VacationRequestDto>> result = new Page<>();
        int size = result.getSize(); // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestWithIdDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호
        int totalRowCount = empMapper2.getHistoryOfUsedVacationOfMineTotalRow(pagingRequestWithIdDto.getId()); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        String orderByCondition = pagingRequestWithIdDto.getSort(); // 정렬할 컬럼 이름
        if(orderByCondition=="name"){
            orderByCondition = "e."+orderByCondition;
        }
        else{
            orderByCondition="v."+orderByCondition;
        }
        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> getData =  empMapper2.getHistoryOfRejectedVacationOfMine(size,orderByCondition,startRow,pagingRequestWithIdDto.getSortOrder(), pagingRequestWithIdDto.getId());

        log.info("empMapper2.getHistoryOfRejectedVacationOfMine()의 getData : {}",getData);

        result.setData(getData);
        if(pagingRequestWithIdDto.getCurrentPage()<lastPageNumber){
            result.setHasNext(true);
        }
        result.setSort(pagingRequestWithIdDto.getSort());
        result.setDesc(pagingRequestWithIdDto.getSortOrder());
        result.setPage(pagingRequestWithIdDto.getCurrentPage());
        result.setTotalElement(totalRowCount);

        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getData : {}",result.getData());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getSize : {}",result.getSize());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getHasNext : {}",result.isHasNext());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getSort : {}",result.getSort());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getDesc : {}",result.getDesc());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getPage : {}",result.getPage());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getTotalElement : {}",result.getTotalElement());
        return result;
    }

    @Override
    public Page<List<VacationRequestDto>> getHistoryOfUsedVacationOfEmployee(PagingRequestWithIdDto pagingRequestWithIdDto) {
        Page<List<VacationRequestDto>> result = new Page<>();
        int size = result.getSize(); // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestWithIdDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호
        int totalRowCount = empMapper2.getHistoryOfUsedVacationOfMineTotalRow(pagingRequestWithIdDto.getId()); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        String orderByCondition = pagingRequestWithIdDto.getSort(); // 정렬할 컬럼 이름
        if(orderByCondition=="name"){
            orderByCondition = "e."+orderByCondition;
        }
        else{
            orderByCondition="v."+orderByCondition;
        }
        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> getData =  empMapper2.getHistoryOfUsedVacationOfEmployee(size,orderByCondition,startRow,pagingRequestWithIdDto.getSortOrder(), pagingRequestWithIdDto.getId());

        log.info("empMapper2.getHistoryOfUsedVacationOfEmployee()의 getData : {}",getData);

        result.setData(getData);
        if(pagingRequestWithIdDto.getCurrentPage()<lastPageNumber){
            result.setHasNext(true);
        }
        result.setSort(pagingRequestWithIdDto.getSort());
        result.setDesc(pagingRequestWithIdDto.getSortOrder());
        result.setPage(pagingRequestWithIdDto.getCurrentPage());
        result.setTotalElement(totalRowCount);

        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getData : {}",result.getData());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getSize : {}",result.getSize());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getHasNext : {}",result.isHasNext());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getSort : {}",result.getSort());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getDesc : {}",result.getDesc());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getPage : {}",result.getPage());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine의 getTotalElement : {}",result.getTotalElement());
        return result;


    }
}
