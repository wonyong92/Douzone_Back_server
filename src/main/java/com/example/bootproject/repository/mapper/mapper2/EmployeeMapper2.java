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
    @Select("SELECT VACATION_REQUEST.vacation_request_key as vacationRequestKey, VACATION_REQUEST.vacation_category_key as vacationCategoryKey, VACATION_REQUEST.employee_id as employeeId, VACATION_REQUEST.vacation_request_state_category_key as vacationRequestStateCategoryKey, VACATION_REQUEST.vacation_quantity as vacationQuantity, VACATION_REQUEST.vacation_start_date as vacationStartDate, VACATION_REQUEST.vacation_end_date as vacationEndDate, VACATION_REQUEST.reason, VACATION_REQUEST.vacation_request_time as vacationRequestTime, VACATION_REQUEST.reason_for_rejection as reasonForRejection, EMPLOYEE.name " +
            "FROM VACATION_REQUEST INNER JOIN EMPLOYEE ON VACATION_REQUEST.employee_id = EMPLOYEE.employee_id " +
            "WHERE VACATION_REQUEST.vacation_request_state_category_key like '%${status}%' AND VACATION_REQUEST.EMPLOYEE_ID=#{id} " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")
    public List<VacationRequestDto> getHistoryOfVacationOfMine( int size, String orderByCondition, int startRow,String sortOrder,String id,String status);

    // result 값이 승인 이면서 타 사원의 연차 사용 이력 데이터를 가져와 반환함
    @Select("SELECT V.vacation_request_key as vacationRequestKey, V.vacation_category_key as vacationCategoryKey, V.employee_id as employeeId, V.vacation_request_state_category_key as vacationRequestStateCategoryKey, V.vacation_quantity as vacationQuantity, V.vacation_start_date as vacationStartDate, V.vacation_end_date as vacationEndDate, V.reason, V.vacation_request_time as vacationRequestTime, V.reason_for_rejection as reasonForRejection,E.name " +
            "FROM VACATION_REQUEST V INNER JOIN EMPLOYEE E ON V.employee_id = E.employee_id " +
            "WHERE v.vacation_request_state_category_key='승인' AND V.EMPLOYEE_ID=#{id} " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow} ;")
    public List<VacationRequestDto> getHistoryOfUsedVacationOfEmployee(int size, String orderByCondition, int startRow,String sortOrder,String id);

    @Select("select count(*) from VACATION_REQUEST V INNER JOIN EMPLOYEE E ON V.employee_id = E.employee_id WHERE V.vacation_request_state_category_key='승인' AND V.EMPLOYEE_ID=#{id};")
    public int getHistoryOfUsedVacationOfMineTotalRow(String id);

    @Select("select count(*) from VACATION_REQUEST V INNER JOIN EMPLOYEE E ON V.employee_id = E.employee_id WHERE V.vacation_request_state_category_key LIKE '%${status}%' AND V.EMPLOYEE_ID=#{id};")
    public int getHistoryOfVacationOfMineTotalRow(String id,String status);
}

