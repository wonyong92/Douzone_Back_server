package com.example.bootproject.service.service3.impl;

import com.example.bootproject.service.service1.EmployeeService;
import com.example.bootproject.service.service3.api.CalendarService;
import com.example.bootproject.vo.vo1.request.calendar.attendanceinfo.CalendarSearchRequestDtoForAttendanceInfo;
import com.example.bootproject.vo.vo1.request.calendar.holiday.CalendarSearchRequestDtoForHoliday;
import com.example.bootproject.vo.vo1.response.AttendanceInfoResponseDto;
import com.example.bootproject.vo.vo1.response.calendar.attendanceinfo.ApiItemToEventDtoForAttendanceInfo;
import com.example.bootproject.vo.vo1.response.calendar.holiday.ApiItemToEventDtoForHoliday;
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
import java.time.LocalDate;
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
//        Map<String,Object> param = Map.of("ServiceKey",API_KEY,"solYear", String.valueOf(dto.getYear()),"solMonth" ,dto.getMonth()!=null?String.valueOf(dto.getMonth()):"","numOfRows",100);
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=moUu8f7YSUIZr9pViDvm%2FCnqZsTX%2Btjw6y7%2BrVy1dnD239wRULAmzD675WYkDtXXL6ZIO592qNII9Tr6rqLWBg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(LocalDate.now().getYear()), "UTF-8")); /*연*/
//        urlBuilder.append("&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode(dto.getMonth() != null ? String.valueOf(dto.getMonth()) : "", "UTF-8")); /*월*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*출력 개수*/


        URL url = new URL(urlBuilder.toString());
        String result = restTemplate.getForObject(url.toURI(), String.class);

        JsonParser jsonParser = objectMapper.createParser(result);
        TreeNode itemArray = jsonParser.readValueAsTree().get("response").get("body").get("items").get("item");
        List<LinkedHashMap> itemArrayDtos = objectMapper.convertValue(itemArray, List.class);
        List<ApiItemToEventDtoForHoliday> mappedResult = itemArrayDtos.stream().map(dtos -> {
            return new ApiItemToEventDtoForHoliday(dtos);
        }).collect(Collectors.toList());
        log.info("item ||  {} ", mappedResult);
        return mappedResult;
    }

    @Override
    public List<ApiItemToEventDtoForAttendanceInfo> getAttendanceInfoEvents(CalendarSearchRequestDtoForAttendanceInfo dto, String loginId) throws IOException, URISyntaxException, JSONException, ParseException {
        log.info("getAttendanceInfoEvents - employeeId = {}", loginId);
//        Map<String,Object> param = Map.of("ServiceKey",API_KEY,"solYear", String.valueOf(dto.getYear()),"solMonth" ,dto.getMonth()!=null?String.valueOf(dto.getMonth()):"","numOfRows",100);
//        StringBuilder urlBuilder = new StringBuilder(getAttendanceInfoUrl); /*URL*/
//        urlBuilder.append("?" + URLEncoder.encode("year", "UTF-8") + dto.getYear()); /*연*/
//        urlBuilder.append("&" + URLEncoder.encode("month", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(dto.getMonth()), "UTF-8")); /*월*/
//        urlBuilder.append("&" + URLEncoder.encode("size", "UTF-8") + "=" + URLEncoder.encode("31", "UTF-8")); /*출력 개수*/
//
//        URL url = new URL(urlBuilder.toString());
//        String result = restTemplate.getForObject(url.toURI(), String.class);
//
//        JsonParser jsonParser = objectMapper.createParser(result);
//        TreeNode itemArray = jsonParser.readValueAsTree().get("data");
//        List<LinkedHashMap> itemArrayDtos = objectMapper.convertValue(itemArray, List.class);
//        List<ApiItemToEventDtoForAttendanceInfo> mappedResult = itemArrayDtos.stream().map(dtos -> {
//            return new ApiItemToEventDtoForAttendanceInfo(dtos);
//        }).collect(Collectors.toList());
//        log.info("item ||  {} ", mappedResult);
        List<AttendanceInfoResponseDto> attendanceInfoResponseDtos = employeeService.findAllAttendanceInfoOfMineByYearAndMonth(loginId, dto.getYear(), dto.getMonth());
        List<ApiItemToEventDtoForAttendanceInfo> mappedResult = attendanceInfoResponseDtos.stream().map(entity ->
                new ApiItemToEventDtoForAttendanceInfo(entity)).collect(Collectors.toList());
        return mappedResult;
    }
}
