package com.vinyl.boot.prod.service;

import com.vinyl.boot.command.ProdImgVO;
import com.vinyl.boot.command.ProdVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface ProdService {

    public int prodRegist(ProdVO vo);
    public int prodRegistImg(String prod_num, ArrayList<MultipartFile> file);
    public ArrayList<ProdVO> prodList ();
    public ArrayList<ProdVO> prodNewList ();
    public ProdVO prodDetail(Integer prod_num);
    public int addCart(Integer prod_num, String username, Integer amount);
    public ArrayList<ProdVO> cartList(String username);
    public void deleteProd(String username, Integer prod_num);
    public void trackList_add(ArrayList<String> list_name);
}
