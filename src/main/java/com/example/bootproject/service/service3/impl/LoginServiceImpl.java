package com.example.bootproject.service.service3.impl;

import com.example.bootproject.repository.mapper3.login.AdminLoginMapper;
import com.example.bootproject.repository.mapper3.login.EmployeeLoginMapper;
import com.example.bootproject.repository.mapper3.login.SessionLoginMapper;
import com.example.bootproject.service.service3.api.LoginService;
import com.example.bootproject.vo.vo3.request.LoginRequestDto;
import com.example.bootproject.vo.vo3.response.login.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import static com.example.bootproject.system.StaticString.SESSION_ID_NOT_MATCHED_LOGIN_REQUEST;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LoginServiceImpl implements LoginService {

    private final EmployeeLoginMapper employeeLoginMapper;
    private final AdminLoginMapper adminLoginMapper;
    //    private final HttpSession session;
    private final SessionLoginMapper sessionLoginMapper;


    @Override
    public LoginResponseDto sessionLogin(LoginRequestDto dto, HttpServletRequest req) {
        /*
         * 세션 로그인을 통해 동시로그인 방지 기능을 구현
         * jsessionId + client Ip로 검사 - 내부망에서 각자 할당된 IP로 로그인을 시도할 것이므로 세션이 유지된 상태에서 IP 변경이 발생하지 않아야 한다
         * 세션에서 loginId, ip attribute 찾아 보기
         * 존재하지 않는 경우 null 반환
         * 존재하는 경우 현재 ip와 ip attribute 가 동일한지 확인
         * 동일한 경우 로그인 정보 유지 및 loginResponseDto 반환
         * 동일하지 않은 경우 jsessionId 탈취 가능성 높음 - 응답으로 IP가 변경되었음을 알리고 기존의 로그인 정보를 로그아웃 시킨다
         * null이 아닌 닫른 데이터를 반환해주어 감지가 필요하다
         * 재로그인후 세션 로그인을 이용할 수 있도록 한다
         * */
        log.info("session login 수행");
        LoginResponseDto loginResult = null;
        HttpSession session = req.getSession(false);
        log.info("session login 검삭 결과 {}", !(session == null || session.getAttribute("loginId") == null));
        if (session == null || session.getAttribute("loginId") == null) {
            /*세션이 존재하지 않는 경우 null 반환*/

        } else {

            String sessionLoginId = (String) session.getAttribute("loginId");
            if (!dto.getLoginId().equals(sessionLoginId)) {
                log.info("login 요청자와 세션의 아이디가 다릅니다 - 로그아웃이 필요합니다");
                LoginResponseDto result = new LoginResponseDto();
                result.setMessage(SESSION_ID_NOT_MATCHED_LOGIN_REQUEST);
                return result;
            }
            String sessionLoginIp = (String) session.getAttribute("ip");
            /*세션에서 인증 정보 찾기*/
            String ip = dto.getIp();
            if (sessionLoginId == null || sessionLoginIp == null) {
                /*세션에 인증 정보가 없는 경우 null 반환*/
                log.info("세션에서 인증 정보 찾지 못함");
                return null;
            }
            if (!sessionLoginIp.equals(ip)) {
                /*ip 변경 감지 : 세션에 저장된 ip와 현재 접속 ip가 다른 경우*/
                log.info("ip 변경 발생");
                //TODO: ip 변경 감지 - 별도 처리 응답 데이터 선정 필요
                return new LoginResponseDto(sessionLoginId, sessionLoginIp);
            }
            /*
             * loginId, ip를 세션에서 확인 완료
             * TODO :  추가적으로 필요한 인증 정보를 auth repository와 employee 테이블에서 가져온다
             * */
            sessionLoginMapper.updateAuthInforamtion(sessionLoginId, sessionLoginIp);
            /*업데이트 후 재조회*/
            loginResult = sessionLoginMapper.sessionLogin(sessionLoginId, sessionLoginIp);
        }
        return loginResult;
    }

    @Override
    public LoginResponseDto formLogin(LoginRequestDto dto, HttpServletRequest req) {
        /*
         * validation check, sanitizing 은 컨트롤러에서 수행
         * dto 에서 String 타입인 loginId와 String 타입인 password 를 가져온다
         * 리포지토레이서 로그인 정보를 검색하고(id, password 기반) 사원 정보 혹은 ADMIN 정보를 가져온다
         * ADMIN 의 경우 아이디를 문자열로 시작하도록 하고 사원의 경우 사원번호로만 이루어지게 제한하여 기능을 쉽게 분기하도록 한다
         * */
        boolean loginType = checkAdmin(dto);

        log.info("login 유형 : {}", loginType ? "employee" : "admin");

        LoginResponseDto loginResult = null;

        if (loginType) {
            loginResult = employeeLogin(dto, req);
        } else {
            loginResult = adminLogin(dto, req);
        }
        return loginResult;

    }


    private LoginResponseDto employeeLogin(LoginRequestDto dto, HttpServletRequest req) {
        /*
         * employee 로그인 수행
         * employee join auth 테이블에서 아이디와 패스워드 + 로그인 정보 한번에 가져오기
         * 로그아웃 여부 확인
         * 로그아웃 데이터가 있다면? - 로그인 정보 update
         * 로그인 데이터가 없다면? - 서버 구동 후 최초 로그인, 로그인 정보 insert
         *
         * */
        HttpSession session = req.getSession(false);
        LoginResponseDto loginResult = employeeLoginMapper.employeeLogin(dto);
        log.info("ip, password 검색 결과 = {}", employeeLoginMapper.employeeLogin(dto));
        /*employee 테이블에서 입력된 아이디와 패스워드로 인증 정보 검색*/
        if (loginResult == null) {

            /*
             * TODO : 인증 데이터가 없다면? - 로그인 실패 - id, password 매칭 실패 응답 데이터 선정 필요
             * */
        } else {
            /*
             * 인증 정보 검색 성공시 기존의 로그인 정보가 있는지 확인하여 중복 로그인을 방지
             * 재로그인의 경우
             * 1, 조회한 인증 정보에서 로그아웃 타임을 초기화하고, 기존의 로그인 시각을 최신화(로그인 데이터를 간결하게 관리 가능)
             * 2. 그냥 새롭게 인증 정보 삽입(과거 기록 확인 가능) -> 근데 로그 기록을 남길꺼니까 중복 데이터가 된다
             * 최초 로그인 경우
             * 인증 정보는 존재하지만
             *
             * */
            log.info("employee form 로그인 수행 {}", dto);
            log.info("employee form 로그인 수행 결과 {}", loginResult);
            LocalDateTime logoutTime = loginResult.getLogoutTime();
            LocalDateTime loginTime = loginResult.getLoginTime();
            String loginIp = loginResult.getIp();
            String clientIp = dto.getIp();
            if (logoutTime == null && loginTime != null) {
                /*
                 * 로그아웃 데이터가 있다면? - 재로그인, 로그인 정보 update - 로그인 시간을 현재로 최신화, 로그아웃 시간 제거
                 * */

                if (!loginIp.equals(clientIp)) {
                    log.info("ip 변경 발생");
                    /*TODO : ip 변경 감지 및 처리 로직 수행*/
                    /*TODO : 기존 로그인 정보 제거 - 로그아웃 수행, 응답 데이터에 로그아웃 수행 내용을 담아 전달*/
                    return new LoginResponseDto(loginResult.getLoginId(), loginIp);
                } else {
                    log.info("employee form 재로그인 요청");
                    employeeLoginMapper.updateAuthInforamtion(loginResult);
                    /*업데이트 후 재조회*/
                    loginResult = employeeLoginMapper.employeeLogin(dto);
                    log.info("employee form 재로그인 {}", loginResult);
                    if (session == null) {
                        session = req.getSession(true);
                        log.info("기존 로그인 정보가 있으나 JSESSIONID 쿠키 삭제로 세션을 찾지 못하여 다사 세션 생성");
                    }
                    session.setAttribute("loginId", loginResult.getLoginId());
                    session.setAttribute("ip", loginResult.getIp());
                    session.setAttribute("manager", loginResult.isManager());
                }
            } else {
                /*
                 * 로그인, 로그아웃 시간 모두 null 인 경우 최초 로그인
                 * */
                log.info("employee form 최초 로그인 {}");
                /*현재 요청 IP를 삽입하기 위하여 dto에 ip 할당*/
                loginResult.setIp(clientIp);

                employeeLoginMapper.insertAuthInforamtion(loginResult);
                /*로그인 성공 후 재조회*/
                loginResult = employeeLoginMapper.employeeLogin(dto);
                log.info("employee form 최초 로그인 {}", loginResult);
                if (session == null) {
                    session = req.getSession(true);
                    log.info("새롭게 세션 생성");
                }
                session.setAttribute("loginId", loginResult.getLoginId());
                session.setAttribute("ip", loginResult.getIp());
                session.setAttribute("manager", loginResult.isManager());
            }
        }
        return loginResult;
    }

    private LoginResponseDto adminLogin(LoginRequestDto dto, HttpServletRequest req) {
        /*
         * employee 로그인 수행
         * employee join auth 테이블에서 아이디와 패스워드 + 로그인 정보 한번에 가져오기
         * 로그아웃 여부 확인
         * 로그아웃 데이터가 있다면? - 로그인 정보 update
         * 로그인 데이터가 없다면? - 서버 구동 후 최초 로그인, 로그인 정보 insert
         *
         * */
        HttpSession session = req.getSession(false);
        LoginResponseDto loginResult = adminLoginMapper.adminLogin(dto);
        log.info("loginResult = {}", adminLoginMapper.adminLogin(dto));
        /*admin 테이블에서 입력된 아이디와 패스워드로 인증 정보 검색*/
        if (loginResult == null) {

            /*
             * TODO : 인증 데이터가 없다면? - 로그인 실패 - id, password 매칭 실패 응답 데이터 선정 필요
             * */
        } else {
            /*
             * 인증 정보 검색 성공시 기존의 로그인 정보가 있는지 확인하여 중복 로그인을 방지
             * 재로그인의 경우
             * 1, 조회한 인증 정보에서 로그아웃 타임을 초기화하고, 기존의 로그인 시각을 최신화(로그인 데이터를 간결하게 관리 가능)
             * 2. 그냥 새롭게 인증 정보 삽입(과거 기록 확인 가능) -> 근데 로그 기록을 남길꺼니까 중복 데이터가 된다
             * 최초 로그인 경우
             * 인증 정보는 존재하지만
             *
             * */
            log.info("admin form 로그인 수행 {}", dto);
            log.info("admin form 로그인 수행 결과 {}", loginResult);
            LocalDateTime logoutTime = loginResult.getLogoutTime();
            LocalDateTime loginTime = loginResult.getLoginTime();
            String loginIp = loginResult.getIp();
            String clientIp = dto.getIp();
            if (logoutTime == null && loginTime != null) {
                /*
                 * 로그아웃 데이터가 있다면? - 재로그인, 로그인 정보 update - 로그인 시간을 현재로 최신화, 로그아웃 시간 제거
                 * */

                if (!loginIp.equals(clientIp)) {
                    log.info("ip 변경 발생");
                    /*TODO : ip 변경 감지 및 처리 로직 수행*/
                    /*TODO : 기존 로그인 정보 제거 - 로그아웃 수행 -> 응답 데이터에 로그아웃 수행 내용을 담아 전달 -> 프론트에서 로그아웃 수행하도록 설계*/
                    return new LoginResponseDto(loginResult.getLoginId(), loginIp);
                } else {
                    log.info("admin form 재로그인 요청");
                    adminLoginMapper.updateAuthInforamtion(loginResult);
                    /*업데이트 후 재조회*/
                    loginResult = adminLoginMapper.adminLogin(dto);
                    log.info("admin form 재로그인 {}", loginResult);
                    if (session == null) {
                        session = req.getSession(true);
                        log.info("기존 로그인 정보가 있으나 JSESSIONID 쿠키 삭제로 세션을 찾지 못하여 다사 세션 생성");
                    }
                    session.setAttribute("loginId", loginResult.getLoginId());
                    session.setAttribute("ip", loginResult.getIp());
                    session.setAttribute("admin", true);
                }
            } else {
                /*
                 * 로그인, 로그아웃 시간 모두 null 인 경우 최초 로그인
                 * */
                log.info("admin form 최초 로그인 {}");
                /*현재 요청 IP를 삽입하기 위하여 dto에 ip 할당*/
                loginResult.setIp(clientIp);

                adminLoginMapper.insertAuthInforamtion(loginResult);
                /*로그인 성공 후 재조회*/
                loginResult = adminLoginMapper.adminLogin(dto);
                log.info("admin form 최초 로그인 {}", loginResult);
                if (session == null) {
                    session = req.getSession(true);
                    log.info("새롭게 세션 생성");
                }
                session.setAttribute("loginId", loginResult.getLoginId());
                session.setAttribute("ip", loginResult.getIp());
                session.setAttribute("admin", true);
            }
        }
        return loginResult;
    }

    boolean checkAdmin(LoginRequestDto dto) {
        String loginId = dto.getLoginId();
        //사원 번호 로그인 경우 true 반환
        return loginId.matches("^[0-9]*$");
    }
}
