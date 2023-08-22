package com.vinyl.boot.prod.service;

import com.vinyl.boot.command.ProdVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProdMapper {

    public int prodRegist(ProdVO vo);


}
