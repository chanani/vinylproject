package com.vinyl.boot.controller;

import com.vinyl.boot.command.UserVO;
import com.vinyl.boot.user.service.UserMapper;
import com.vinyl.boot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @PostMapping("/joinForm")
    public String joinForm(UserVO vo,
                           @RequestParam("email_domain") String email_domain,
                           @RequestParam("address2") String address2,
                           @RequestParam("year") String year,
                           @RequestParam("month") String month,
                           @RequestParam("day") String day,
                            RedirectAttributes ra){

        String email = vo.getUser_email() + "@" + email_domain;
        String birth = year + "/" + month + "/" + day;
        String address = vo.getUser_add() + " " + address2;
        vo.setUser_birth(birth);
        vo.setUser_add(address);
        vo.setUser_email(email);
        int result = userService.addJoin(vo);

        String msg = result == 1 ? "회원가입이 완료되었습니다." : "회원가입에 실패하셨습니다.";
        ra.addFlashAttribute("msg", msg);
        return "redirect:/";
    }

}
