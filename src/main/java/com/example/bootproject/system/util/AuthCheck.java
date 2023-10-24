package com.example.bootproject.system.util;

import com.example.bootproject.entity.Member;
import com.example.bootproject.repository.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Component
@RequiredArgsConstructor
public class AuthCheck {

    private final SqlSessionFactory sqlSessionFactory;
    public Member getPrincipal(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        if(session != null){
            String ip = IpAnalyzer.getClientIp(req);
            String memberId = (String)session.getAttribute("member_id");
            Member findMember = sqlSessionFactory.openSession().getMapper(AuthMapper.class).authCheck(memberId, ip);
            return findMember;
        }
        return null;
    }

    public boolean checkManager(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        if(session != null){
            String ip = IpAnalyzer.getClientIp(req);
            String memberId = (String)session.getAttribute("member_id");
            Member findMember = sqlSessionFactory.openSession().getMapper(AuthMapper.class).authCheck(memberId, ip);
            return findMember.getAuth();
        }
        return false;
    }
}
