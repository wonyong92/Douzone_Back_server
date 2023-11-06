package com.example.bootproject.repository.mapper.mapper2;

import com.example.bootproject.vo.vo2.request.DefaultVacationRequestDto;
import com.example.bootproject.vo.vo2.response.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.example.bootproject.system.util.StaticString.VACATION_REQUEST_STATUS_CATEGORY_APPROVAL;

@Mapper
@Repository
public interface ManagerMapper2 {


    /* vacation_request 테이블의 vacation_request_time 컬럼의 값을 '%Y-%m-%d'로 변환 하고,
       변환 값이 매개변수 date와 일치하는 데이터 중, 매개변수로 받은 정렬 방식, 정렬 기준 컬럼, 출력 게시물 설정값에 맞는 데이터를 반환한다
    */
    @Select("SELECT vacation_request_key as vacationRequestKey, vacation_category_key as vacationCategoryKey, employee_id as employeeId, vacation_request_state_category_key as vacationRequestStateCategoryKey, vacation_quantity as vacationQuantity, vacation_start_date as vacationStartDate, vacation_end_date as vacationEndDate, reason, vacation_request_time as vacationRequestTime, reason_for_rejection as reasonForRejection,name " +
            "FROM vacation_request  inner join employee using(employee_id) WHERE DATE_FORMAT(vacation_request_time,'%Y-%m-%d')=#{date} " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")
    public List<VacationRequestDto> getAllVacationHistory(int size, String orderByCondition, int startRow,String sortOrder, String date);

    // 특정 날짜의 모든 데이터 개수 반환
    @Select("SELECT COUNT(*) " +
            "FROM vacation_request V inner join employee E on V.employee_id = E.employee_id " +
            "WHERE DATE_FORMAT(V.vacation_request_time,'%Y-%m-%d')=#{date};")
    public int getAllVacationRequestCountByDate(String date);

    // result 값이 승인 이면서 타 사원의 연차 사용 이력 데이터를 가져와 반환함
    @Select("SELECT vacation_request_key as vacationRequestKey, vacation_category_key as vacationCategoryKey, employee_id as employeeId, vacation_request_state_category_key as vacationRequestStateCategoryKey, vacation_quantity as vacationQuantity, vacation_start_date as vacationStartDate, vacation_end_date as vacationEndDate, reason, vacation_request_time as vacationRequestTime, reason_for_rejection as reasonForRejection,name " +
            "FROM VACATION_REQUEST INNER JOIN EMPLOYEE USING(employee_id) " +
            "WHERE vacation_request_state_category_key like CONCAT('%', #{status}, '%') AND EMPLOYEE_ID=#{id} " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow} ;")
    public List<VacationRequestDto> getHistoryOfVacationOfEmployee(int size, String orderByCondition, int startRow,String sortOrder,String id,String status);

    // 특정 사원의 신청 결과값이 매개변수로 받아온 값에 일치하는 데이터의 개수를 반환
    @Select("select count(*) " +
            "from VACATION_REQUEST V INNER JOIN EMPLOYEE E ON V.employee_id = E.employee_id " +
            "WHERE V.vacation_request_state_category_key LIKE '%${status}%' AND V.EMPLOYEE_ID=#{id};")
    public int getHistoryOfVacationOfEmployeeTotalRow(String id,String status);

    //정규 근무시간 조정내역 테이블의 전체 정보를 select 함
    @Select("SELECT regular_time_adjustment_history_id as regularTimeAdjustmentHistoryId, target_date as targetDate, adjusted_start_time as adjustedStartTime, adjusted_end_time as adjustedEndTime, reason, regular_time_adjustment_time as regularTimeAdjustmentTime,employee_id as employeeId " +
            "FROM regular_time_adjustment_history " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")
    public List<SettingWorkTimeDto> getSettingWorkTime(int size,String orderByCondition,int startRow,String sortOrder);

    // regular_time_adjustment_history 테이블의 튜플 개수 반환
    @Select("SELECT COUNT(*) FROM regular_time_adjustment_history;")
    public int getSettingWorkTimeCount();

    // 연차별 사원 연차 개수 설정 테이블의 전체 정보를 select 함
    @Select("SELECT setting_key as settingKey, freshman,senior,setting_time as settingTime,target_date as targetDate, employee_id as employeeId" +
            " FROM vacation_quantity_setting " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")
    public List<VacationQuantitySettingDto> getVacationSettingHistory(int size,String orderByCondition,int startRow,String sortOrder);

    // vacation_quantity_setting 테이블의 튜플 개수 반환
    @Select("SELECT COUNT(*) FROM vacation_quantity_setting;")
    public int getVacationSettingHistoryCount();

    // 근속 연수에 따른 기본 연차 개수 설정
    @Insert("INSERT INTO vacation_quantity_setting(freshman,senior,target_date,employee_id) values(#{freshman},#{senior},#{targetDate},#{employeeId})")
    @Options(useGeneratedKeys = true, keyProperty = "settingKey")
    public int insertDefaultVacation(DefaultVacationRequestDto dto);

    // generated key 값이 pk와 일치하는 튜플 모두 반환
    @Select("SELECT * FROM vacation_quantity_setting WHERE setting_key=#{generatedKey};")
    public DefaultVacationResponseDto getDefaultVacationResponseDto(int generatedKey);

    //입사년도 데이터 가져옴
    @Select("SELECT YEAR(hire_year) FROM EMPLOYEE WHERE employee_id=#{id}; ")
    public int getHireYear(String id);

    //작년의 가장 최근 데이터에서 입사연도에 따라서 기본 연차 부여 설정값 가져옴
    @Select("SELECT CASE\n" +
            "        WHEN #{year} = YEAR(NOW()) THEN freshman\n" +
            "        WHEN #{year} < YEAR(now()) THEN senior\n" +
            "       END AS settingValue\n" +
            "FROM vacation_quantity_setting " +
            "WHERE YEAR(setting_time) = YEAR(CURDATE() - INTERVAL 1 YEAR) " +
            "ORDER BY setting_time DESC LIMIT 1;")
    public int getDefaultSettingVacationValue(int year);

    // 올해 조정된 데이터가 있는지 확인하여 존재시 조정 연차 개수 총합 리턴
    // 미존재시 0 리턴
    @Select("SELECT IFNULL(sum(adjust_quantity),0) " +
            "FROM vacation_adjusted_history " +
            "WHERE year(adjust_time)=year(now()) AND employee_id = #{employeeId};")
    public int getVacationAdjustedHistory (String employeeId);

    // 올해 연차 승인 받은 데이터의 수
    @Select("SELECT IFNULL(SUM(vacation_quantity),0)" +
            "FROM vacation_request" +
            " WHERE EMPLOYEE_ID=#{employeeId} AND VACATION_REQUEST_STATE_CATEGORY_KEY="
            +"'"+VACATION_REQUEST_STATUS_CATEGORY_APPROVAL+"'")
    public int getApproveVacationQuantity(String employeeId);

    // 매개변수로 받아온 id가 employee 테이블에 존재시 1 반환
    @Select("select count(*) from employee where employee_id=#{id}")
    public int getEmployeeCheck(String id);

}
