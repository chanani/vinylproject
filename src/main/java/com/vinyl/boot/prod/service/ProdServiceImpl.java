package com.vinyl.boot.prod.service;

import com.vinyl.boot.command.ProdImgVO;
import com.vinyl.boot.command.ProdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

@Service("prodService")
public class ProdServiceImpl implements ProdService{

    @Autowired
    private ProdMapper prodMapper;

    @Value("${project.upload.path}")
    private String uploadPath;

    // 폴더 생성함수
    private String makeFolder() {
        String path = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        File file = new File(uploadPath + "/" + path);

        if (file.exists() == false) { // 존재하면 true, 존재하지 않으면 false
            file.mkdir();
        }
        return path; // 폴더명 반환
    }

    @Override
    public int prodRegist(ProdVO vo) {
        System.out.println(vo.toString());
        return prodMapper.prodRegist(vo);
    }

    @Override
    public int prodRegistImg(String prod_name, ArrayList<MultipartFile> file) {
        //파일 이름 받기

        ArrayList<ProdImgVO> list = new ArrayList<>();

        int index = 0;
        for(MultipartFile file1 : file){
            String originName = file1.getOriginalFilename();
            //브라우저 별로 파일의 경로가 다를 수 있으므로 /기준으로 파일명만 잘라서 다시 저장
            String filename = originName.substring(originName.lastIndexOf("/") + 1);
            //파일사이즈
            long size = file1.getSize();
            //동일한 파일 재업로드시 기존파일 덮어버리기 때문에, 난수 이름으로 파일명을 바꿔서 올림
            String uuid = UUID.randomUUID().toString();
            //날짜별로 폴더생성
            String filepath = makeFolder();
            //세이브할 경로
            String savepath = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;

            try {
                File saveFile = new File(savepath);
                file1.transferTo(saveFile);
            } catch (IOException e) {
                System.out.println("파일업로드 중 error 발생");
                e.printStackTrace();
                return 0;
            }

            list.add(ProdImgVO.builder()
                    .img_name(filename)
                    .img_path(filepath)
                    .img_uuid(uuid)
                    .img_type(index == 0 ? "대표이미지" : "상세이미지")
                    .prod_num(prod_name)
                    .build());

            index++;
        }

        int result = prodMapper.prodRegistImg(prod_name, list);

        //productUpload 테이블에 파일 경로 insert
        //prod_id는 insert 전에


// prod_name, prod_no 대신 prod_del_no로 변경 -> 테이블도 변경
        return 0;
    }

    @Override
    public ArrayList<ProdVO> prodList() {
        return prodMapper.prodList();
    }

    @Override
    public ArrayList<ProdVO> prodNewList() {
        return prodMapper.prodNewList();
    }

    @Override
    public ProdVO prodDetail(Integer prod_num) {
        return prodMapper.prodDetail(prod_num);
    }

    @Override
    public int addCart(Integer prod_num, Integer amount) {
        return prodMapper.addCart(prod_num, amount);
    }
}
