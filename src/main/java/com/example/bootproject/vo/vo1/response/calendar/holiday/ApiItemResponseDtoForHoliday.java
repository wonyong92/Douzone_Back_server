package com.example.bootproject.vo.vo1.response.calendar.holiday;

import lombok.Data;

@Data
public class ApiItemResponseDtoForHoliday {
    /*
    * <dateKind>01</dateKind>
                 <dateName>추석</dateName>
                 <isHoliday>Y</isHoliday>
                 <locdate>20150927</locdate>
                 <seq>1</seq>
    *
    *{response={header={resultCode=00, resultMsg=NORMAL SERVICE.}, body={items={item=[{dateKind=01, dateName=1월1일, isHoliday=Y, locdate=20230101, seq=1}, {dateKind=01, dateName=설날, isHoliday=Y, locdate=20230121, seq=1}, {dateKind=01, dateName=설날, isHoliday=Y, locdate=20230122, seq=1}, {dateKind=01, dateName=설날, isHoliday=Y, locdate=20230123, seq=1}, {dateKind=01, dateName=대체공휴일, isHoliday=Y, locdate=20230124, seq=1}, {dateKind=01, dateName=삼일절, isHoliday=Y, locdate=20230301, seq=1}, {dateKind=01, dateName=어린이날, isHoliday=Y, locdate=20230505, seq=1}, {dateKind=01, dateName=부처님오신날, isHoliday=Y, locdate=20230527, seq=1}, {dateKind=01, dateName=대체공휴일, isHoliday=Y, locdate=20230529, seq=1}, {dateKind=01, dateName=현충일, isHoliday=Y, locdate=20230606, seq=1}]}, numOfRows=10, pageNo=1, totalCount=18}}}  ]]]
    *
    * */
    String dateKind;
    String dateName;
    String isHoliday = "true";
    String locdate;
    String seq;
}
