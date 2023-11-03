package com.example.bootproject.service.service1;

import com.example.bootproject.repository.mapper.AdminMapper1;
import com.example.bootproject.vo.vo1.request.EmployeeInsertRequestDto;
import com.example.bootproject.vo.vo1.response.EmployeeInsertResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdminService1Impl implements  AdminService1{

    private final AdminMapper1 adminMapper1;

    @Override
    public EmployeeInsertResponseDto insertEmployee(EmployeeInsertRequestDto dto) {

        if(dto != null){
            log.info("서비스사원요청 dto{}=",dto);
            adminMapper1.insertEmployee(dto);
        }else
            return null;


        return adminMapper1.selectEmployee(dto.getEmployeeId());
    }
}
