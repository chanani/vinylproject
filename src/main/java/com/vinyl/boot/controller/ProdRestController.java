package com.vinyl.boot.controller;

import com.vinyl.boot.prod.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/prod")
public class ProdRestController {
    @Autowired
    @Qualifier("prodService")
    private ProdService prodService;
    @Autowired
    private HttpSession httpSession;
    public static final String SESSION_COOKIE_NAME = "username";
    @GetMapping("/addCart")
    public String addCart(@RequestParam("num") Integer num,
                          @RequestParam("amount") Integer amount){
        String username = (String) httpSession.getAttribute(SESSION_COOKIE_NAME);
        if (username == null){
            return "로그인 후 이용 가능합니다.";
        }

        int result = prodService.addCart(num, username, amount);
        System.out.println("성공 여부 : " + result);

        if (result == 1){
            return "상품이 장바구니에 등록되었습니다.";
        } else{
            return "상품이 장바구니에 등록되지 않았습니다.";

        }

        //return "redirect:/prod/prodDetail?prod_num=" + num;
    }

}
