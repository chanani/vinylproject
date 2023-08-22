package com.vinyl.boot.controller;

import com.vinyl.boot.command.ProdVO;
import com.vinyl.boot.prod.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProdController {

    @Autowired
    @Qualifier("prodService")
    private ProdService prodService;

    @Value("${project.upload.path")
    private String uploadPath;

    @RequestMapping("/prodList")
    public String prodList(){

        return "/prod/prodList";
    }

    @RequestMapping("/prodDetail")
    public String prodDetail(){
        return "prod/prodDetail";
    }

    @RequestMapping("/prodReg")
    public String prodReg(){
        return "/prod/prodReg";
    }

    @RequestMapping("/registForm")
    public String registForm(ProdVO vo,
                             @RequestParam("file") List<MultipartFile> file,
                             RedirectAttributes ra){

        int result = prodService.prodRegist(vo);

        String msg = result == 1 ? "상품이 등록되었습니다." : "상품 등록에 실패하였습니다.";
        ra.addFlashAttribute("msg", msg);


        return "redirect:/prod/prodList";
    }
}
