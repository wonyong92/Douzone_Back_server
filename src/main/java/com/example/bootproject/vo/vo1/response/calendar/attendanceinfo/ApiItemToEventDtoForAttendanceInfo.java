package com.example.bootproject.vo.vo1.response.calendar.attendanceinfo;

import com.example.bootproject.vo.vo1.request.calendar.holiday.ExtendPropsForHoliday;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

import static com.example.bootproject.system.StaticString.ATTENDANCE_INFO_STATUS_ABSENT;
import static com.example.bootproject.system.StaticString.ATTENDANCE_INFO_STATUS_NORMAL;

@Data
public class ApiItemToEventDtoForAttendanceInfo {
    String title;
    String date;
    String backgroundColor = "gray";
    ExtendPropsForHoliday extendedProps = new ExtendPropsForHoliday("attendance_info");

    /*
     * 응답 데이터 생성을 위한 자료
     * 데이터의 종류 -> extendProps 저장 및 backgroundColor 설정
     * 데이터 타이틀
     * 이벤트 날짜
     * employeeId 와 같은 특수 파라미터
     *
     * 응답을 한번에 답을 수 있게 공통화, 구조화 해야할까? -> 나중에 리팩토링
     * 그냥 조회 데이터 종류에 따라 요청을 나누고 최종적으로 프론트에서 데이터를 합칠까? -> 선택
     *
     *
     * */
    public ApiItemToEventDtoForAttendanceInfo(AttendanceInfoResponseDto dto) {
        title = dto.getAttendanceStatusCategory();
        date = dto.getAttendanceDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
        String status = dto.getAttendanceStatusCategory();
        switch (status) {
            case ATTENDANCE_INFO_STATUS_ABSENT:
                this.backgroundColor = "red";
                break;
            case ATTENDANCE_INFO_STATUS_NORMAL:
                this.backgroundColor = "blue";
                break;
            default:
                this.backgroundColor = "gray";
        }
    }
}
