package com.vinyl.boot.controller;

import com.vinyl.boot.prod.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prod")
public class ProdRestController {
    @Autowired
    @Qualifier("prodService")
    private ProdService prodService;

    @GetMapping("/addCart")
    public String addCart(@RequestParam("num") Integer num,
                          @RequestParam("amount") Integer amount){
        System.out.println("addCart num : " + num);
        System.out.println("addCart amount : " + amount);
        int result = prodService.addCart(num, amount);
        System.out.println("성공 여부 : " + result);

        if (result == 1){
            return "상품이 장바구니에 등록되었습니다.";
        } else{
            return "상품이 장바구니에 등록되지 않았습니다.";

        }

        //return "redirect:/prod/prodDetail?prod_num=" + num;
    }

}
