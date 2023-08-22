package com.vinyl.boot.prod.service;

import com.vinyl.boot.command.ProdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("prodService")
public class ProdServiceImpl implements ProdService{

    @Autowired
    private ProdMapper prodMapper;

    @Override
    public int prodRegist(ProdVO vo) {
        System.out.println(vo.toString());
        return prodMapper.prodRegist(vo);
    }
}
