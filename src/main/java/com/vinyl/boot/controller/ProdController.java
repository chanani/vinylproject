package com.vinyl.boot.controller;

import com.vinyl.boot.command.ProdImgVO;
import com.vinyl.boot.command.ProdVO;
import com.vinyl.boot.prod.service.ProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
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
    public String prodList(Model model,
                           @RequestParam("search_data") String search_data){
        System.out.println("searchData : " + search_data);
        if (search_data == null || search_data == "") {
            ArrayList<ProdVO> list = prodService.prodList();
            ArrayList<ProdImgVO> imgList = prodService.prodListImg();
            model.addAttribute("list", list);
            model.addAttribute("imgList", imgList);
            return "/prod/prodList";
        } else {
            ArrayList<ProdVO> list = prodService.searchList(search_data);
            ArrayList<ProdImgVO> imgList = prodService.searchListImg(search_data);
            model.addAttribute("list", list);
            model.addAttribute("imgList", imgList);
            for (int i = 0; i < imgList.size(); i++) {
                System.out.println("imgList : " + imgList.get(i).getImg_name());
            }
            return "/prod/prodList";
        }


    }

    @RequestMapping("/prodDetail")
    public String prodDetail(@RequestParam("prod_num") Integer prod_num,
                             Model model){
        ProdVO vo = prodService.prodDetail(prod_num);
        ProdImgVO imgVO = prodService.prodDetailImg(prod_num);
        ArrayList<ProdImgVO> subImgList = prodService.prodDetailSubImg(prod_num);
        model.addAttribute("vo", vo);
        model.addAttribute("imgVO", imgVO);
        model.addAttribute("subImgList", subImgList);

        return "prod/prodDetail";
    }

    @RequestMapping("/prodReg")
    public String prodReg(){
        return "/prod/prodReg";
    }

    @RequestMapping("/registForm")
    public String registForm(ProdVO vo,
                             @RequestParam("file") ArrayList<MultipartFile> file,
                             RedirectAttributes ra,
                             @RequestParam("track_num") Integer[] track_num,
                             @RequestParam("track_name") String[] track_name){
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
        ArrayList<String> list_name = new ArrayList();
        for (int i = 0; i < track_name.length; i++) {
            list_name.add(track_name[i]);
        }
        prodService.trackList_add(list_name);

        return "redirect:/prod/prodList";
    }




}
