package com.example.bootproject.system.util;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.repository.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Component
@RequiredArgsConstructor
public class AuthCheck {

//    private final SqlSessionFactory sqlSessionFactory;
//    public Employee getPrincipal(HttpServletRequest req){
//        HttpSession session = req.getSession(false);
//        if(session != null){
//            String ip = IpAnalyzer.getClientIp(req);
//            String employeeId = (String)session.getAttribute("employeeId");
//            try(SqlSession sqlSession = sqlSessionFactory.openSession()){
//                Employee findEmployee = sqlSession.getMapper(AuthMapper.class).authCheck(employeeId, ip);
//                return findEmployee;
//            }
//        }
//        return null;
//    }
//
//    public boolean checkManager(HttpServletRequest req){
//        HttpSession session = req.getSession(false);
//        if(session != null){
//            String ip = IpAnalyzer.getClientIp(req);
//            String employeeId = (String)session.getAttribute("employeeId");
//            try(SqlSession sqlSession = sqlSessionFactory.openSession()){
//                Employee findEmployee = sqlSession.getMapper(AuthMapper.class).authCheck(employeeId, ip);
//                return findEmployee.isManager();
//            }
//        }
//        return false;
//    }
}
