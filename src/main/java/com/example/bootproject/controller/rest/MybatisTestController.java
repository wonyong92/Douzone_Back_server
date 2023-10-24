package com.example.bootproject.controller.rest;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.repository.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/",""})
@RestController
@RequiredArgsConstructor
@Slf4j
public class MybatisTestController {
    private final SqlSessionFactory sqlSessionFactory;
    @GetMapping("/test")
    public Employee getMemberTest(){
        log.info("test");
        Employee result;
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            result = sqlSession.getMapper(EmployeeMapper.class).findMemberByMemberId("test");
            return result;
        }
    }
}
