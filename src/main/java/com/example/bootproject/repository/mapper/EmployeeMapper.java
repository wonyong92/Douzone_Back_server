package com.example.bootproject.repository.mapper;

import com.example.bootproject.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface EmployeeMapper {
//    @Select("SELECT * FROM employee WHERE employee_id = #{employeeId}") // 어노테이션으로 쿼리문 설정 가능
//    Employee findMemberByMemberId(String employeeId);//파라미터 타입, 이름과 반환 타입. 네임스페이스 정보를 알아서 파싱한다
//    @Select("select * from employee where (#{employeeId},#{employeePassword}) = (employee_id,employee_password)")
//    Employee findLoginInfoByMemberIdAndMemberPassword(String employeeId, String employeePassword);
}
