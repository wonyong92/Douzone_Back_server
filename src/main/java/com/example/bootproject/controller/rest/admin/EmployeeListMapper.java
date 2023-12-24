package com.example.bootproject.controller.rest.admin;

import com.example.bootproject.vo.vo1.request.PageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeListMapper {
    @Select("select employee_id from employee limit ${size} offset ${size*(pageRequest.page-1)}")
    public List<String> getEmployeeNumbers(PageRequest pageRequest,int size);
    @Select("select count(*) from employee")
    int countAllEmployee();
    @Select("(SELECT employee_id,name FROM employee WHERE !attendance_manager and (#{searchText} REGEXP '^[0-9]+$' AND employee_id like concat('%',#{searchText},'%')) OR (#{searchText} REGEXP '^[a-zA-Z]+$' AND name like concat('%',#{searchText},'%')) order by ${pageRequest.sort} ${pageRequest.desc} limit ${size} offset ${size*(pageRequest.page-1)} ) union ((select count(*),'searchCount' FROM employee WHERE (#{searchText} REGEXP '^[0-9]+$' AND employee_id like concat('%',#{searchText},'%') OR (#{searchText} REGEXP '^[a-zA-Z]+$' AND name like concat('%',#{searchText},'%')) ))) ")
    List<String> searchEmployeeNumbers(String searchText,PageRequest pageRequest, int size);
    @Select("(SELECT employee_id,name FROM employee where !attendance_manager order by ${pageRequest.sort} ${pageRequest.desc} limit ${size} offset ${size*(pageRequest.page-1)} ) union ((select count(*),'searchCount' FROM employee where !attendance_manager)) ")
    List<String> getAllEmployeeNumbers(String searchText,PageRequest pageRequest, int size);
    @Select("(SELECT employee_id FROM employee WHERE attendance_manager = #{isManager} and ((#{searchText} REGEXP '^[0-9]+$' AND employee_id like concat('%',#{searchText},'%')) OR (#{searchText} REGEXP '^[a-zA-Z]+$' AND name like concat('%',#{searchText},'%')) OR (#{searchText} REGEXP '^[가-힣]+$' AND name like concat('%',#{searchText},'%'))) order by ${pageRequest.sort} ${pageRequest.desc} limit ${size} offset ${size*(pageRequest.page-1)} ) union ((select count(*) FROM employee WHERE attendance_manager = #{isManager} and (#{searchText} REGEXP '^[0-9]+$' AND employee_id like concat('%',#{searchText},'%') OR (#{searchText} REGEXP '^[a-zA-Z]+$' AND name like concat('%',#{searchText},'%')) ))) ")
    List<String> findEmployeeNumbersAndEmployeeName(String searchText,PageRequest pageRequest, int size, boolean isManager);
    @Select("(SELECT employee_id FROM employee where attendance_manager = #{isManager} order by ${pageRequest.sort} ${pageRequest.desc} limit ${size} offset ${size*(pageRequest.page-1)} ) union ((select count(*) FROM employee where attendance_manager = #{isManager})) ")
    List<String> getAllEmployeeNumbersAndEmployeeName(String searchText,PageRequest pageRequest, int size, boolean isManager);
    @Select("SELECT count(*) FROM employee WHERE (#{searchText} REGEXP '^[0-9]+$' AND employee_id like '%#{searchText}%') OR (#{searchText} REGEXP '^[a-zA-Z]+$' AND name like '%#{searchText}%') limit ${size} offset ${size*(pageRequest.page-1)}")
    int countSearchEmployee();
}
