package com.vinyl.boot.controller;

import com.vinyl.boot.command.ProdVO;
import com.vinyl.boot.prod.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
    public String prodList(Model model){
        ArrayList<ProdVO> list = prodService.prodList();
        model.addAttribute("list", list);

        return "/prod/prodList";
    }

    @RequestMapping("/prodDetail")
    public String prodDetail(@RequestParam("prod_num") Integer prod_num,
                             Model model){
        ProdVO vo = prodService.prodDetail(prod_num);
        model.addAttribute("vo", vo);
        return "prod/prodDetail";
    }

    @RequestMapping("/prodReg")
    public String prodReg(){
        return "/prod/prodReg";
    }

    @RequestMapping("/registForm")
    public String registForm(ProdVO vo,
                             @RequestParam("file") ArrayList<MultipartFile> file,
                             RedirectAttributes ra){
        for (int i = 0; i < file.size(); i++) {
            if (file.get(i).getOriginalFilename() == ""){
                file.remove(i);
                i--;
            }
        }

        if (file.get(0).getContentType().contains("image") == false) {
            ra.addFlashAttribute("msg", "jpg, png, jpeg 형식의 이미지 파일만 등록이 가능합니다.");
            return "redirect:/prod/prodList"; //이미지가 아니라면 list목록으로
        }


        int result = prodService.prodRegist(vo);
        int result1 = prodService.prodRegistImg(vo.getProd_name(), file);
        String msg = result == 1 ? "상품이 등록되었습니다." : "상품 등록에 실패하였습니다.";
        ra.addFlashAttribute("msg", msg);


        return "redirect:/prod/prodList";
    }
}
