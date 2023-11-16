package com.example.bootproject.controller.rest.calendar;

import com.example.bootproject.service.service3.api.CalendarService;
import com.example.bootproject.vo.vo1.request.calendar.attendanceinfo.CalendarSearchRequestDtoForAttendanceInfo;
import com.example.bootproject.vo.vo1.request.calendar.holiday.CalendarSearchRequestDtoForHoliday;
import com.example.bootproject.vo.vo1.request.calendar.vacation.CalendarSearchRequestDtoForVacation;
import com.example.bootproject.vo.vo1.response.calendar.attendanceinfo.ApiItemToEventDtoForAttendanceInfo;
import com.example.bootproject.vo.vo1.response.calendar.holiday.ApiItemToEventDtoForHoliday;
import com.example.bootproject.vo.vo1.response.calendar.vacation.ApiItemToEventDtoForVacation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.example.bootproject.system.util.ValidationChecker.getLoginIdOrNull;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;
    private final ObjectMapper obj;

    //쿼리 파라미터로 year month day를 받을 수 있다
    @GetMapping("/system/calendar/holiday")
    public ResponseEntity<List<ApiItemToEventDtoForHoliday>> getHoliday(@Valid @ModelAttribute CalendarSearchRequestDtoForHoliday dto) throws JsonProcessingException {
        List<ApiItemToEventDtoForHoliday> mappedResult = null;
        try {
            mappedResult = calendarService.getHolidayEvents(dto);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("공공 캘린더 API에서 데이터 가져오기 실패");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.info("공공 캘린더 API에서 데이터 가져오기 실패");
        } catch (JSONException e) {
            e.printStackTrace();
            log.info("공공 캘린더 API에서 데이터 가져오기 실패");
        } catch (ParseException e) {
            e.printStackTrace();
            log.info("공공 캘린더 API에서 데이터 가져오기 실패");
        }
        log.info("API 호출 결과 {} ", mappedResult);

        return ResponseEntity.ok(mappedResult);
    }

    @GetMapping("/system/calendar/attendance_info")
    public ResponseEntity<List<ApiItemToEventDtoForAttendanceInfo>> getAttendanceInfo(@Valid @ModelAttribute CalendarSearchRequestDtoForAttendanceInfo dto, HttpServletRequest req) throws JSONException, IOException, URISyntaxException, ParseException {
        String loginId = getLoginIdOrNull(req);

        List<ApiItemToEventDtoForAttendanceInfo> mappedResult = null;

        mappedResult = calendarService.getAttendanceInfoEvents(dto, loginId);


        return ResponseEntity.ok(mappedResult);
//        return null;
    }

    @GetMapping("/system/calendar/vacation_info")
    public ResponseEntity<List<ApiItemToEventDtoForVacation>> getVacationInfo(@Valid @ModelAttribute CalendarSearchRequestDtoForVacation dto, HttpServletRequest req) throws JsonProcessingException {
        String loginId = getLoginIdOrNull(req);

        List<ApiItemToEventDtoForVacation> mappedResult = null;
        mappedResult = calendarService.getVacationInfoEvents(dto, loginId);
        log.info("{}", mappedResult);
        List<ApiItemToEventDtoForVacation> appendedResult = new ArrayList<>();
        mappedResult.stream().forEachOrdered(
                entity -> {
                    if (entity.getExtendedProps().getQuantity() > 1) {
                        for (long i = 0; i < entity.getExtendedProps().getQuantity(); i++) {
                            ApiItemToEventDtoForVacation newOne = new ApiItemToEventDtoForVacation(entity, i);
                            appendedResult.add(newOne);
                        }
                    } else {
                        appendedResult.add(entity);
                    }

                }
        );
        return ResponseEntity.ok(appendedResult);
    }
}
