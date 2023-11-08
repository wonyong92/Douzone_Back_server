package com.example.bootproject.system.util;

import com.example.bootproject.vo.vo1.request.PagedLocalDateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

//@Component
@Slf4j
@Component
public class ValidationChecker {


    public static int validationPageNum(String getPageNum) { //요청 받은 페이지에 대한 validation check
        try {
            int currentPage = Integer.parseInt(getPageNum); // 쿼리파라미터로 받아온 페이지 번호를 int 형으로 변환
            return (currentPage > 0 ? currentPage : 1); // 0보다 크면 받아온 데이터 반환, 그렇지 않으면 1 반환
        } catch (NumberFormatException e) {
            return 1; // 예외 발생시 1 반환
        }
    }

    public static boolean isAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("loginId") == null) {
            log.info("로그인 정보가 없습니다 {}", req.getRequestURL());
            return false;
        }
        return session.getAttribute("admin") != null ? (boolean) session.getAttribute("admin") : false;
    }

    /* 정렬할 컬럼이 영문자와 '_'로 이루어 져있는지 확인 후 해당 값 반환, 조건 만족하지 않으면 비어있는 값 반환 */
    public static String validationSort(String getSort) {
        return (getSort.matches("^[a-zA-Z_]+$") ? getSort : "''");
    }

    /* 정렬 방식이 desc 인지 확인 후 정렬 방식 반환, 조건 만족하지 않으면 비어있는 값 반환 */
    public static String validationDesc(String getSortOrder) {
        return (getSortOrder.matches("^(desc|DESC|)$") ? getSortOrder : "''"); // 정렬할
    }

    public static String getLoginIdOrNull(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String loginId = session.getAttribute("loginId") != null ? (String) session.getAttribute("loginId") : null;
            String sessionIp = session.getAttribute("ip") != null ? (String) session.getAttribute("ip") : null;
            if (loginId != null && sessionIp != null) {
                if (sessionIp.equals(IpAnalyzer.getClientIp(req))) {
                    if (session.getAttribute("admin") != null) {
                        log.info("admin에서는 지원하지 않는 기능 : 사원 정보 조회");
                        return null;
                    }
                    log.info("로그인 정보 확인 완료 loginId {}", loginId);
                    return loginId;
                }
                log.info("login 정보 확인 완료 but IP 변경 발생 session : {} client real IP : {} ", sessionIp, IpAnalyzer.getClientIp(req));
            }
            log.info("세선에서 로그인 정보를 확인하지 못했습니다 loginId : {} session IP : {}", loginId, sessionIp);
        }
        log.info("세션이 존재하지 않는 상태에서 요청 수행");
        return null;
    }

    public static boolean validateDateIsRealDate(PagedLocalDateDto pageRequest) { //날짜 Validation 확인
        int year = pageRequest.getYear();
        int day = pageRequest.getDay() == null ? 1 : pageRequest.getDay();
        int month = pageRequest.getMonth();

        LocalDate targetDate = LocalDate.of(year, month, day);
        if (targetDate.toString().matches("^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])")) {
            if (year < 1 || month < 1 || month > 12) // year가 양수가 아니거나, month가 1보다 작거나 12보다 클때 false
                return false;
            int[] lastDay = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //각 월별 마지막 날

            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) lastDay[1] = 29; //윤년일 때는 2월의 마지막 날을 29일로 설정

            return day >= 1 && lastDay[month - 1] >= day; // day가 1보다 작거나 마지막날 보다 클 떄 false
        }
        return false;
// 이외의 경우는 true 반환
    }

    public static boolean validateDateIsRightDateFormat(LocalDate targetDate) { // 최소 내년 이면서  'yyyy-MM-dd' 형식 인지 확인
        // 현재 날짜 가져오기
        LocalDate today = LocalDate.now();
        // targetDate의 년도를 가져와서 현재 년도보다 큰지 확인
        return targetDate.getYear() > today.getYear();
    }

    /* 신청 결과가 승인 혹은 반려가 들어왔으면 해당 값 리턴하고, 이외의 값은 빈값 리턴*/
    public static String validateVacationRequestResultStatus(String getStatus) {
        return (getStatus.matches("^(permitted|rejected|)$") ? getStatus : "''");
    }


    public static boolean isEmployeeFreshman(int freshman) { //근속 연수 1년 미만 연차 개수 validation check
        return freshman > 0;
    }

    public static boolean isEmployeeSenior(int senior) { //근속 연수 1년 이상 연차 개수 validation check
        return senior > 0;
    }

    public static boolean isManager(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String loginId = (String) session.getAttribute("loginId");
        String ip = (String) session.getAttribute("ip");

        if (loginId == null) {
            log.info("로그인 정보 없는 요청 발생");
            return false;
        }

        if (!ip.equals(IpAnalyzer.getClientIp(req))) {
            log.info("ip 변경 발생 session {} client {}", ip, IpAnalyzer.getClientIp(req));
            return false;
        }
        if (session.getAttribute("manager") != null && (boolean) session.getAttribute("manager")) {
            return true;
        }
        log.info("매니저가 아닌 유저의 요청 {} ", req.getRequestURL());
        return false;
    }

}
