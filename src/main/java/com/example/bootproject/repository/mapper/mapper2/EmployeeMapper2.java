package com.example.bootproject.repository.mapper.mapper2;

import com.example.bootproject.vo.vo2.response.VacationRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.bootproject.system.util.StaticString.VACATION_REQUEST_STATUS_CATEGORY_APPROVAL;

@Mapper
@Repository
public interface EmployeeMapper2 {

    /* result 값이 반려 이면서 본인의 반려된 연차 이력 데이터 중, 매개변수로 받은 정렬 방식, 정렬 기준 컬럼, 출력 게시물 설정값에 맞는 데이터를 반환한다 */
    @Select("SELECT vacation_request_key as vacationRequestKey, vacation_category_key as vacationCategoryKey, employee_id as employeeId, vacation_request_state_category_key as vacationRequestStateCategoryKey, vacation_quantity as vacationQuantity, vacation_start_date as vacationStartDate, vacation_end_date as vacationEndDate, reason, vacation_request_time as vacationRequestTime, reason_for_rejection as reasonForRejection, name " +
            "FROM VACATION_REQUEST INNER JOIN EMPLOYEE using(employee_id) " +
            "WHERE vacation_request_state_category_key like CONCAT('%', #{status}, '%') AND EMPLOYEE_ID=#{id} " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")
    public List<VacationRequestDto> getHistoryOfVacationOfMine( int size, String orderByCondition, int startRow,String sortOrder,String id,String status);

    /* 특정 사원의 vacation_request_state_category_key 컬럼 값이 매개변수로 전달받은 신청 결과값에 해당하는 모든 데이터를 가져온다 */
    @Select("select count(*) " +
            "from VACATION_REQUEST V INNER JOIN EMPLOYEE E ON V.employee_id = E.employee_id " +
            "WHERE V.vacation_request_state_category_key LIKE '%${status}%' AND V.EMPLOYEE_ID=#{id};")
    public int getHistoryOfVacationOfMineTotalRow(String id,String status);

    // 입사년도 데이터 가져온다
    @Select("SELECT YEAR(hire_year) FROM EMPLOYEE WHERE employee_id=#{id}; ")
    public int getHireYear(String id);


    //작년의 가장 최근 데이터에서 입사연도에 따라서, 기본 연차 부여 설정 값을 가져온다
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
            " WHERE EMPLOYEE_ID=#{employeeId} AND VACATION_REQUEST_STATE_CATEGORY_KEY=" +
            "'"+VACATION_REQUEST_STATUS_CATEGORY_APPROVAL+"'")
    public int getApproveVacationQuantity(String employeeId);
}

