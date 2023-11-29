package com.example.bootproject.controller.rest.admin;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.repository.mapper1.EmployeeMapper1;
import com.example.bootproject.repository.mapper3.employee_delete.EmployeeIdDeleteAndBackupMapper;
import com.example.bootproject.service.service3.api.EmployeeDeleteService;
import com.example.bootproject.vo.vo1.response.employee.EmployeeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.example.bootproject.system.util.ValidationChecker.isAdmin;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EmployeeDelete {
    private final EmployeeDeleteService employeeDeleteService;
    private final EmployeeMapper1 employeeMapper1;

    @GetMapping("/admin/employee/delete/{employeeId}")
    public ResponseEntity<Employee> employeeDelete(HttpServletRequest req, @PathVariable String employeeId){
        if(isAdmin(req)) {
            Employee result = employeeMapper1.findEmployeeInfoById(employeeId);
            //TODO: employeeId validation check
            if(employeeDeleteService.backUpDataAndDeleteEmployeeInformation(employeeId)){
                log.info("백업 및 삭제 성공");
                return ResponseEntity.ok(result);
            }
            log.info("백업 및 삭제 실패");
            return ResponseEntity.badRequest().build();
        }
        log.info("ADMIN이 아닌 유저가 유저 삭제 기능 호출");
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

}
