//package com.example.bootproject.service.service3.impl;
//
//import com.example.bootproject.repository.mapper3.employee.EmployeeMapper;
//import com.example.bootproject.service.service3.api.ManagerService;
//import com.example.bootproject.vo.vo3.response.Page;
//import com.example.bootproject.vo.vo3.response.employee.EmployeeResponseDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.example.bootproject.vo.vo3.response.Page.PAGE_SIZE;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class ManagerServiceImpl implements ManagerService {
//
//    private final EmployeeMapper employeeMapper;
//
//    public Page<List<EmployeeResponseDto>> getEmployeeList(int page, String sort, String desc) {
//        int size = PAGE_SIZE; // Page 객체로부터 size를 가져옴
//        int startRow = (page - 1) * size; // 가져오기 시작할 row의 번호
//
//        List<EmployeeResponseDto> findEmployees = employeeMapper.getAllEmployee(sort, desc, size, startRow);
//        log.info("List<EmployeeResponseDto> result : {}", findEmployees);
//        if (findEmployees.isEmpty()) {
//            Page<List<EmployeeResponseDto>> empty = new Page<>();
//            empty.setData(new ArrayList<>());
//            return empty;
//        }
//
//        int totalRowCount = employeeMapper.getEmpInfoTotalRow(); // 전제 행
//        int lastPageNumber = (int) Math.ceil((double) totalRowCount / size); //마지막 페이지 번호
//
//        Page<List<EmployeeResponseDto>> result = new Page<>(findEmployees, page < lastPageNumber, sort, desc, page, totalRowCount);
//        log.info("Page<List<EmployeeResponseDto>> result : {}", result);
//        return result;
//    }
//}
