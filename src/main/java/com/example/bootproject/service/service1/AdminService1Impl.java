package com.example.bootproject.service.service1;


import com.example.bootproject.entity.Employee;
import com.example.bootproject.repository.mapper1.AdminMapper;
import com.example.bootproject.repository.mapper1.EmployeeMapper1;
import com.example.bootproject.vo.vo1.request.EmployeeInsertRequestDto;
import com.example.bootproject.vo.vo1.request.EmployeeUpdateRequestDto;
import com.example.bootproject.vo.vo1.response.EmployeeResponseDto;
import com.example.bootproject.vo.vo1.request.PagingRequestDto;
import com.example.bootproject.vo.vo1.response.EmployeeDto;
import com.example.bootproject.vo.vo1.response.Page;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.bootproject.vo.vo1.response.Page.PAGE_SIZE;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdminService1Impl implements AdminService1 {

    private final AdminMapper adminMapper1;
    private final EmployeeMapper1 employeeMapper;

    @Override
    public EmployeeResponseDto insertEmployee(EmployeeInsertRequestDto dto) {



        int findAdmin = adminMapper1.countById(dto.getEmployeeId());

        if (findAdmin > 0) {
            log.info("중복된 아이디가있습니다{}", dto.getEmployeeId());

            return null;
        }

        if (dto == null) {
            log.info("데이터가 담기지 않았습니");
        } else {
            adminMapper1.insertEmployee(dto);
        }



        return adminMapper1.selectEmployee(dto.getEmployeeId());
    }


    @Override
    public EmployeeResponseDto updateEmployee(EmployeeUpdateRequestDto dto) {
        adminMapper1.updateEmployee(dto);
        return adminMapper1.selectEmployee(dto.getEmployeeId());
    }

    @Override
    public int deleteEmployee(String employeeId) {

        return adminMapper1.deleteEmployee(employeeId);
    }

    @Override
    public Page<List<EmployeeDto>> getEmpInfo(PagingRequestDto pagingRequestDto) {

        int size = PAGE_SIZE; // 한 페이지에 출력할 게시물의 개수
        int startRow = (pagingRequestDto.getCurrentPage() - 1) * size; // 가져오기 시작할 row의 번호
        String orderByCondition = pagingRequestDto.getSort(); // 정렬할 컬럼 이름

        /* 현재 페이지에 대해서 size만큼 orderByCondition 정렬 조건에 맞추어 startRow부터 데이터를 가져온다 */
        List<EmployeeDto> getData = adminMapper1.getEmpInfo(size, orderByCondition, startRow, pagingRequestDto.getSortOrder());
        log.info("adminMapper1.getEmpInfo()의 getData : {}", getData);

        /* 가져온 데이터가 비어있다면 Page 객체를 새로 생성하고, Page 객체 중 가져온 데이터를 담는 속성에 빈 ArrayList를 생성하여 리턴한다 */
        if (getData.isEmpty()) {
            Page<List<EmployeeDto>> pageObj = new Page();
            pageObj.setData(new ArrayList<>());
            return pageObj;
        }

        int totalRowCount = adminMapper1.getEmpInfoTotalRow(); // 전제 행
        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
        boolean isLastPage = (pagingRequestDto.getCurrentPage() < lastPageNumber); // 마지막 페이지 여부

        /* 생성자 이용해 Page 객체 생성, 리턴*/
        Page<List<EmployeeDto>> result = new Page<>(getData, isLastPage, pagingRequestDto.getSort(), pagingRequestDto.getSortOrder(), pagingRequestDto.getCurrentPage(), totalRowCount);
        return result;


    }

    @Override
    public EmployeeDto getOneEmpInfo(String employeeId) {

        // 한명에 대한 정보를 받아와 리턴해줌
        EmployeeDto result = adminMapper1.getOneEmpInfo(employeeId);
        log.info("adminMapper1.getOneEmpInfo(employeeId)의 result : {}", result);
        return result;
    }

    // 받아온 id가 실제 테이블에 존재하는지 확인
    @Override
    public int getEmployeeCheck(String id) {
        return adminMapper1.getEmployeeCheck(id); // 실제 테이블에 id가 존재하면 1 반환됨
    }

    @Override
    public boolean toggleManager(String employeeId) {
        log.info("employee ID {}", employeeId);
        Employee old = employeeMapper.findMemberByMemberId(employeeId);
        if (old != null) {
            TempDto dto = new TempDto();
            employeeMapper.toggleManager(dto, employeeId);
            Employee updated = employeeMapper.findMemberByMemberId(employeeId);
            log.info("old {} updated {}", old, updated);
            return !updated.isManager() == old.isManager();
        }
        return false;
    }

    @Data
    public class TempDto {
        String generatedKey;
    }

}

