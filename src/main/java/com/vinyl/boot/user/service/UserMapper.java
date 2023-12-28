package com.vinyl.boot.user.service;

import com.vinyl.boot.command.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public int addJoin(UserVO vo);
    public String checkId(String username);
    public UserVO login(String username);


}
