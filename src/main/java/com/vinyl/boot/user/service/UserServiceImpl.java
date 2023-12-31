package com.vinyl.boot.user.service;

import com.vinyl.boot.command.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int addJoin(UserVO vo) {
        return userMapper.addJoin(vo);
    }

    @Override
    public String checkId(String username) {
        return userMapper.checkId(username);
    }

    @Override
    public UserVO login(String username) {
        return userMapper.login(username);
    }

    @Override
    public int checkEmail(String username, String email) {
        return userMapper.checkEmail(username, email);
    }

    @Override
    public int modifyPassword(String username, String password) {
        return userMapper.modifyPassword(username, password);
    }
}
