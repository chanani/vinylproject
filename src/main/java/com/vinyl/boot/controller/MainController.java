package com.vinyl.boot.controller;


import com.vinyl.boot.command.ProdImgVO;
import com.vinyl.boot.command.ProdVO;
import com.vinyl.boot.prod.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    @Qualifier("prodService")
    private ProdService prodService;
    @Autowired
    private HttpSession httpSession;
    public static final String SESSION_COOKIE_NAME = "username";

    @RequestMapping("")
    public String MainPage(Model model){

        ArrayList<ProdVO> list = prodService.prodList(); // 상품 리스트
        ArrayList<ProdVO> list2 = prodService.prodNewList(); // 상품 리스트
        String username = (String) httpSession.getAttribute(SESSION_COOKIE_NAME);
        System.out.println("username : " + username);
        if (username != null){
            model.addAttribute("session_username", username);
        }

        model.addAttribute("list", list);
        model.addAttribute("list2", list2);

        return "/main/mainpage";
    }

    @RequestMapping("/cartPage")
    public String cartPage(/*@RequestParam("username") String username,*/
                           Model model,
                           RedirectAttributes ra){

        String username = (String) httpSession.getAttribute(SESSION_COOKIE_NAME);
        if (username == null){
            String msg = "로그인을 해주세요.";
            ra.addFlashAttribute("msg", msg);
            return "redirect:/";
        }

        ArrayList<ProdVO> list = prodService.cartList(username);
        int price_sum = 0;
        for (int i = 0; i < list.size(); i++) {
            price_sum += Integer.parseInt(list.get(i).getProd_price());
        }
        model.addAttribute("list", list);
        System.out.println(list.toString());
        model.addAttribute("price_sum", price_sum);

        return "/main/cartPage";
    }

    @RequestMapping("/joinpage")
    public String joinpage(){
        return "/main/joinPage";
    }






}
