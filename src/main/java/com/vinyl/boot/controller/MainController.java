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


        model.addAttribute("list", list);

        return "/main/mainpage";
    }






}
