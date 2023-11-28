package com.example.bootproject.service.service3.api;

import com.example.bootproject.vo.vo1.request.calendar.attendanceinfo.CalendarSearchRequestDtoForAttendanceInfo;
import com.example.bootproject.vo.vo1.request.calendar.holiday.CalendarSearchRequestDtoForHoliday;
import com.example.bootproject.vo.vo1.request.calendar.vacation.CalendarSearchRequestDtoForVacation;
import com.example.bootproject.vo.vo1.response.calendar.attendanceinfo.ApiItemToEventDtoForAttendanceInfo;
import com.example.bootproject.vo.vo1.response.calendar.holiday.ApiItemToEventDtoForHoliday;
import com.example.bootproject.vo.vo1.response.calendar.vacation.ApiItemToEventDtoForVacation;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface CalendarService {
    List<ApiItemToEventDtoForHoliday> getHolidayEvents(CalendarSearchRequestDtoForHoliday dto) throws IOException, URISyntaxException, JSONException, ParseException;

    List<ApiItemToEventDtoForAttendanceInfo> getAttendanceInfoEvents(CalendarSearchRequestDtoForAttendanceInfo dto, String loginId) throws IOException, URISyntaxException, JSONException, ParseException;

    List<ApiItemToEventDtoForVacation> getVacationInfoEvents(CalendarSearchRequestDtoForVacation dto, String loginId);
}
