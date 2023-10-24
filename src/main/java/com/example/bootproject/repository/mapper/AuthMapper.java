package com.example.bootproject.repository.mapper;

import com.example.bootproject.entity.Member;
import org.apache.ibatis.annotations.Select;

public interface AuthMapper {
    @Select("SELECT * FROM auth WHERE member_id = #{memberId} and ip = #{ip}" )
    Member authCheck(String memberId, String ip);
}
