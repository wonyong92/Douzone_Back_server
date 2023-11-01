package com.example.bootproject.repository.mapper.mapper2;

import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmployeeMapper2 {

    // result 값이 완료 이면서, 본인의 연차 사용 이력 데이터를 가져와 반환함
    @Select("SELECT* FROM VACATION_REQUEST WHERE RESULT='승인' AND EMPLOYEE_ID=#{id}")
    public List<VacationRequestDto> getHistoryOfUsedVacationOfMine(String id);

    // result 값이 반려 이면서 본인의 반려된 연차 이력 데이터를 가져와 반환함
    @Select("SELECT* FROM VACATION_REQUEST WHERE RESULT='반려' AND EMPLOYEE_ID=#{id}")
    public List<VacationRequestDto> getHistoryOfRejectedVacationOfMine(String id);

    // result 값이 승인 이면서 타 사원의 연차 사용 이력 데이터를 가져와 반환함
    @Select("SELECT* FROM vacation_request WHERE RESULT='승인' AND employee_id=#{id}")
    public List<VacationRequestDto> getHistoryOfUsedVacationOfEmployee(String id);
}