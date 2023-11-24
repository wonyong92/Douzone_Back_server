package com.example.bootproject.repository.mapper1;

import com.example.bootproject.vo.vo1.request.RegularTimeAdjustmentHistoryRequestDto;
import com.example.bootproject.vo.vo1.response.*;
import com.example.bootproject.vo.vo1.request.DefaultVacationRequestDto;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

import static com.example.bootproject.system.StaticString.VACATION_REQUEST_STATUS_CATEGORY_APPROVAL;

@Mapper
public interface ManagerMapper1 {


    //정규출퇴근시간 설정
    @Insert("INSERT INTO regular_time_adjustment_history (" + "target_date, adjusted_start_time, adjusted_end_time, reason, regular_time_adjustment_time, employee_id) " + "VALUES (#{dto.targetDate}, #{dto.adjustedStartTime}, #{dto.adjustedEndTime}, #{dto.reason}, #{dto.regularTimeAdjustmentTime}, #{dto.employeeId})")
    int insertregulartimeadjustment(@Param("dto") RegularTimeAdjustmentHistoryRequestDto dto, String employeeId);


    //정규출퇴근시간 내역
    @Select("SELECT " + "regular_time_adjustment_history_id, target_date, adjusted_start_time, adjusted_end_time, reason, regular_time_adjustment_time, employee_id " + "FROM regular_time_adjustment_history " + "WHERE employee_id = #{employeeId} " + "ORDER BY regular_time_adjustment_time DESC " + "LIMIT 1")
    RegularTimeAdjustmentHistoryResponseDto selectregulartimeadjustment(String employeeId);


    //타사원 근태이상승인내역
    @Select("SELECT * FROM attendance_approval WHERE employee_id = #{employeeId} ORDER BY ${sort} ${desc} LIMIT #{size} OFFSET #{startRow}")
    List<AttendanceApprovalUpdateResponseDto> getAllEmployeeByEmployeeId(@Param("employeeId") String employeeId, @Param("sort") String sort, @Param("desc") String desc, @Param("size") int size, @Param("startRow") int startRow);

    @Select("SELECT COUNT(*) FROM attendance_approval WHERE employee_id = #{employeeId}")
    int countApprovalInfoByEmployeeId(@Param("employeeId") String employeeId);

    //타사원의조정요청내역
    @Select("SELECT attendance_appeal_request_id, status, reason, attendance_info_id, appealed_start_time, appealed_end_time, employee_id, attendance_appeal_request_time, reason_for_rejection FROM attendance_appeal_request WHERE employee_id = #{employeeId} ORDER BY ${sort} ${desc} LIMIT #{size} OFFSET #{startRow}")
    List<AttendanceAppealMediateResponseDto> managerfindAttendanceAppealByEmployeeId(@Param("employeeId") String employeeId, @Param("sort") String sort, @Param("desc") String desc, @Param("size") int size, @Param("startRow") int startRow);

    //본인의 조정 요청 이력 갯수확인
    @Select("SELECT COUNT(*) FROM attendance_appeal_request WHERE employee_id = #{employeeId}")
    int countAttendanceAppealByEmployeeId(@Param("employeeId") String employeeId);


    //사원년월일 사원근태정보검색
    @Select("SELECT " + "attendance_info_id, " + "attendance_status_category, " + "employee_id, " + "start_time, " + "end_time, " + "attendance_date " + "FROM attendance_info " + "WHERE attendance_date = #{attendanceDate} AND employee_id = #{employeeId} " + "ORDER BY ${sort} ${desc} " + // sort 파라미터가 'attendance_date'가 되도록 확실히 하세요.
            "LIMIT #{size} OFFSET #{startRow}")
    List<AttendanceInfoResponseDto> selectmanagerAttendanceByDate(@Param("attendanceDate") LocalDate attendanceDate, @Param("employeeId") String employeeId, @Param("sort") String sort, @Param("desc") String desc, @Param("size") int size, @Param("startRow") int startRow);


    //사원년월 사원근태정보검색
    @Select("SELECT " + "attendance_info_id, " + "attendance_status_category, " + "employee_id, " + "start_time, " + "end_time, " + "attendance_date " + "FROM attendance_info " + "WHERE employee_id = #{employeeId} " + "AND YEAR(attendance_date) = #{year} " + "AND MONTH(attendance_date) = #{month} " + "ORDER BY ${sort} ${desc} " + // 여기에 공백 추가
            "LIMIT #{size} OFFSET #{startRow}")
    List<AttendanceInfoResponseDto> selectmanagerAttendanceByMonthAndEmployee(int year, int month, String employeeId, String sort, String desc, int size, int startRow);

    @Select({"SELECT COUNT(*) FROM attendance_info WHERE employee_id = #{employeeId} AND YEAR(attendance_date) = #{year} AND MONTH(attendance_date) = #{month} "})
    int managercountAttendanceInfoByEmployeeId(@Param("employeeId") String employeeId, int year, int month);

    @Select({"SELECT COUNT(*) FROM attendance_info WHERE employee_id = #{employeeId} AND attendance_date = #{attendanceDate} "})
    int managercountAttendanceInfoByMonthEmployeeId(@Param("employeeId") String employeeId, LocalDate attendanceDate);

    @Select("SELECT EXISTS(SELECT 1 FROM employee WHERE employee_id = #{employeeId})")
    boolean existsById(String employeeId);


    /* vacation_request 테이블의 vacation_request_time 컬럼의 값을 '%Y-%m-%d'로 변환 하고,
       변환 값이 매개변수 date와 일치하는 데이터 중, 매개변수로 받은 정렬 방식, 정렬 기준 컬럼, 출력 게시물 설정값에 맞는 데이터를 반환한다
    */
    @Select("SELECT vacation_request_key as vacationRequestKey, vacation_category_key as vacationCategoryKey, employee_id as employeeId, vacation_request_state_category_key as vacationRequestStateCategoryKey, vacation_quantity as vacationQuantity, vacation_start_date as vacationStartDate, vacation_end_date as vacationEndDate, reason, vacation_request_time as vacationRequestTime, reason_for_rejection as reasonForRejection,name " +
            "FROM vacation_request  inner join employee using(employee_id) WHERE DATE_FORMAT(vacation_request_time,'%Y-%m-%d')=#{date} " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")
    List<VacationRequestDto> getAllVacationHistory(int size, String orderByCondition, int startRow, String sortOrder, String date);

    // 특정 날짜의 모든 데이터 개수 반환
    @Select("SELECT COUNT(*) " +
            "FROM vacation_request V inner join employee E on V.employee_id = E.employee_id " +
            "WHERE DATE_FORMAT(V.vacation_request_time,'%Y-%m-%d')=#{date};")
    int getAllVacationRequestCountByDate(String date);

    // result 값이 승인 이면서 타 사원의 연차 사용 이력 데이터를 가져와 반환함
    @Select("SELECT vacation_request_key as vacationRequestKey, vacation_category_key as vacationCategoryKey, employee_id as employeeId, vacation_request_state_category_key as vacationRequestStateCategoryKey, vacation_quantity as vacationQuantity, vacation_start_date as vacationStartDate, vacation_end_date as vacationEndDate, reason, vacation_request_time as vacationRequestTime, reason_for_rejection as reasonForRejection,name " +
            "FROM VACATION_REQUEST INNER JOIN EMPLOYEE USING(employee_id) " +
            "WHERE vacation_request_state_category_key like CONCAT('%', #{status}, '%') AND EMPLOYEE_ID=#{id} " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow} ;")
    List<VacationRequestDto> getHistoryOfVacationOfEmployee(int size, String orderByCondition, int startRow, String sortOrder, String id, String status);

    // 특정 사원의 신청 결과값이 매개변수로 받아온 값에 일치하는 데이터의 개수를 반환
    @Select("select count(*) " +
            "from VACATION_REQUEST V INNER JOIN EMPLOYEE E ON V.employee_id = E.employee_id " +
            "WHERE V.vacation_request_state_category_key LIKE '%${status}%' AND V.EMPLOYEE_ID=#{id};")
    int getHistoryOfVacationOfEmployeeTotalRow(String id, String status);

    //정규 근무시간 조정내역 테이블의 전체 정보를 select 함
    @Select("SELECT r.regular_time_adjustment_history_id AS regularTimeAdjustmentHistoryId, " +
            "r.target_date AS targetDate, " +
            "r.adjusted_start_time AS adjustedStartTime, " +
            "r.adjusted_end_time AS adjustedEndTime, " +
            "r.reason, " +
            "r.regular_time_adjustment_time AS regularTimeAdjustmentTime, " +
            "r.employee_id AS employeeId, " +
            "e.name AS name " +
            "FROM regular_time_adjustment_history r " +
            "INNER JOIN employee e ON r.employee_id = e.employee_id " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")

    List<SettingWorkTimeDto> getSettingWorkTime(int size, String orderByCondition, int startRow, String sortOrder);

    // regular_time_adjustment_history 테이블의 튜플 개수 반환
    @Select("SELECT COUNT(*) FROM regular_time_adjustment_history;")
    int getSettingWorkTimeCount();

    // 연차별 사원 연차 개수 설정 테이블의 전체 정보를 select 함
    @Select("SELECT v.setting_key as settingKey, v.freshman, v.senior, v.setting_time as settingTime, v.target_date as targetDate, v.employee_id as employeeId, e.name " +
            "FROM vacation_quantity_setting v " +
            "JOIN employee e ON v.employee_id = e.employee_id " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")
    List<VacationQuantitySettingDto> getVacationSettingHistory(int size, String orderByCondition, int startRow, String sortOrder);


    // vacation_quantity_setting 테이블의 튜플 개수 반환
    @Select("SELECT COUNT(*) FROM vacation_quantity_setting;")
    int getVacationSettingHistoryCount();

    // 근속 연수에 따른 기본 연차 개수 설정
    @Insert("INSERT INTO vacation_quantity_setting(freshman,senior,target_date,employee_id) values(#{freshman},#{senior},#{targetDate},#{employeeId})")
    @Options(useGeneratedKeys = true, keyProperty = "settingKey")
    int insertDefaultVacation(DefaultVacationRequestDto dto);

    // generated key 값이 pk와 일치하는 튜플 모두 반환
    @Select("SELECT * FROM vacation_quantity_setting WHERE setting_key=#{generatedKey};")
    DefaultVacationResponseDto getDefaultVacationResponseDto(int generatedKey);

    //입사년도 데이터 가져옴
    @Select("SELECT YEAR(hire_year) FROM EMPLOYEE WHERE employee_id=#{id}; ")
    int getHireYear(String id);

    //작년의 가장 최근 데이터에서 입사연도에 따라서 기본 연차 부여 설정값 가져옴
    @Select("SELECT CASE\n" +
            "        WHEN #{year} = YEAR(NOW()) THEN freshman\n" +
            "        WHEN #{year} < YEAR(now()) THEN senior\n" +
            "       END AS settingValue\n" +
            "FROM vacation_quantity_setting " +
            "WHERE YEAR(setting_time) = YEAR(CURDATE() - INTERVAL 1 YEAR) " +
            "ORDER BY setting_time DESC LIMIT 1;")
    int getDefaultSettingVacationValue(int year);


    // 가장 최근 데이터 리턴
    @Select("SELECT ifnull(${info},0)\n" +
            "FROM VACATION_QUANTITY_SETTING \n" +
            "ORDER BY setting_time DESC\n" +
            "LIMIT 1;")
    int getVacationDefaultLatestCount(String info);

    // 올해 조정된 데이터가 있는지 확인하여 존재시 조정 연차 개수 총합 리턴
    // 미존재시 0 리턴
    @Select("SELECT IFNULL(sum(adjust_quantity),0) " +
            "FROM vacation_adjusted_history " +
            "WHERE year(adjust_time)=year(now()) AND employee_id = #{employeeId};")
    int getVacationAdjustedHistory(String employeeId);

    // 올해 연차 승인 받은 데이터의 수
    @Select("SELECT IFNULL(SUM(vacation_quantity),0)" +
            "FROM vacation_request" +
            " WHERE EMPLOYEE_ID=#{employeeId} AND VACATION_REQUEST_STATE_CATEGORY_KEY="
            + "'" + VACATION_REQUEST_STATUS_CATEGORY_APPROVAL + "'")
    int getApproveVacationQuantity(String employeeId);

    // 매개변수로 받아온 id가 employee 테이블에 존재시 1 반환
    @Select("select count(*) from employee where employee_id=#{id}")
    int getEmployeeCheck(String id);



    //사원에대한 검색과년월일 검색
    @Select("SELECT vr.employee_id as employeeId, " +
            "vr.vacation_request_state_category_key as vacationRequestStateCategoryKey, " +
            "vr.vacation_start_date as vacationStartDate, " +
            "vr.vacation_end_date as vacationEndDate, " +
            "vr.reason, " +
            "vr.vacation_request_time as vacationRequestTime, " +
            "vr.reason_for_rejection as reasonForRejection, " +
            "e.name " +
            "FROM vacation_request vr INNER JOIN employee e ON vr.employee_id = e.employee_id " +
            "WHERE (e.name LIKE CONCAT('%', #{searchParameter}, '%')) " +
            "AND DATE(vr.vacation_request_time) = #{date} " +
            "LIMIT #{size} OFFSET #{startRow}")
    List<VacationResponseDto> getVacationHistoryByEmployeeAndDate(int size, int startRow, String date, String searchParameter);


    //사원검색에 대한 총갯수
    @Select("SELECT COUNT(*) " +
            "FROM vacation_request vr INNER JOIN employee e ON vr.employee_id = e.employee_id " +
            "WHERE (e.name LIKE CONCAT('%', #{searchParameter}, '%')) " +
            "AND DATE(vr.vacation_request_time) = #{date}")
    int countVacationRequestByEmployeeAndDate(String date, String searchParameter);










}

