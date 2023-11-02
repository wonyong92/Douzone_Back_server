package com.example.bootproject.service.service2;

import com.example.bootproject.repository.mapper.mapper2.EmployeeMapper2;
import com.example.bootproject.vo.vo2.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.bootproject.vo.vo2.response.Page.PAGE_SIZE;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService2Impl implements  EmployeeService2{

    private final EmployeeMapper2 empMapper2;

    @Override
    public Page<List<VacationRequestDto>> getHistoryOfVacationOfMine(PagingRequestWithIdStatusDto pagingRequestWithIdStatusDto) {
        int size = PAGE_SIZE; // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestWithIdStatusDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호
        String orderByCondition = pagingRequestWithIdStatusDto.getSort(); // 정렬할 컬럼 이름

        /* result에 어떠한 데이터도 담기지 않으면 null이 아닌 빈 List 형임*/
        List<VacationRequestDto> getData =  empMapper2.getHistoryOfVacationOfMine(size,orderByCondition,startRow,pagingRequestWithIdStatusDto.getSortOrder(), pagingRequestWithIdStatusDto.getId(),pagingRequestWithIdStatusDto.getStatus());
        log.info("empMapper2.getHistoryOfRejectedVacationOfMine()의 getData : {}",getData);

        if(getData.isEmpty()){
            return new Page<List<VacationRequestDto>>();
        }

        int totalRowCount = empMapper2.getHistoryOfVacationOfMineTotalRow(pagingRequestWithIdStatusDto.getId(),pagingRequestWithIdStatusDto.getStatus()); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestWithIdStatusDto.getCurrentPage()<lastPageNumber?true:false);

        Page<List<VacationRequestDto>> result = new Page<>(getData,isLastPage,pagingRequestWithIdStatusDto.getSort(),pagingRequestWithIdStatusDto.getSortOrder(),pagingRequestWithIdStatusDto.getCurrentPage(),totalRowCount);
        return result;
    }


}
