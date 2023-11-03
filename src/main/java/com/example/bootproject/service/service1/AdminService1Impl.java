package com.example.bootproject.service.service1;

import com.example.bootproject.repository.mapper.AdminMapper1;
import com.example.bootproject.vo.vo1.request.EmployeeInsertRequestDto;
import com.example.bootproject.vo.vo1.request.EmployeeUpdateRequestDto;
import com.example.bootproject.vo.vo1.response.EmployeeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdminService1Impl implements  AdminService1 {

    private final AdminMapper1 adminMapper1;

    @Override
    public EmployeeResponseDto insertEmployee(EmployeeInsertRequestDto dto) {

        int findAdmin = adminMapper1.countById(dto.getEmployeeId());

        if (findAdmin >0){
            log.info("중복된 아이디가있습니다{}" ,dto.getEmployeeId());

            return null;
        }

        if(dto == null){
            log.info("데이터가 담기지 않았습니");
        }else{
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


}

