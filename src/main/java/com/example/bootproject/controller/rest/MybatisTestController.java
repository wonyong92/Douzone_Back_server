package com.example.bootproject.controller.rest;

import com.example.bootproject.entity.Employee;
import com.example.bootproject.repository.mapper.EmployeeMapper1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/",""})
@RestController
@RequiredArgsConstructor
@Slf4j
public class MybatisTestController {
    private final SqlSessionFactory sqlSessionFactory;
//    @GetMapping("/test")
//    public Employee getMemberTest(){
//        log.info("test");
//        Employee result;
//        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
//            result = sqlSession.getMapper(EmployeeMapper1.class).findMemberByMemberId("test");
//            return result;
//        }
//    }

//    @GetMapping("/test2")
//    public Employee getMemberTest2(@RequestParam(name="data") String data){
//        log.info("test");
//        log.info(data);
//        Employee result;
//        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
//            result = sqlSession.getMapper(EmployeeMapper1.class).findMemberByMemberId("test");
//            return result;
//        }
    }

