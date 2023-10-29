package com.example.bootproject.service.service3.impl;

import com.example.bootproject.repository.mapper3.login.AuthMapper;
import com.example.bootproject.service.service3.api.LogoutService;
import com.example.bootproject.system.util.IpAnalyzer;
import com.example.bootproject.vo.vo3.response.login.LoginResponseDto;
import com.example.bootproject.vo.vo3.response.logout.LogoutResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutServiceImpl implements LogoutService {
    private final HttpSession session;

    private final AuthMapper authMapper;
    private final HttpServletResponse resp;
    private final HttpServletRequest req;

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
        String sessionLoginId = (String) session.getAttribute("loginId");
        String sessionIp = (String) session.getAttribute("ip");
        LoginResponseDto dto = authMapper.checkAuthInformation(sessionLoginId, sessionIp);
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
                return response;
            }
        }
        log.info("login 정보가 없이 로그아웃 요청 발생 {}", IpAnalyzer.getClientIp(req));
        return null;
    }
}
