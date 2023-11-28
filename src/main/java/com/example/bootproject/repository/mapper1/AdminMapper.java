package com.example.bootproject.repository.mapper1;

import com.example.bootproject.vo.vo1.request.EmployeeInsertRequestDto;
import com.example.bootproject.vo.vo1.request.EmployeeUpdateRequestDto;
import com.example.bootproject.vo.vo1.response.EmployeeDto;
import com.example.bootproject.vo.vo1.response.EmployeeResponseDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {


    @Insert("INSERT INTO employee (employee_id,password, name, attendance_manager, hire_year) VALUES (#{dto.employeeId},#{dto.passWord}, #{dto.name}, #{dto.attendanceManager}, #{dto.hireYear} )")
    int insertEmployee(@Param("dto") EmployeeInsertRequestDto dto);

    //TODO:입사일로 넣는데이터로 수정하고 업데이트도 진행
    @Select("SELECT employee_id, password, name, attendance_manager, hire_year FROM employee WHERE employee_id = #{employeeId}")
    EmployeeResponseDto selectEmployee(String employeeId);

    //이거 사원id에 대해서 auto_increment할껀가..?


    @Update("UPDATE employee " + "SET password = #{dto.passWord}, " + "name = #{dto.name}, " + "attendance_manager = #{dto.attendanceManager}, " + "hire_year = #{dto.hireYear} " + "WHERE employee_id = #{dto.employeeId}")
    int updateEmployee(@Param("dto") EmployeeUpdateRequestDto dto);

    //사원 조회
    @Select("SELECT COUNT(*) FROM employee WHERE employee_Id = #{employeeId} ")
    int countById(String employeeId);


    //사원삭제
    @Delete("DELETE FROM employee WHERE employee_id = #{employeeId}")
    int deleteEmployee(String employeeId);

    // employee 테이블의 employee_id, password, name, attendance_manager, hire_year을 SELECT
    @Select("SELECT employee_id as employeeId, password, name, attendance_manager as attendanceManager, hire_year as hireYear\n" + "FROM employee\n" + "ORDER BY ${orderByCondition} ${sortOrder}\n" + "LIMIT ${size} OFFSET ${startRow};")
    List<EmployeeDto> getEmpInfo(int size, String orderByCondition, int startRow, String sortOrder);


    // employee 테이블의 employee_id, password, name, attendance_manager, hire_year을 select 하여 특정 사원 번호 가진 사원의 정보 반환
    @Select("SELECT employee_id as employeeId, password, name, attendance_manager as attendanceManager, hire_year as hireYear" + " FROM employee " + "WHERE employee_id= #{employeeId}")
    EmployeeDto getOneEmpInfo(String employeeId);


    // employee 테이블의 전체 행 select
    @Select("select count(*) from employee;")
    int getEmpInfoTotalRow();

    // 매개변수로 받아온 id가 employee 테이블에 존재시 1 반환
    @Select("select count(*) from employee where employee_id=#{id}")
    int getEmployeeCheck(String id);

}
