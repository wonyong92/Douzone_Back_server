package com.example.bootproject.repository.mapper1;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.service.service1.AdminService1Impl;
import com.example.bootproject.vo.vo1.request.*;
import com.example.bootproject.vo.vo1.response.*;
import com.example.bootproject.vo.vo1.response.employee.EmployeeResponseDto;
import com.example.bootproject.vo.vo1.response.vacation.VacationRequestResponseDto;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.bootproject.system.StaticString.VACATION_REQUEST_STATUS_CATEGORY_APPROVAL;
import static com.example.bootproject.system.StaticString.VACATION_REQUEST_STATUS_CATEGORY_REQUESTED;

@Mapper
public interface EmployeeMapper1 {


    //출근기록
    @Insert("INSERT INTO attendance_info (employee_id, attendance_date, start_time) " +
            "VALUES (#{dto.employeeId}, #{dto.attendanceDate}, #{dto.startTime}) " +
            "ON DUPLICATE KEY UPDATE start_time = VALUES (start_time)")
    int startTimeRequest(@Param("dto") AttendanceInfoStartRequestDto dto);


    //응답 근태정보테이블
    @Select("SELECT * " +
            "FROM attendance_info " +
            "WHERE employee_id = #{employeeId} AND attendance_date = #{AttendanceDate}")
    AttendanceInfoResponseDto findattendanceInfo(String employeeId, LocalDate AttendanceDate);


    //출근내역찾기
    @Select("SELECT start_time FROM attendance_info " +
            "WHERE employee_id = #{employeeId} AND attendance_date = #{date}")
    LocalDateTime getStartTimeByEmployeeIdAndDate(String employeeId, LocalDate date);


    //퇴근기록
    @Insert("UPDATE attendance_info SET end_time = #{dto.endTime} " +
            "WHERE employee_id = #{dto.employeeId} AND attendance_date = #{dto.attendanceDate}")
    int endTimeRequest(@Param("dto") AttendanceInfoEndRequestDto dto);

    //퇴근내역찾기
    @Select("SELECT end_time FROM attendance_info " +
            "WHERE employee_id = #{employeeId} AND attendance_date = #{date}")
    LocalDateTime getEndTimeByEmployeeIdAndDate(String employeeId, LocalDate date);


    //사원년월일 사원근태정보검색
    @Select("SELECT " +
            "attendance_info_id, " +
            "attendance_status_category, " +
            "employee_id, " +
            "start_time, " +
            "end_time, " +
            "attendance_date " +
            "FROM attendance_info " +
            "WHERE attendance_date = #{attendanceDate} AND employee_id = #{employeeId} " +
            "ORDER BY ${sort} ${desc} " + // sort 파라미터가 'attendance_date'가 되도록 확실히 하세요.
            "LIMIT #{size} OFFSET #{startRow}")
    List<AttendanceInfoResponseDto> selectAttendanceByDate(@Param("attendanceDate") LocalDate attendanceDate,
                                                           @Param("employeeId") String employeeId,
                                                           @Param("sort") String sort,
                                                           @Param("desc") String desc,
                                                           @Param("size") int size,
                                                           @Param("startRow") int startRow);


    //사원년월 사원근태정보검색
    @Select("SELECT " +
            "attendance_info_id, " +
            "attendance_status_category, " +
            "employee_id, " +
            "start_time, " +
            "end_time, " +
            "attendance_date " +
            "FROM attendance_info " +
            "WHERE employee_id = #{employeeId} " +
            "AND YEAR(attendance_date) = #{year} " +
            "AND MONTH(attendance_date) = #{month} " +
            "ORDER BY ${sort} ${desc} " + // 여기에 공백 추가
            "LIMIT #{size} OFFSET #{startRow}"
    )
    List<AttendanceInfoResponseDto> selectAttendanceByMonthAndEmployee(int year, int month, String employeeId, String sort, String desc,
                                                                       int size, int startRow);

    @Select({
            "SELECT COUNT(*) FROM attendance_info WHERE employee_id = #{employeeId} AND YEAR(attendance_date) = #{year} AND MONTH(attendance_date) = #{month} "
    })
    int countAttendanceInfoByEmployeeId(@Param("employeeId") String employeeId, int year, int month);


    @Select({
            "SELECT COUNT(*) FROM attendance_info WHERE employee_id = #{employeeId} AND attendance_date = #{attendanceDate} "
    })
    int countAttendanceInfoByMonthEmployeeId(@Param("employeeId") String employeeId, LocalDate attendanceDate);

    //세션에서 attendance_info정보를 찾아오는걸로 변경
    //'지각' key값을 찾는 쿼리문이다
    @Select("SELECT * FROM attendance_status_category WHERE `key` = 'abnormal'")
    AttendanceStatusCategoryRequestDto findLateStatus();

    //todo 그럼 자바에서 저장해서 public static final

    //attendance_info테이블에 대리키를 조회해 현재상태를 만약 근태이상이라고 있으면 이거를 인정한 지각이라는 데이터로 변경한다
    @Update("UPDATE attendance_info SET attendance_status_category = #{dto.attendanceStatusCategory} WHERE attendance_info_id = #{dto.attendanceInfoId}")
    int updateAttendanceStatus(@Param("dto") AttendanceApprovalUpdateRequestDto attendanceApprovalUpdateRequestDto);
//    dto보단 key만 적는다

    //근태정보--승인 테이블에 승인을 한 내역을 남긴다
    @Insert("INSERT IGNORE INTO attendance_approval (attendance_info_id, attendance_approval_date, employee_id) VALUES (#{dto.attendanceInfoId}, NOW(), #{dto.employeeId}) ")
    int insertAttendanceApproval(@Param("dto") AttendanceApprovalInsertRequestDto attendanceApprovalInsertRequestDto);

    //근태이상확인
    //todo 근태상태가 근태이상인지 확인하고 지각승인을 할 즉 update를 할 내역조회할 쿼리가 필요하다

    @Select("SELECT attendance_approval_id , attendance_info_id , attendance_approval_date , employee_id " +
            "FROM attendance_approval " +
            "WHERE employee_id = #{employeeId} AND attendance_info_id = #{attendanceInfoId}")
    AttendanceApprovalResponseDto findAttendanceApproval(String employeeId, Long attendanceInfoId);


    //자신의 근태이상승인내역

    @Select("SELECT * FROM attendance_approval WHERE employee_id = #{employeeId} ORDER BY ${sort} ${desc} LIMIT #{size} OFFSET #{startRow}")
    List<AttendanceApprovalUpdateResponseDto> getAllEmployeeByEmployeeId(
            @Param("employeeId") String employeeId,
            @Param("sort") String sort,
            @Param("desc") String desc,
            @Param("size") int size,
            @Param("startRow") int startRow
    );

    //근태이상승이내역전체갯수조회
    @Select({
            "SELECT COUNT(*) FROM attendance_approval WHERE employee_id = #{employeeId}"
    })
    int countApprovalInfoByEmployeeId(@Param("employeeId") String employeeId);


    //본인의 조정 요청 이력 조회
    @Select(
            "SELECT attendance_appeal_request_id, status, reason, attendance_info_id, appealed_start_time, appealed_end_time, employee_id, attendance_appeal_request_time, reason_for_rejection " +
                    "FROM attendance_appeal_request " +
                    "WHERE employee_id = #{employeeId} " +
                    "ORDER BY ${sort} ${desc} " +
                    "LIMIT #{size} " +
                    "OFFSET #{startRow} "
    )
    List<AttendanceAppealMediateResponseDto> findAttendanceAppealByEmployeeId(
            @Param("employeeId") String employeeId,
            @Param("sort") String sort,
            @Param("desc") String desc,
            @Param("size") int size,
            @Param("startRow") int startRow
    );

    //본인의 조정 요청 이력 갯수확인
    @Select({
            "SELECT COUNT(*) FROM attendance_appeal_request WHERE employee_id = #{employeeId}"
    })
    int countAttendanceAppealByEmployeeId(@Param("employeeId") String employeeId);


    //이름검색
    @Select("SELECT * FROM employee WHERE name LIKE CONCAT('%', #{searchParameter}, '%')")
    List<EmployeeSearchResponseDto> searchEmployeeName(String searchParameter);

    //사원id검색
    @Select("SELECT * FROM employee WHERE employee_id LIKE CONCAT('%', #{searchParameter}, '%')")
    List<EmployeeSearchResponseDto> searchEmployeeEmployeeId(String searchParameter);


    @Select("SELECT EXISTS(SELECT 1 FROM employee WHERE employee_id = #{employeeId})")
    boolean existsById(String employeeId);

    @Select("SELECT * FROM employee WHERE employee_id = #{employeeId}")
        // 어노테이션으로 쿼리문 설정 가능
    Employee findMemberByMemberId(String employeeId);//파라미터 타입, 이름과 반환 타입. 네임스페이스 정보를 알아서 파싱한다

    @Select("select * from employee where (#{employeeId},#{employeePassword}) = (employee_id,password)")
    Employee findLoginInfoByMemberIdAndMemberPassword(String employeeId, String employeePassword);

    @Select("select count(*) from employee where employee_id = #{employeeId}")
    Integer checkEmployeeExist(String employeeId);

    @Update("update employee set attendance_manager=!attendance_manager where employee_id = #{employeeId}")
    @Options(useGeneratedKeys = true, keyProperty = "generatedKeyDto.generatedKey")
    void toggleManager(@Param("generatedKeyDto") AdminService1Impl.TempDto generatedKey, @Param("employeeId") String employeeId);

    @Select("select employee_id,name,attendance_manager,hire_year from employee order by ${orderByCondition} ${desc} LIMIT #{size} OFFSET #{startRow}")
    List<EmployeeResponseDto> getAllEmployee(@Param("orderByCondition") String sort, @Param("desc") String desc, @Param("size") int size, @Param("startRow") int startRow);

    @Select("select count(*) from employee;")
    int getEmpInfoTotalRow();

    @Select("select * from employee where employee_id = #{loginId}")
    Employee findEmployeeInfoById(String loginId);

    @Update("update employee set password = #{newPassword} where employee_id = #{loginId}")
    int updatePassword(String loginId, String newPassword);

    @Select("select employee_id from employee")
    List<String> getAllEmployeeIdWithoutPagination();

    /* result 값이 반려 이면서 본인의 반려된 연차 이력 데이터 중, 매개변수로 받은 정렬 방식, 정렬 기준 컬럼, 출력 게시물 설정값에 맞는 데이터를 반환한다 */
    @Select("SELECT vacation_request_key as vacationRequestKey, vacation_category_key as vacationCategoryKey, employee_id as employeeId, vacation_request_state_category_key as vacationRequestStateCategoryKey, vacation_quantity as vacationQuantity, vacation_start_date as vacationStartDate, vacation_end_date as vacationEndDate, reason, vacation_request_time as vacationRequestTime, reason_for_rejection as reasonForRejection, name " +
            "FROM VACATION_REQUEST INNER JOIN EMPLOYEE using(employee_id) " +
            "WHERE vacation_request_state_category_key like CONCAT('%', #{status}, '%') AND EMPLOYEE_ID=#{id} " +
            "ORDER BY ${orderByCondition} ${sortOrder} " +
            "LIMIT ${size} OFFSET ${startRow};")
    List<VacationRequestDto> getHistoryOfVacationOfMine(int size, String orderByCondition, int startRow, String sortOrder, String id, @Param("status") String status);

    /* 특정 사원의 vacation_request_state_category_key 컬럼 값이 매개변수로 전달받은 신청 결과값에 해당하는 모든 데이터를 가져온다 */
    @Select("select count(*) " +
            "from VACATION_REQUEST V INNER JOIN EMPLOYEE E ON V.employee_id = E.employee_id " +
            "WHERE V.vacation_request_state_category_key LIKE '%${status}%' AND V.EMPLOYEE_ID=#{id};")
    int getHistoryOfVacationOfMineTotalRow(String id, @Param("status") String status);

    // 입사년도 데이터 가져온다
    @Select("SELECT YEAR(hire_year) FROM EMPLOYEE WHERE employee_id=#{id}; ")
    int getHireYear(String id);


    //작년의 가장 최근 데이터에서 입사연도에 따라서, 기본 연차 부여 설정 값을 가져온다
    @Select("SELECT CASE\n" +
            "        WHEN #{year} = YEAR(NOW()) THEN freshman\n" +
            "        WHEN #{year} < YEAR(now()) THEN senior\n" +
            "       END AS settingValue\n" +
            "FROM vacation_quantity_setting " +
            "WHERE YEAR(setting_time) = YEAR(CURDATE() - INTERVAL 1 YEAR) " +
            "ORDER BY setting_time DESC LIMIT 1;")
    int getDefaultSettingVacationValue(int year);


    // 올해 조정된 데이터가 있는지 확인하여 존재시 조정 연차 개수 총합 리턴
    // 미존재시 0 리턴
    @Select("SELECT IFNULL(sum(adjust_quantity),0) " +
            "FROM vacation_adjusted_history " +
            "WHERE year(adjust_time)=year(now()) AND employee_id = #{employeeId};")
    int getVacationAdjustedHistory(String employeeId);


    // 올해 연차 승인 받은 데이터의 수
    @Select("SELECT IFNULL(SUM(vacation_quantity),0)" +
            "FROM vacation_request" +
            " WHERE EMPLOYEE_ID=#{employeeId} AND VACATION_REQUEST_STATE_CATEGORY_KEY=" +
            "'" + VACATION_REQUEST_STATUS_CATEGORY_APPROVAL + "'")
    int getApproveVacationQuantity(String employeeId);


    @Select("SELECT IFNULL(SUM(vacation_quantity),0)" +
            "FROM vacation_request" +
            " WHERE EMPLOYEE_ID=#{employeeId} AND VACATION_REQUEST_STATE_CATEGORY_KEY=" +
            "'" + VACATION_REQUEST_STATUS_CATEGORY_REQUESTED + "'")
    int getRequestedVacationQuantity(String id);

    @Select("SELECT " +
            "attendance_info_id, " +
            "attendance_status_category, " +
            "employee_id, " +
            "start_time, " +
            "end_time, " +
            "attendance_date " +
            "FROM attendance_info " +
            "WHERE employee_id = #{employeeId} " +
            "AND YEAR(attendance_date) = #{year} " +
            "AND MONTH(attendance_date) = #{month} "
    )
    List<AttendanceInfoResponseDto> getAllAttendanceInfoByIdByYearByMonth(String employeeId, Integer year, Integer month);

    //TODO : 페이지네이션 적용
    @Select("SELECT name, vacation_request.employee_id as employeeId, vacation_request_key as vacationRequestKey,vacation_category_key as vacationCategoryKey, vacation_start_date as vacationStartDate,\n" +
            "       vacation_end_date as vacationEndDate, vacation_request_time as vacationRequestTime,reason " +
            "FROM VACATION_REQUEST INNER JOIN EMPLOYEE USING(employee_id)\n" +
            "where vacation_request_state_category_key='requested'\n" +
            "ORDER BY ${sort} ${desc}\n" +
            "LIMIT #{size} OFFSET #{startRow}")
    List<AllVacationRequestResponseDto> getAllRequestedInformationOfVacation(int size, String sort, int startRow, String desc);

    @Select("SELECT count(*) FROM  vacation_request WHERE vacation_request_state_category_key=" + "'requested'")
    int countAllRequestedInformationOfVacation();

    //TODO : 페이지네이션 적용


    @Select("SELECT count(*) FROM  attendance_appeal_request WHERE status =" + "'requested'")
    int countAllRequestedInformationOfAppeal();

    @Select("select * from vacation_request where employee_id=#{employeeId} and year(vacation_start_date) =#{year} and month(vacation_start_date) = #{month}")
    List<VacationRequestResponseDto> findAllVacationRequestByEmployeeIdByYearAndByMonth(String employeeId, Integer year, Integer month);

    @Select("select employee_id from vacation_request where vacation_request_key=${requestId}")
    String findEmployeeIdByVacationRequestId(Long requestId);

    @Select("select name from employee where employee_id=${loginId}")
    String findEmployeeNameByEmployeeId(String loginId);

    @Select("SELECT attendance_appeal_request.attendance_appeal_request_id as attendanceAppealRequestId ,employee.name as name,attendance_appeal_request.employee_id as employeeId,\n" +
            "       attendance_info.start_time as startTime ,attendance_info.end_time as EndTime, attendance_appeal_request.appealed_start_time as appealedStartTime,attendance_appeal_request.appealed_end_time as appealedEndTime,\n" +
            "       attendance_info.attendance_date as attendanceDate,attendance_appeal_request.attendance_appeal_request_time as attendanceAppealRequestTime,attendance_appeal_request.reason as reason " +
            "FROM  attendance_appeal_request inner join employee using(employee_id) inner join attendance_info using(attendance_info_id) " +
            "WHERE status =" + "'requested'" + " " +
            "ORDER BY ${sort} ${desc} LIMIT #{size} OFFSET #{startRow}")
    List<AllAttendanceAppealMediateResponseDto> getAllRequestedInformationOfAppeal(int size, String sort, int startRow, String desc);
}
