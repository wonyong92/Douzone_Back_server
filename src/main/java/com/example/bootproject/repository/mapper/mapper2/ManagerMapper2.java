package com.example.bootproject.repository.mapper.mapper2;

import com.example.bootproject.vo.vo2.request.DefaultVacationRequestDto;
import com.example.bootproject.vo.vo2.response.DefaultVacationResponseDto;
import com.example.bootproject.vo.vo2.response.SettingWorkTimeDto;
import com.example.bootproject.vo.vo2.response.VacationQuantitySettingDto;
import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ManagerMapper2 {


    /* vacation_request 테이블의 vacation_request_time 컬럼의 값을 '%Y-%m-%d'로 변환 하고,
       변환 값이 매개변수 date와 일치하는 데이터만 select함
    */
    @Select("SELECT vacation_request_key as vacationRequestKey, vacation_category_key as vacationCategoryKey, employee_id as employeeId, vacation_request_state_category_key as vacationRequestStateCategoryKey, vacation_quantity as vacationQuantity, vacation_start_date as vacationStartDate, vacation_end_date as vacationEndDate, reason, vacation_request_time as vacationRequestTime, reason_for_rejection as reasonForRejection,name " +
            "FROM vacation_request  inner join employee using(employee_id) WHERE DATE_FORMAT(vacation_request_time,'%Y-%m-%d')=#{date} " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")
    public List<VacationRequestDto> getAllVacationHistory(int size, String orderByCondition, int startRow,String sortOrder, String date);

    @Select("SELECT COUNT(*) FROM vacation_request V inner join employee E on V.employee_id = E.employee_id WHERE DATE_FORMAT(V.vacation_request_time,'%Y-%m-%d')=#{date};")
    public int getAllVacationRequestCountByDate(String date);



    // result 값이 승인 이면서 타 사원의 연차 사용 이력 데이터를 가져와 반환함
    @Select("SELECT vacation_request_key as vacationRequestKey, vacation_category_key as vacationCategoryKey, employee_id as employeeId, vacation_request_state_category_key as vacationRequestStateCategoryKey, vacation_quantity as vacationQuantity, vacation_start_date as vacationStartDate, vacation_end_date as vacationEndDate, reason, vacation_request_time as vacationRequestTime, reason_for_rejection as reasonForRejection,name " +
            "FROM VACATION_REQUEST INNER JOIN EMPLOYEE USING(employee_id) " +
            "WHERE vacation_request_state_category_key like CONCAT('%', #{status}, '%') AND EMPLOYEE_ID=#{id} " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow} ;")
    public List<VacationRequestDto> getHistoryOfVacationOfEmployee(int size, String orderByCondition, int startRow,String sortOrder,String id,String status);

    @Select("select count(*) from VACATION_REQUEST V INNER JOIN EMPLOYEE E ON V.employee_id = E.employee_id WHERE V.vacation_request_state_category_key LIKE '%${status}%' AND V.EMPLOYEE_ID=#{id};")
    public int getHistoryOfVacationOfEmployeeTotalRow(String id,String status);




    //정규 근무시간 조정내역 테이블의 전체 정보를 select 함
    @Select("SELECT regular_time_adjustment_history_id as regularTimeAdjustmentHistoryId, target_date as targetDate, adjusted_start_time as adjustedStartTime, adjusted_end_time as adjustedEndTime, reason, regular_time_adjustment_time as regularTimeAdjustmentTime,employee_id as employeeId " +
            "FROM regular_time_adjustment_history " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")
    public List<SettingWorkTimeDto> getSettingWorkTime(int size,String orderByCondition,int startRow,String sortOrder);

    @Select("SELECT COUNT(*) FROM regular_time_adjustment_history;")
    public int getSettingWorkTimeCount();




    // 연차별 사원 연차 개수 설정 테이블의 전체 정보를 select 함
    @Select("SELECT setting_key as settingKey, freshman,senior,setting_time as settingTime,target_date as targetDate, employee_id as employeeId" +
            " FROM vacation_quantity_setting " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")
    public List<VacationQuantitySettingDto> getVacationSettingHistory(int size,String orderByCondition,int startRow,String sortOrder);

    @Select("SELECT COUNT(*) FROM vacation_quantity_setting;")
    public int getVacationSettingHistoryCount();




    // 근속 연수에 따른 기본 연차 개수 설정
    @Insert("INSERT INTO vacation_quantity_setting(freshman,senior,setting_time,target_date,employee_id) values(#{freshman},#{senior},#{settingTime},#{targetDate},#{employeeId})")
    public int insertDefaultVacation(DefaultVacationRequestDto dto);

    @Select("SELECT * FROM vacation_quantity_setting WHERE employee_id = #{id} ORDER BY setting_time desc LIMIT 1;")
    public DefaultVacationResponseDto getDefaultVacationResponseDto(String id);

}
