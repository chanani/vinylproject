package com.vinyl.boot.user.service;

import com.vinyl.boot.command.UserVO;

import java.util.HashMap;

public interface UserService {
    public int addJoin(UserVO vo);
    public String checkId(String username);
    public UserVO login(String username);
    public int checkEmail(String username, String email);
    public int modifyPassword(String username, String password);
    public String getAccessToken(String authorize_code);
    public HashMap<String, Object> getUserInfo(String access_Token);
}
