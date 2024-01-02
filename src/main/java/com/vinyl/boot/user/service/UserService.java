package com.vinyl.boot.user.service;

import com.vinyl.boot.command.naverLogin.NaverLoginProfile;
import com.vinyl.boot.command.naverLogin.NaverLoginVo;
import com.vinyl.boot.command.UserVO;

import java.util.HashMap;
import java.util.Map;

public interface UserService {
    public int addJoin(UserVO vo);
    public String checkId(String username);
    public UserVO login(String username);
    public int checkEmail(String username, String email);
    public int modifyPassword(String username, String password);
    public String getAccessToken(String authorize_code);
    public HashMap<String, Object> getUserInfo(String access_Token);
    public String socialCheckEmail(String email);
    public String getUsername(String email);
    public NaverLoginVo requestNaverLoginAcceccToken(Map<String, String> resValue, String grant_type);
    public NaverLoginProfile requestNaverLoginProfile(NaverLoginVo naverLoginVo);
}
