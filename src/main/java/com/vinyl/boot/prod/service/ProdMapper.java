package com.vinyl.boot.prod.service;

import com.vinyl.boot.command.ProdImgVO;
import com.vinyl.boot.command.ProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface ProdMapper {

    public int prodRegist(ProdVO vo);
    public int prodRegistImg(@Param("prod_name") String prod_name, @Param("list") ArrayList<ProdImgVO> list);
    public ArrayList<ProdVO> prodList(); // 메인페이지 리스트
    public ArrayList<ProdVO> prodNewList (); // 메인페이지 new리스트
    public ProdVO prodDetail(Integer prod_num); // 상품 디테일 페이지


}
