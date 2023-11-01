package com.example.bootproject.service.service2;

import com.example.bootproject.repository.mapper.mapper2.AdminMapper2;
import com.example.bootproject.vo.vo2.response.EmployeeDto;
import com.example.bootproject.vo.vo2.response.Page;
import com.example.bootproject.vo.vo2.response.PagingRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService2Impl implements AdminService2 {
    private final AdminMapper2 mapper;

    @Override
    public Page<List<EmployeeDto>> getEmpInfo(PagingRequestDto pagingRequestDto) {

        Page<List<EmployeeDto>> result = new Page<>();
        int size = result.getSize(); // Page 객체로부터 size를 가져옴
        int startRow = (pagingRequestDto.getCurrentPage()-1)*size; // 가져오기 시작할 row의 번호
        int totalRowCount = mapper.getEmpInfoTotalRow(); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호

        String orderByCondition = pagingRequestDto.getSort(); // 정렬할 컬럼 이름
        /*if (desc&&!getSort.isEmpty()) { // 내림차순 적용 True일 때 orderByCondition 뒤에 desc를 붙인다

            orderByCondition += " desc";
        }*/

        List<EmployeeDto> getData = mapper.getEmpInfo(pagingRequestDto.getCurrentPage(),size,orderByCondition,startRow,pagingRequestDto.getSortOrder()); // 현재 페이지에 대해서 size만큼 orderByCondition 정렬 조건에 맞추어 startRow부터 데이터를 가져온다
        log.info("mapper.getEmpInfo()의 result : {}",result);


        result.setData(getData);
        if(pagingRequestDto.getCurrentPage()<lastPageNumber){
            result.setHasNext(true);
        }
        result.setSort(pagingRequestDto.getSort());
        result.setDesc(pagingRequestDto.getSortOrder());
        result.setPage(pagingRequestDto.getCurrentPage());
        result.setTotalElement(totalRowCount);

        log.info("mapper.getEmpInfo()의 getData : {}",result.getData());
        log.info("mapper.getEmpInfo()의 getSize : {}",result.getSize());
        log.info("mapper.getEmpInfo()의 getHasNext : {}",result.isHasNext());
        log.info("mapper.getEmpInfo()의 getSort : {}",result.getSort());
        log.info("mapper.getEmpInfo()의 getDesc : {}",result.getDesc());
        log.info("mapper.getEmpInfo()의 getPage : {}",result.getPage());
        log.info("mapper.getEmpInfo()의 getTotalElement : {}",result.getTotalElement());


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
