package com.example.bootproject.repository.mapper3.vacation;

import com.example.bootproject.vo.vo1.request.vacation.VacationAdjustRequestDto;
import com.example.bootproject.vo.vo1.request.vacation.VacationProcessRequestDto;
import com.example.bootproject.vo.vo1.request.vacation.VacationRequestDto;
import com.example.bootproject.vo.vo1.response.vacation.VacationAdjustResponseDto;
import com.example.bootproject.vo.vo1.response.vacation.VacationRequestResponseDto;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;

import static com.example.bootproject.system.StaticString.ATTENDANCE_INFO_STATUS_PENDING;
import static com.example.bootproject.system.StaticString.VACATION_REQUEST_STATE_REQUESTED;

@Mapper
public interface VacationMapper {
    @Select("select count(*) from vacation_request where employee_id=#{id} and ( " +
            " (vacation_start_date <= #{start} AND vacation_end_date >= #{end}) or" +
            " (vacation_start_date <= #{end} AND vacation_start_date >= #{start}) or" +
            "(vacation_start_date <= #{start} AND vacation_end_date <= #{end} and vacation_end_date >= #{start})" +
            ")")
    Integer checkDataRegion(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("id") String employeeId);

    @Insert("insert into douzone_test.vacation_request (vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity, vacation_start_date, vacation_end_date, reason, vacation_request_time, reason_for_rejection)\n" +
            "values  (#{dto.vacationCategoryKey}, #{dto.employeeId}, '"+VACATION_REQUEST_STATE_REQUESTED+"', #{dto.vacationQuantity}, #{dto.vacationStartDate}, #{dto.vacationEndDate}, #{dto.reason}, now(), '처리 되지 않음')")
    @Options(useGeneratedKeys = true, keyProperty = "vacationRequestKey")
    Long addRequest(@Param("dto") VacationRequestDto dto);

    @Select("select * from vacation_request where vacation_request_key = #{generatedKey}")
    VacationRequestResponseDto findByVacationRequestKey(long generatedKey);

    @Update("update vacation_request set vacation_request_state_category_key=#{dto.vacationRequestStateCategoryKey}, reason_for_rejection=#{dto.reasonForRejection} where vacation_request_key =  #{dto.vacationRequestKey}")
    @Options(useGeneratedKeys = true, keyProperty = "vacationRequestKey")
    void process(@Param("dto") VacationProcessRequestDto dto);

    @Insert("insert into douzone_test.vacation_adjusted_history (employee_id, adjust_type, adjust_time, adjust_quantity, reason)\n" +
            "values  (#{employeeId}, #{dto.adjustType}, now(), #{dto.adjustQuantity}, #{dto.reason});")
    @Options(useGeneratedKeys = true, keyProperty = "dto.generatedKey")
    void modifyVacationOfEmployee(@Param("dto") VacationAdjustRequestDto dto, @Param("employeeId") String employeeId);

    @Select("select * from vacation_adjusted_history where vacation_adjusted_history_id = #{generatedKey}")
    VacationAdjustResponseDto getModifyVacationOfEmployee(Long generatedKey);
    @Insert("INSERT INTO attendance_info (employee_id, attendance_date,attendance_status_category) " +
            "VALUES (#{dto.employeeId}, #{dto.vacationStartDate} , '"+VACATION_REQUEST_STATE_REQUESTED+"' ) " +
            "ON DUPLICATE KEY UPDATE attendance_status_category = '"+VACATION_REQUEST_STATE_REQUESTED+"'")
    void addAttendanceInfo(@Param(value = "dto")VacationRequestDto dto);

    @Delete("delete from attendance_info where employee_id=#{dto.employeeId} and attendance_date=#{dto.vacationStartDate} and attendance_status_category=' " +VACATION_REQUEST_STATE_REQUESTED+"'")
    void removeAttendanceInfo(VacationRequestResponseDto dto);
}
