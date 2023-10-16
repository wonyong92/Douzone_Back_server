package com.example.bootproject.controller.rest;

import com.example.bootproject.entity.Member;
import com.example.bootproject.repository.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/",""})
@RestController
@RequiredArgsConstructor
public class MybatisTestController {
    private final SqlSessionFactory sqlSessionFactory;
    @GetMapping("/test")
    public Member getMemberTest(){
        Member result = sqlSessionFactory.openSession().getMapper(MemberMapper.class).findMemberByMemberId("test");
        return result;
    }
}
