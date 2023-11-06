package com.example.bootproject.service.service2;


import com.example.bootproject.repository.mapper2.AdminMapper2;
import com.example.bootproject.vo.vo2.request.PagingRequestDto;
import com.example.bootproject.vo.vo2.response.EmployeeDto;
import com.example.bootproject.vo.vo3.response.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.bootproject.vo.vo3.response.Page.PAGE_SIZE;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService2Impl implements AdminService2 {
    private final AdminMapper2 mapper;

    @Override
    public Page<List<EmployeeDto>> getEmpInfo(PagingRequestDto pagingRequestDto) {

        int size = PAGE_SIZE; // 한 페이지에 출력할 게시물의 개수
        int startRow = (pagingRequestDto.getCurrentPage() - 1) * size; // 가져오기 시작할 row의 번호
        String orderByCondition = pagingRequestDto.getSort(); // 정렬할 컬럼 이름

        /* 현재 페이지에 대해서 size만큼 orderByCondition 정렬 조건에 맞추어 startRow부터 데이터를 가져온다 */
        List<EmployeeDto> getData = mapper.getEmpInfo(size, orderByCondition, startRow, pagingRequestDto.getSortOrder());
        log.info("mapper.getEmpInfo()의 getData : {}", getData);

        /* 가져온 데이터가 비어있다면 Page 객체를 새로 생성하고, Page 객체 중 가져온 데이터를 담는 속성에 빈 ArrayList를 생성하여 리턴한다 */
        if (getData.isEmpty()) {
            Page<List<EmployeeDto>> pageObj = new Page();
            pageObj.setData(new ArrayList<>());
            return pageObj;
        }

        int totalRowCount = mapper.getEmpInfoTotalRow(); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestDto.getCurrentPage() < lastPageNumber); // 마지막 페이지 여부

        /* 생성자 이용해 Page 객체 생성, 리턴*/
        Page<List<EmployeeDto>> result = new Page<>(getData, isLastPage, pagingRequestDto.getSort(), pagingRequestDto.getSortOrder(), pagingRequestDto.getCurrentPage(), totalRowCount);
        return result;


    }

    @Override
    public EmployeeDto getOneEmpInfo(String employeeId) {

        // 한명에 대한 정보를 받아와 리턴해줌
        EmployeeDto result = mapper.getOneEmpInfo(employeeId);
        log.info("mapper.getOneEmpInfo(employeeId)의 result : {}", result);
        return result;
    }

    // 받아온 id가 실제 테이블에 존재하는지 확인
    @Override
    public int getEmployeeCheck(String id) {
        return mapper.getEmployeeCheck(id); // 실제 테이블에 id가 존재하면 1 반환됨
    }

}
