package com.example.bootproject.repository.mapper;

import com.example.bootproject.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface MemberMapper {
    @Select("SELECT * FROM member WHERE member_id = #{memberId}") // 어노테이션으로 쿼리문 설정 가능
    Member findMemberByMemberId(String memberId);//파라미터 타입, 이름과 반환 타입. 네임스페이스 정보를 알아서 파싱한다
    @Select("select * from member where (#{memberId},#{memberPassword}) = (member_id,member_password)")
    Member findLoginInfoByMemberIdAndMemberPassword(String memberId, String memberPassword);
}
