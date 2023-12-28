package com.vinyl.boot.controller;

import com.vinyl.boot.command.UserVO;
import com.vinyl.boot.command.ValidVO;
import com.vinyl.boot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public static final String SESSION_COOKIE_NAME = "username";
    @PostMapping("/login")
    public String login(UserVO vo,
                        RedirectAttributes ra,
                        HttpServletResponse response){
        if ((vo.getUsername() == null|| vo.getUsername() == "") || (vo.getPassword() == null || vo.getPassword() == "")){
            String msg = "아이디와 비밀번호는 공백일 수 없습니다.";
            ra.addFlashAttribute("msg", msg);
            return "redirect:/";
        }

        UserVO vo2 = userService.login(vo.getUsername());
        System.out.println(vo2.toString());
       return "g";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/";
    }

    @PostMapping("/joinForm")
    public String joinForm(@Valid @ModelAttribute("vo") ValidVO vo, Errors errors,
                           @RequestParam("email_domain") String email_domain,
                           @RequestParam("address2") String address2,
                           @RequestParam("year") String year,
                           @RequestParam("month") String month,
                           @RequestParam("day") String day,
                           @RequestParam("password2") String password2,
                           UserVO userVO,
                           RedirectAttributes ra,
                           Model model){

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);
        model.addAttribute("address2", address2);
        model.addAttribute("email_domain", email_domain);


        if (errors.hasErrors()) { // 에러가 있다면 true, 없다면 false

            // 1. 유효성 검사에 실패한 에거 확인
            List<FieldError> list = errors.getFieldErrors();

            // 2. 반복처리
            for (FieldError err : list) {
            System.out.println(err);
            System.out.println(err.getField()); // 에러가 난 필드명
            System.out.println(err.getDefaultMessage()); // 메시지 출력
            System.out.println(err.isBindingFailure()); // 유효성검사에 의해서 err라면 false, 아니라면 true, 자바 내부에서 err

                if (err.isBindingFailure()) {
                    model.addAttribute("valid_" + err.getField(), "잘못된 값 입력입니다.");
                } else {
                    model.addAttribute("valid_" + err.getField(), err.getDefaultMessage());
                }
            }

            return "/main/joinPage"; // 실패시 원래 화면으로
        }

        if (!vo.getPassword().equals(password2)){
            model.addAttribute("valid_password2", "비밀번호가 일치하지 않습니다.");
            return "/main/joinPage";
        }

        String encryptedPassword = passwordEncoder.encode(vo.getPassword());

        String email = vo.getUser_email() + "@" + email_domain;
        String birth = year + "/" + month + "/" + day;
        String address = vo.getUser_add() + " " + address2;
        userVO.setUsername(vo.getUsername());
        userVO.setPassword(encryptedPassword);
        userVO.setUser_phone(vo.getUser_phone());
        userVO.setUser_birth(birth);
        userVO.setUser_add(address);
        userVO.setUser_email(email);
        int result = userService.addJoin(userVO);

        String msg = result == 1 ? "회원가입이 완료되었습니다." : "회원가입에 실패하셨습니다.";
        ra.addFlashAttribute("msg", msg);
        return "redirect:/";
    }



}
