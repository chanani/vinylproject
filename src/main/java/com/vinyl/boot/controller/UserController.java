package com.vinyl.boot.controller;

import com.vinyl.boot.command.naverLogin.NaverLoginProfile;
import com.vinyl.boot.command.naverLogin.NaverLoginProfileResponse;
import com.vinyl.boot.command.naverLogin.NaverLoginVo;
import com.vinyl.boot.command.UserVO;
import com.vinyl.boot.command.ValidVO;
import com.vinyl.boot.security.config.JWTService;
import com.vinyl.boot.user.service.UserService;
import com.vinyl.boot.util.MailSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    /*@PostMapping("/login")
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
    }*/

    @RequestMapping("/logout")
    public String logout() {
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
                           Model model) {

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

            return "main/joinPage"; // 실패시 원래 화면으로
        }

        if (!vo.getPassword().equals(password2)) {
            model.addAttribute("valid_password2", "비밀번호가 일치하지 않습니다.");
            return "main/joinPage";
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
        return "redirect:";
    }

    @PostMapping("/authNumber")
    public ResponseEntity<String> authNumber(@RequestBody Map<String, Object> map) {
        String username = (String) map.get("username");
        String email = (String) map.get("email");
        MailSend send = new MailSend();
        int number = (int) ((Math.random() * 899999) + 100000);
        send.setAuthNum(number);
        String result = send.welcomeMailSend(email, send.getAuthNum());
        System.out.println(result);
        try {
            return ResponseEntity.ok(String.valueOf(number));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("오류가 발생했습니다.");
        }

    }

    @PostMapping("/checkEmail")
    public ResponseEntity<String> checkEmail(@RequestBody Map<String, Object> map){
        String username = (String) map.get("username");
        String email = (String) map.get("email");


        try {
            int result = userService.checkEmail(username, email);
            return ResponseEntity.ok(String.valueOf(result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("정보 확인 중 오류가 발생하였습니다.");
        }
    }

    @PostMapping("/modifyPassword")
    public ResponseEntity<String> modifyPassword(@RequestBody Map<String, Object> map){
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String pw = passwordEncoder.encode(password);
        try {
            int result = userService.modifyPassword(username, pw);
            if (result == 1){
                return ResponseEntity.ok("정상적으로 비밀번호가 변경되었습니다.");
            } else {
                return ResponseEntity.ok("비밀번호 변경에 실패하였습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("비밀번호 변경 중 오류가 발생하였습니다.");
        }
    }

        @RequestMapping(value = "/kakaoLogin", method = RequestMethod.GET)
        public String kakaoLogin (@RequestParam(value = "code", required = false) String code,
                                  RedirectAttributes ra,
                                  Model model) throws Exception{
            String access_Token = userService.getAccessToken(code);
            HashMap<String, Object> userInfo = userService.getUserInfo(access_Token);

            String result = userService.socialCheckEmail((String) userInfo.get("email"));
            System.out.println("결과 : " + result);
            if (result.equals("true")){
                String username = userService.getUsername((String)userInfo.get("email"));
                String token = JWTService.createToken(username, "user");
                ra.addFlashAttribute("Authorization", "Bearer " + token);
                ra.addFlashAttribute("username", username);
                ra.addFlashAttribute("msg", "반갑습니다.");
                return "redirect:";
            } else {
                model.addAttribute("msg", "가입되어있지 않은 아이디입니다.");
                return "main/joinPage";
            }

        }

        @RequestMapping(value = "/naverLogin", method = RequestMethod.GET)
        public String naverLogin (@RequestParam Map<String, String> resValue,
                                  RedirectAttributes ra,
                                  Model model){

            // code 를 받아오면 code 를 사용하여 access_token를 발급받는다.
            final NaverLoginVo naverLoginVo = userService.requestNaverLoginAcceccToken(resValue, "authorization_code");

            // access_token를 사용하여 사용자의 고유 id값을 가져온다.
            final NaverLoginProfile naverLoginProfile = userService.requestNaverLoginProfile(naverLoginVo);
            String email = naverLoginProfile.getEmail();
            System.out.println("email : " + email);
            String result = userService.socialCheckEmail(email);

            if (result.equals("true")){
                String username = userService.getUsername(email);
                String token = JWTService.createToken(username, "user");
                ra.addFlashAttribute("Authorization", "Bearer " + token);
                ra.addFlashAttribute("username", username);
                ra.addFlashAttribute("msg", "반갑습니다.");
                return "redirect:";
            } else {
                model.addAttribute("msg", "가입되어있지 않은 아이디입니다.");
                return "main/joinPage";
            }
        }
}
