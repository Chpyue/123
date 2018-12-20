package com.example.demo.mapper;

import com.example.demo.model.UserAuthority;
import com.example.demo.model.UserAuthorityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAuthorityMapper {
    int countByExample(UserAuthorityExample example);

    int deleteByExample(UserAuthorityExample example);

    int insert(UserAuthority record);

    int insertSelective(UserAuthority record);

    List<UserAuthority> selectByExample(UserAuthorityExample example);

    int updateByExampleSelective(@Param("record") UserAuthority record, @Param("example") UserAuthorityExample example);

    int updateByExample(@Param("record") UserAuthority record, @Param("example") UserAuthorityExample example);
}