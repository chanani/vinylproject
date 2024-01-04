package com.vinyl.boot.prod.service;

import com.vinyl.boot.command.ProdImgVO;
import com.vinyl.boot.command.ProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProdMapper {

    public int prodRegist(ProdVO vo);
    public int prodRegistImg(@Param("prod_name") String prod_name, @Param("list") List<ProdImgVO> list);
    public ArrayList<ProdVO> prodList(); // 메인페이지 리스트
    public ArrayList<ProdImgVO> prodListImg(); // 메인페이지 이미지
    public ArrayList<ProdVO> prodNewList (); // 메인페이지 new리스트
    public ProdVO prodDetail(Integer prod_num); // 상품 디테일 페이지
    public ProdImgVO prodDetailImg(Integer prod_num); // 상품 디테일 페이지 이미지
    public ArrayList<ProdImgVO> prodDetailSubImg(Integer prod_num); // 상품 디테일 페이지 서브 이미지

    public int addCart(@Param("prod_num") Integer prod_num, @Param("username") String username, @Param("amount")Integer amount);
    public ArrayList<ProdVO> cartList(String username); // 장바구니 목록
    public ArrayList<ProdImgVO> cartListImg(String username); // 장바구니 페이지 이미지

    public void deleteProd(@Param("username") String username, @Param("prod_num") Integer prod_num);
    public void trackList_add(ArrayList<String> list_name);
    public ArrayList<ProdVO> searchList(String search_data); // 검색 기능
    public ArrayList<ProdImgVO> searchListImg(String search_data); // 검색 이미지
    public String getRole(String username);


}
