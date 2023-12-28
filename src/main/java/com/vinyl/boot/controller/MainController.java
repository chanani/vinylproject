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
        ArrayList<ProdImgVO> imgList = prodService.prodListImg();
        ArrayList<ProdImgVO> imgList2 = prodService.prodListImg();

        String username = (String) httpSession.getAttribute(SESSION_COOKIE_NAME);
        System.out.println("username : " + username);
        if (username != null){
            model.addAttribute("session_username", username);
        }

        model.addAttribute("list", list);
        model.addAttribute("list2", list2);
        model.addAttribute("imgList", imgList);
        model.addAttribute("imgList2", imgList2);

        return "/main/mainpage";
    }

    @RequestMapping("/cartPage")
    public String cartPage(@RequestParam("username") String username,
                           Model model,
                           RedirectAttributes ra){

        if (username == null){
            String msg = "로그인을 해주세요.";
            ra.addFlashAttribute("msg", msg);
            return "redirect:/";
        }
        ArrayList<ProdVO> list = prodService.cartList(username);
        ArrayList<ProdImgVO> imgList = prodService.cartListImg(username);

        int price_sum = 0;
        int cart_count = 0;
        for (int i = 0; i < list.size(); i++) {
            price_sum += Integer.parseInt(list.get(i).getProd_price()) * list.get(i).getProd_count();
            cart_count += list.get(i).getProd_count();
        }
        model.addAttribute("list", list);
        model.addAttribute("imgList", imgList);
        model.addAttribute("price_sum", price_sum);
        model.addAttribute("cart_count", cart_count);
        return "/main/cartPage";
    }

    @RequestMapping("/joinpage")
    public String joinpage(){
        return "/main/joinPage";
    }






}
