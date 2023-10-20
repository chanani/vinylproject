package com.vinyl.boot.controller;

import com.vinyl.boot.user.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @GetMapping("/checkId")
    public String checkId(@RequestParam("username") String username){
        boolean result = Boolean.parseBoolean(userService.checkId(username));
        if (result == true) {
            return "사용 가능한 아이디 입니다.";
        } else {
            return "이미 사용 중인 아이디 입니다.";
        }

    }
}
