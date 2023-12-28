package com.vinyl.boot.controller;

import com.vinyl.boot.prod.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class ProdRestController {
    @Autowired
    @Qualifier("prodService")
    private ProdService prodService;

    public static final String SESSION_COOKIE_NAME = "username";
    @PostMapping("/addCart")
    public String addCart(@RequestParam Integer num,
                          @RequestParam Integer amount,
                          @RequestParam String username){

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
    }

    @PostMapping("/delete")
    public String deleteProd(@RequestParam("prod_num") int prod_num,
                             @RequestParam String username){

        prodService.deleteProd(username, prod_num);

        return "상품이 정상적으로 삭제되었습니다.";
    }

}
