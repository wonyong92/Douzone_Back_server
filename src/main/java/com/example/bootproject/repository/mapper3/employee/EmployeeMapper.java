package com.example.bootproject.repository.mapper3.employee;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.service.service3.impl.AdminServiceImpl;
import com.example.bootproject.vo.vo3.response.employee.EmployeeResponseDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("SELECT * FROM employee WHERE employee_id = #{employeeId}")
        // 어노테이션으로 쿼리문 설정 가능
    Employee findMemberByMemberId(String employeeId);//파라미터 타입, 이름과 반환 타입. 네임스페이스 정보를 알아서 파싱한다

    @Select("select * from employee where (#{employeeId},#{employeePassword}) = (employee_id,employee_password)")
    Employee findLoginInfoByMemberIdAndMemberPassword(String employeeId, String employeePassword);

    @Select("select count(*) from employee where employee_id = #{employeeId}")
    Integer checkEmployeeExist(String employeeId);

    @Update("update employee set attendance_manager=!attendance_manager where employee_id = #{employeeId}")
    @Options(useGeneratedKeys = true, keyProperty = "generatedKeyDto.generatedKey")
    void toggleManager(@Param("generatedKeyDto") AdminServiceImpl.TempDto generatedKey, @Param("employeeId") String employeeId);

    @Select("select employee_id,name,attendance_manager,hire_year from employee order by #{orderByCondition} ${desc} LIMIT #{size} OFFSET #{startRow}")
    List<EmployeeResponseDto> getAllEmployee(@Param("orderByCondition") String sort, @Param("desc") String desc, @Param("size") int size, @Param("startRow") int startRow);

    @Select("select count(*) from employee;")
    int getEmpInfoTotalRow();

    @Select("select * from employee where employee_id = #{loginId}")
    Employee findEmployeeInfoById(String loginId);

    @Update("update employee set password = #{newPassword} where employee_id = #{loginId}")
    int updatePassword(String loginId, String newPassword);

    @Select("select employee_id from employee")
    List<String> getAllEmployeeIdWithoutPagination();
}
