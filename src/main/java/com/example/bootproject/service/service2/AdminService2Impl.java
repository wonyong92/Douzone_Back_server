package com.example.bootproject.service.service2;

import com.example.bootproject.repository.mapper.mapper2.AdminMapper2;
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
public class AdminService2Impl implements AdminService2 {
    private final AdminMapper2 mapper;

    @Override
    public Page<List<EmployeeDto>> getEmpInfo(PagingRequestDto pagingRequestDto) {

        int size = PAGE_SIZE; // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호
        String orderByCondition = pagingRequestDto.getSort(); // 정렬할 컬럼 이름
        List<EmployeeDto> getData = mapper.getEmpInfo(size,orderByCondition,startRow,pagingRequestDto.getSortOrder()); // 현재 페이지에 대해서 size만큼 orderByCondition 정렬 조건에 맞추어 startRow부터 데이터를 가져온다
        log.info("mapper.getEmpInfo()의 getData : {}",getData);

        if(getData.isEmpty()){
            Page<List<EmployeeDto>> pageObj = new Page();
            pageObj.setData(new ArrayList<>());
            return pageObj;
        }

        int totalRowCount = mapper.getEmpInfoTotalRow(); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestDto.getCurrentPage()<lastPageNumber?true:false);

        Page<List<EmployeeDto>> result = new Page<>(getData,isLastPage,pagingRequestDto.getSort(),pagingRequestDto.getSortOrder(),pagingRequestDto.getCurrentPage(),totalRowCount);
        return result;


        // mapper.getEmpInfo()의 결과가 에러일 경우 500 에러 발생
        //    => DB의 연결을 끊어 에러 발생 가능, DB의 주소 값을 바꾸어 에러 발생 가능
        // controlleradvice로 하거나, try-catch문으로 null값 리턴해 명시
        //    => 공통적이기 때문에 controlleradvice로 처리

    }

    @Override
    public EmployeeDto getOneEmpInfo(String employeeId) {
        /* 값이 비어 null이 아닌, 에러로 인해 null이 발생 할 수 있다
        *  현재는 값이 비어 null인 경우, 204 No Content 반환 으로 구현 */
        EmployeeDto result = mapper.getOneEmpInfo(employeeId);
        log.info("mapper.getOneEmpInfo(employeeId)의 result : {}",result);
        return result;
    }

}
