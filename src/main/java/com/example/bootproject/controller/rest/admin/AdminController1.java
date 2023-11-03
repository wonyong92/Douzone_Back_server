package com.example.bootproject.controller.rest.admin;

import com.example.bootproject.service.service1.AdminService1;
import com.example.bootproject.vo.vo1.request.EmployeeInsertRequestDto;
import com.example.bootproject.vo.vo1.response.EmployeeInsertResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController1{

    private final AdminService1 adminService1;


    //사원등록
    @PostMapping("/register")
    public ResponseEntity<EmployeeInsertResponseDto>registerEmployee(@ModelAttribute EmployeeInsertRequestDto dto){

        log.info("request{}",dto);

        EmployeeInsertResponseDto responseDto = adminService1.insertEmployee(dto);

        if (responseDto!=null) {
            log.info("성공적으로 데이터를 받았습니다: {}", responseDto);
            return ResponseEntity.ok(responseDto);
        } else {
            log.info("안좋은 데이터를 받았습니다: {}", responseDto);
            return ResponseEntity.badRequest().build();
        }

    }

    //사원id가 중복됐을때 사원id에 unique걸었을때의 오류메시지를 400에러로 띄우면괜찮나?


}