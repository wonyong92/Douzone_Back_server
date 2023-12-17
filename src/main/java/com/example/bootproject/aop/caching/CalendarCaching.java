package com.example.bootproject.aop.caching;

import com.example.bootproject.vo.vo1.request.calendar.holiday.CalendarSearchRequestDtoForHoliday;
import com.example.bootproject.vo.vo1.response.calendar.holiday.ApiItemToEventDtoForHoliday;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.bootproject.ServletInitializer.cache;

@Component
@Aspect
@Slf4j
public class CalendarCaching {



    @Pointcut("execution(* com.example.bootproject.service.service3.impl.CalendarServiceImpl.getHolidayEvents(..)) && args(dto, ..)")
    public void getHolidayMethod(CalendarSearchRequestDtoForHoliday dto) {
    }

    @Around("getHolidayMethod(dto)")
    public Object cacheHolidayResponse(ProceedingJoinPoint joinPoint, CalendarSearchRequestDtoForHoliday dto) throws Throwable {
        String cacheKey = generateCacheKey(dto);
        log.info("cacheHolidayResponse key : {} ",cacheKey);
        if (cache.containsKey(cacheKey)) {
        log.info("cache matched!! : {} ",cache.get(cacheKey));
            return cache.get(cacheKey);
        }
        log.info("no cache matched!! : {} ",cache.get(cacheKey));
        List<ApiItemToEventDtoForHoliday> result = (List<ApiItemToEventDtoForHoliday>) joinPoint.proceed();
            cache.put(cacheKey, result);
            log.info("cache saved : {}",cache)  ;
            return result;
    }

    private String generateCacheKey(CalendarSearchRequestDtoForHoliday dto) {
        return dto.getYear()+"-"+dto.getMonth();
    }
}
