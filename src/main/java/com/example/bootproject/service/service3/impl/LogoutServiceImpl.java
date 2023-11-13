package com.example.bootproject.service.service3.impl;

import com.example.bootproject.repository.mapper3.login.AuthMapper;
import com.example.bootproject.repository.mapper3.login.SessionLoginMapper;
import com.example.bootproject.service.service3.api.LogoutService;
import com.example.bootproject.system.util.IpAnalyzer;
import com.example.bootproject.vo.vo1.response.login.LoginResponseDto;
import com.example.bootproject.vo.vo1.response.logout.LogoutResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LogoutServiceImpl implements LogoutService {
    private final HttpSession session;

    private final AuthMapper authMapper;
    private final HttpServletResponse resp;
    private final HttpServletRequest req;
    private final SessionLoginMapper sessionLoginMapper;

    @Override
    public LogoutResponseDto logout() {
        /*
         * 로그인 정보가 제대로 있는 지 확인
         * 세션 비활성화
         * JSESSIONID 삭제
         * 기존 로그인 정보에서 logout_time 최신화
         *
         * */
        /*로그인 정보 재확인*/
        String sessionLoginId = session.getAttribute("loginId") != null ? (String) session.getAttribute("loginId") : null;
        String sessionIp = session.getAttribute("ip") != null ? (String) session.getAttribute("ip") : null;
        LoginResponseDto dto = authMapper.checkAuthInformation(sessionLoginId, sessionIp);

        if (dto == null) {
            log.info("로그아웃 시도 중 auth 테이블에서 인증 정보 확인 실패 - 세션에서 검사 합니다");
            if (sessionLoginId != null && sessionIp != null) {
                log.info("auth 테이블에는 인증정보가 없지만 session이 살아있는 상태");
                session.invalidate();
                Cookie cookie = new Cookie("JSESSIONID", null);
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
                log.info("세션 정리 결과 - null 데이터가 출력되어야 합니다. sessionLoginId {} sessionLoginIp {}", session.getAttribute("loginId"), session.getAttribute("ip"));
                sessionLoginMapper.withOutAuthDataLogout(sessionLoginId, sessionIp);
                LogoutResponseDto response = authMapper.logoutResult(sessionLoginId);
                log.info("로그아웃 완료");
                return response;
            }
        }
        if (dto != null) {
            if (dto.getLogoutTime() == null) {
                log.info("login 정보 확인 {} ", dto);
                /*로그인 정보가 존재하는 경우*/
                /*TODO : ip 검사 수행 필요? - 일단 스킵*/
                /*세션의 정보 비우기*/
                session.invalidate();
                Cookie cookie = new Cookie("JSESSIONID", null);
                cookie.setMaxAge(0);
                resp.addCookie(cookie);

                /*auth 테이블 정리*/
                authMapper.logout(sessionLoginId);
                LogoutResponseDto response = authMapper.logoutResult(sessionLoginId);
                log.info("로그아웃 완료");
                return response;
            }
        }
        log.info("login 정보가 없이 로그아웃 요청 발생 - 다시 로그인하고 로그아웃 하세요 {}", IpAnalyzer.getClientIp(req));
        return null;
    }
}
