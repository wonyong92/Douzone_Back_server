package com.example.bootproject.repository.mapper3.appeal;

import com.example.bootproject.vo.vo1.request.appeal.AppealProcessRequestDto;
import com.example.bootproject.vo.vo1.request.appeal.AppealRequestDto;
import com.example.bootproject.vo.vo1.response.appeal.AppealRequestResponseDto;
import org.apache.ibatis.annotations.*;

import static com.example.bootproject.system.StaticString.APPEAL_REQUEST_STATE_REQUESTED;

@Mapper
public interface AppealMapper {
    @Select("select count(*) from attendance_appeal_request where attendance_info_id=#{attId}")
    Integer checkAppealRequestExist(@Param("attId") Long attendanceInfoId);

    //TODO: 여기까지 했음 - 쿼리 마저 작성하면 완성 할듯
    @Insert("INSERT INTO attendance_appeal_request (\n" +
            "    status,\n" +
            "    reason,\n" +
            "    attendance_info_id,\n" +
            "    appealed_start_time,\n" +
            "    appealed_end_time,\n" +
            "    employee_id,\n" +
            "    attendance_appeal_request_time,\n" +
            "    reason_for_rejection\n" +
            ") VALUES (\n'" +
                APPEAL_REQUEST_STATE_REQUESTED +
            "',    #{dto.reason},\n" +
            "    #{dto.attendanceInfoId},\n" +
            "    #{dto.appealedStartTime},\n" +
            "    #{dto.appealedEndTime},\n" +
            "    #{dto.employeeId},\n" +
            "    now(),\n" +
            "    '처리 되지 않음'\n" +
            ");\n")
    @Options(useGeneratedKeys = true, keyProperty = "dto.attendanceAppealRequestId")
    Long makeRequest(@Param("dto") AppealRequestDto dto);

    @Select("select * from attendance_appeal_request where attendance_appeal_request_id = #{generatedKey} ")
    AppealRequestResponseDto findById(Long generatedKey);

    @Update("update attendance_appeal_request set status=#{dto.status}, reason_for_rejection=#{dto.reasonForRejection} where attendance_appeal_request_id =  #{dto.attendanceAppealRequestId}")
    @Options(useGeneratedKeys = true, keyProperty = "attendanceAppealRequestId")
    void process(@Param("dto") AppealProcessRequestDto dto);
    @Update("update attendance_info set attendance_status_category=${dto.status}")
    void changeAttendanceInfo(AppealProcessRequestDto dto);
}
