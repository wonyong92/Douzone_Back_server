package com.example.bootproject.vo.vo1.response.calendar.vacation;

import com.example.bootproject.vo.vo1.request.calendar.vacation.ExtendPropsForVacation;
import com.example.bootproject.vo.vo1.response.vacation.VacationRequestResponseDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.example.bootproject.system.StaticString.*;

@Data
public class ApiItemToEventDtoForVacation {
    String title;
    String date;
    String backgroundColor = "red";
    ExtendPropsForVacation extendedProps;

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
    public ApiItemToEventDtoForVacation(VacationRequestResponseDto dto) {

        title = dto.getVacationRequestStateCategoryKey()+" - " + dto.getVacationStartDate() + "~" + dto.getVacationEndDate();
        date = dto.getVacationStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String status = dto.getVacationRequestStateCategoryKey();
        /*
        * - 연차 요청 중 (노랑) #FFFACD
        - 연차 요청 승인 (파랑) #ADD8E6
        - 연차 요청 반려 (빨강) #FFB6C1
        *
        * */
        switch(status){
            case VACATION_REQUEST_STATE_REQUESTED:
                this.backgroundColor="#FFFACD";
                        break;
            case VACATION_REQUEST_STATE_REJECTED:
                this.backgroundColor="#FFB6C1";
                        break;
            case VACATION_REQUEST_STATE_PERMITTED:
                this.backgroundColor="#ADD8E6";
                        break;
            default:
                this.backgroundColor="red";
        }
        extendedProps = new ExtendPropsForVacation("vacationRequested", dto.getVacationQuantity(), dto.getVacationRequestStateCategoryKey(), dto.getVacationRequestKey());
    }

    public ApiItemToEventDtoForVacation(ApiItemToEventDtoForVacation old, long dayOffSet) {
        title = old.getTitle();
        date = (LocalDate.parse(old.date).plusDays(dayOffSet)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        extendedProps = old.getExtendedProps();
    }
}
