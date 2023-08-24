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

import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    @Qualifier("prodService")
    private ProdService prodService;

    @RequestMapping("")
    public String MainPage(Model model){

        ArrayList<ProdVO> list = prodService.prodList();
        ArrayList<Integer> prod_id = new ArrayList<>();

        // ArrayList<ProdImgVO> list2 = prodService.prodListImg(prod_id);

        for (int i = 0; i < list.size(); i++) prod_id.add(list.get(i).getProd_num());


        model.addAttribute("list", list);
        // model.addAttribute("list2", list2);

        return "/main/mainpage";
    }






}
