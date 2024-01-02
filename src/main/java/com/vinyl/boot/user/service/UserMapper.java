package com.vinyl.boot.user.service;

import com.vinyl.boot.command.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    public int addJoin(UserVO vo);
    public String checkId(String username);
    public UserVO login(String username);
    public int checkEmail(@Param("username") String username, @Param("email") String email);
    public int modifyPassword(@Param("username") String username, @Param("password") String password);
    public String socialCheckEmail(String email);
    public String getUsername(String email);


}
