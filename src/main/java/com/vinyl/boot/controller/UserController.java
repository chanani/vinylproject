package com.vinyl.boot.controller;

import com.vinyl.boot.command.UserVO;
import com.vinyl.boot.user.service.UserMapper;
import com.vinyl.boot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @PostMapping("/joinForm")
    public String joinForm(@Valid @ModelAttribute("vo") UserVO vo, Errors errors,
                           @RequestParam("email_domain") String email_domain,
                           @RequestParam("address2") String address2,
                           @RequestParam("year") String year,
                           @RequestParam("month") String month,
                           @RequestParam("day") String day,
                            RedirectAttributes ra,
                           Model model){
        System.out.println("에러 여부 : " + errors.hasErrors());
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
