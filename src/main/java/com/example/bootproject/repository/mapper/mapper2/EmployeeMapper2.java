package com.example.bootproject.repository.mapper.mapper2;

import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmployeeMapper2 {

    // result 값이 반려 이면서 본인의 반려된 연차 이력 데이터를 가져와 반환함
    @Select("SELECT vacation_request_key as vacationRequestKey, vacation_category_key as vacationCategoryKey, employee_id as employeeId, vacation_request_state_category_key as vacationRequestStateCategoryKey, vacation_quantity as vacationQuantity, vacation_start_date as vacationStartDate, vacation_end_date as vacationEndDate, reason, vacation_request_time as vacationRequestTime, reason_for_rejection as reasonForRejection, name " +
            "FROM VACATION_REQUEST INNER JOIN EMPLOYEE using(employee_id) " +
            "WHERE vacation_request_state_category_key like CONCAT('%', #{status}, '%') AND EMPLOYEE_ID=#{id} " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")
    public List<VacationRequestDto> getHistoryOfVacationOfMine( int size, String orderByCondition, int startRow,String sortOrder,String id,String status);

    @Select("select count(*) from VACATION_REQUEST V INNER JOIN EMPLOYEE E ON V.employee_id = E.employee_id WHERE V.vacation_request_state_category_key LIKE '%${status}%' AND V.EMPLOYEE_ID=#{id};")
    public int getHistoryOfVacationOfMineTotalRow(String id,String status);
}

