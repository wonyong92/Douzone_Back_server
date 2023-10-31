package com.example.bootproject.repository.mapper3.vacation;

import com.example.bootproject.vo.vo3.request.vacation.VacationRequestDto;
import com.example.bootproject.vo.vo3.response.vacation.VacationRequestResponseDto;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;

@Mapper
public interface VacationMapper {
    @Select("select count(*) from vacation_request where employee_id=#{id} and ( " +
            " (vacation_start_date <= #{start} AND vacation_end_date >= #{end}) or" +
            " (vacation_start_date <= #{end} AND vacation_start_date >= #{start}) or" +
            "(vacation_start_date <= #{start} AND vacation_end_date <= #{end} and vacation_end_date >= #{start})" +
            ")")
    Integer checkDataRegion(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("id") String employeeId);

    @Insert("insert into douzone_test.vacation_request (vacation_category_key, employee_id, vacation_request_state_category_key, vacation_quantity, vacation_start_date, vacation_end_date, reason, vacation_request_time, reason_for_rejection)\n" +
            "values  (#{dto.vacationCategoryKey}, #{dto.employeeId}, 'requested', #{dto.vacationQuantity}, #{dto.vacationStartDate}, #{dto.vacationEndDate}, #{dto.reason}, now(), null)")
    @Options(useGeneratedKeys = true, keyProperty = "vacationRequestKey")
    Long addRequest(@Param("dto") VacationRequestDto dto);

    @Select("select * from vacation_request where vacation_request_key = #{generatedKey}")
    VacationRequestResponseDto findByVacationRequestKey(long generatedKey);
}
