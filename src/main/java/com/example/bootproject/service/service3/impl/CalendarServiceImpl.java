package com.example.bootproject.service.service3.impl;

import com.example.bootproject.service.service1.EmployeeService;
import com.example.bootproject.service.service3.api.CalendarService;
import com.example.bootproject.vo.vo1.request.calendar.attendanceinfo.CalendarSearchRequestDtoForAttendanceInfo;
import com.example.bootproject.vo.vo1.request.calendar.holiday.CalendarSearchRequestDtoForHoliday;
import com.example.bootproject.vo.vo1.request.calendar.vacation.CalendarSearchRequestDtoForVacation;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import com.example.bootproject.vo.vo1.response.calendar.attendanceinfo.ApiItemToEventDtoForAttendanceInfo;
import com.example.bootproject.vo.vo1.response.calendar.holiday.ApiItemToEventDtoForHoliday;
import com.example.bootproject.vo.vo1.response.calendar.vacation.ApiItemToEventDtoForVacation;
import com.example.bootproject.vo.vo1.response.vacation.VacationRequestResponseDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarServiceImpl implements CalendarService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final EmployeeService employeeService;
    @Value("${url.getAttendanceByMonthAndEmployee}")
    String getAttendanceInfoUrl;
    @Value(value = "${calendar.api.key}")
    private String API_KEY;

    /*
     * RestTemplate 사용시 인코딩으로 문제가 발생하므로 디코딩 키를 사용하는 것을 추천 -> 그래도 문제가 발생하면 URI를 객체를 이용하여 요청 생성
     *
     * JSON 응답에 대해 ObjectMapper(Jackson) 을 이용하여 파서를 생성
     * 트리로 읽어오기
     * 트리에서 필요한 데이터를 get을 이용하여 찾아오기
     * 결과로 TreeNode 가 반환된다 -> 반환된 TreeNode를 List 형태로 변환 -> 트리노드는 내부적으로 LinkedHashMap으로 구성 되어있다
     * 이걸 스트림을 통해 원하는 타입으로 변경하여 반환
     * */
    public List<ApiItemToEventDtoForHoliday> getHolidayEvents(CalendarSearchRequestDtoForHoliday dto) throws IOException, URISyntaxException, JSONException, ParseException {
        log.info("api key = {}", API_KEY);
        log.info("dto = {}", dto.toString());
//        Map<String,Object> param = Map.of("ServiceKey",API_KEY,"solYear", String.valueOf(dto.getYear()),"solMonth" ,dto.getMonth()!=null?String.valueOf(dto.getMonth()):"","numOfRows",100);
        /*URL*/
        String urlBuilder = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo" + "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=moUu8f7YSUIZr9pViDvm%2FCnqZsTX%2Btjw6y7%2BrVy1dnD239wRULAmzD675WYkDtXXL6ZIO592qNII9Tr6rqLWBg%3D%3D" + /*Service Key*/
                "&" + URLEncoder.encode("solYear", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(dto.getYear()), StandardCharsets.UTF_8) + /*연*/
                "&" + URLEncoder.encode("solMonth", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.format("%02d", dto.getMonth()), StandardCharsets.UTF_8) + /*월*/
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("100", StandardCharsets.UTF_8); /*출력 개수*/

        URL url = new URL(urlBuilder);
        log.info("request URI {}", url.toURI());
        String result = restTemplate.getForObject(url.toURI(), String.class);
        log.info("api 요청 결과 {}", result);
        JsonParser jsonParser = objectMapper.createParser(result);
        TreeNode itemArray = jsonParser.readValueAsTree().get("response").get("body").get("items").get("item");
        if (itemArray == null) {
            return List.of();
        }
        if (itemArray.isArray()) {
            List<LinkedHashMap> itemArrayDtos = objectMapper.convertValue(itemArray, List.class);
            if (itemArrayDtos != null) {
                List<ApiItemToEventDtoForHoliday> mappedResult = itemArrayDtos.stream().map(dtos -> {
                    return new ApiItemToEventDtoForHoliday(dtos);
                }).collect(Collectors.toList());
                log.info("item ||  {} ", mappedResult);
                return mappedResult;
            }
        }
        LinkedHashMap itemArrayDto = objectMapper.convertValue(itemArray, LinkedHashMap.class);
        if (itemArrayDto != null) {
            ApiItemToEventDtoForHoliday mappedResult = new ApiItemToEventDtoForHoliday(itemArrayDto);
            log.info("item ||  {} ", mappedResult);
            return List.of(mappedResult);
        }
        return List.of();
    }

    @Override
    public List<ApiItemToEventDtoForAttendanceInfo> getAttendanceInfoEvents(CalendarSearchRequestDtoForAttendanceInfo dto, String loginId) {
        log.info("getAttendanceInfoEvents - employeeId = {}", loginId);
        List<AttendanceInfoResponseDto> attendanceInfoResponseDtos = employeeService.findAllAttendanceInfoOfMineByYearAndMonth(loginId, dto.getYear(), dto.getMonth());
        List<ApiItemToEventDtoForAttendanceInfo> mappedResult = attendanceInfoResponseDtos.stream().map(entity ->
                new ApiItemToEventDtoForAttendanceInfo(entity)).collect(Collectors.toList());
        return mappedResult;
    }

    @Override
    public List<ApiItemToEventDtoForVacation> getVacationInfoEvents(CalendarSearchRequestDtoForVacation dto, String loginId) {
        log.info("getAttendanceInfoEvents - employeeId = {}", loginId);
        List<VacationRequestResponseDto> attendanceInfoResponseDtos = employeeService.findAllVacationRequestByEmployeeIdByYearAndByMonth(loginId, dto.getYear(), dto.getMonth());
        List<ApiItemToEventDtoForVacation> mappedResult = attendanceInfoResponseDtos.stream().map(entity ->
                new ApiItemToEventDtoForVacation(entity)).collect(Collectors.toList());
        return mappedResult;
    }
}
