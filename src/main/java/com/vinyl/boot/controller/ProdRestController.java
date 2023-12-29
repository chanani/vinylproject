package com.vinyl.boot.controller;

import com.vinyl.boot.prod.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class ProdRestController {
    @Autowired
    @Qualifier("prodService")
    private ProdService prodService;

    public static final String SESSION_COOKIE_NAME = "username";
    @PostMapping("/addCart")
    public String addCart(@RequestBody Map<String, Object> requestBody){

        String num = (String) requestBody.get("num");
        String amount = (String) requestBody.get("amount");
        String username = (String) requestBody.get("username");

        if (username == null){
            return "로그인 후 이용 가능합니다.";
        }

        int result = prodService.addCart(Integer.parseInt(num), username, Integer.parseInt(amount));

        if (result == 1){
            return "상품이 장바구니에 등록되었습니다.";
        } else{
            return "상품이 장바구니에 등록되지 않았습니다.";

        }
    }

    @PostMapping("/delete")
    public String deleteProd(@RequestBody Map<String, Object> requestBody){
        String prod_num = (String) requestBody.get("prod_num");
        String username = (String) requestBody.get("username");
        System.out.println(prod_num);
        System.out.println(username);
        prodService.deleteProd(username, Integer.parseInt(prod_num));

        return "상품이 정상적으로 삭제되었습니다.";
    }

}
