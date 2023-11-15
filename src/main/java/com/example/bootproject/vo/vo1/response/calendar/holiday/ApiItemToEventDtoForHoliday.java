package com.example.bootproject.vo.vo1.response.calendar.holiday;

import com.example.bootproject.vo.vo1.request.calendar.holiday.ExtendPropsForHoliday;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class ApiItemToEventDtoForHoliday {
    String title;
    String date;
    String backgroundColor = "red";
    ExtendPropsForHoliday extendedProps = new ExtendPropsForHoliday("holiday");

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
    public ApiItemToEventDtoForHoliday(LinkedHashMap dto) {
        title = dto.get("dateName").toString();
        date = new StringBuilder(dto.get("locdate").toString()).insert(4, '-').insert(7, '-').toString();
    }
}
