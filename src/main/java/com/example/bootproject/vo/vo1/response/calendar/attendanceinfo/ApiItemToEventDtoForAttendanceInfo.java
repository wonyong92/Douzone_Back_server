package com.example.bootproject.vo.vo1.response.calendar.attendanceinfo;

import com.example.bootproject.vo.vo1.request.calendar.attendanceinfo.ExtendPropsForAttendanceInfo;
import com.example.bootproject.vo.vo1.request.calendar.holiday.ExtendPropsForHoliday;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import lombok.Data;

import java.time.format.DateTimeFormatter;

import static com.example.bootproject.system.StaticString.*;

@Data
public class ApiItemToEventDtoForAttendanceInfo {
    String title;
    String date;
    String backgroundColor = "gray";
    ExtendPropsForAttendanceInfo extendedProps = null;

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
        date = dto.getAttendanceDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String status = dto.getAttendanceStatusCategory();
//        2. 근태 정보
//        - 근태 판정 중 (민트) #AAFFBC
//                - 정상 근태 (하늘) #78E8F7
//                - 이상 근태 (분홍) #FFA6EC
//
//        3. 근태 조정
//        조정 요청 중 (연한 노란색) #FFFF86
//        조정 요청 승인 (초록) #61F059
//        조정 요청 반려(주황) #FF9933
        switch (status) {
            // 정상 근태
            case ATTENDANCE_INFO_STATUS_NORMAL:
                this.backgroundColor = "#78E8F7";
                break;
            // 근태 판정 중
            case ATTENDANCE_INFO_STATUS_PENDING:
                this.backgroundColor = "#AAFFBC";
                break;
            // 조정 요청 중
            case ATTENDANCE_INFO_STATUS_REQUESTED:
                this.backgroundColor = "#FFFF86";
                break;
            // 조정 요청 승인
            case APPEAL_REQUEST_STATE_PERMITTED:
                this.backgroundColor = "#61F059";
                break;
            // 조정 요청 반려
            case APPEAL_REQUEST_STATE_REJECTED:
                this.backgroundColor = "#FF9933";
                break;
            ////이상 근태
            case ATTENDANCE_INFO_STATUS_ABSENT:
            case ATTENDANCE_INFO_STATUS_NORMAL_START_EARLY_END:
            case ATTENDANCE_INFO_STATUS_LATE_START_EARLY_END:
            case ATTENDANCE_INFO_STATUS_LATE_START_NORMAL_END:
            case ATTENDANCE_INFO_STATUS_LATE_START_NULL_END:
            case ATTENDANCE_INFO_STATUS_NORMAL_START_NULL_END:
                this.backgroundColor = "#FFA6EC";
                break;
            // 승인 이상
            case ATTENDANCE_INFO_STATUS_ABSENT_APPROVED:
            case ATTENDANCE_INFO_STATUS_NORMAL_START_EARLY_END_APPROVED:
            case ATTENDANCE_INFO_STATUS_LATE_START_EARLY_END_APPROVED:
            case ATTENDANCE_INFO_STATUS_LATE_START_NORMAL_END_APPROVED:
            case ATTENDANCE_INFO_STATUS_LATE_START_NULL_END_APPROVED:
            case ATTENDANCE_INFO_STATUS_NORMAL_START_NULL_END_APPROVED:
                this.backgroundColor = "grey";
                break;
            //


            default:
                this.backgroundColor = "gray";
        }
        this.extendedProps = new ExtendPropsForAttendanceInfo("attendance_info",status,String.valueOf(dto.getAttendanceInfoId()));
    }
}
