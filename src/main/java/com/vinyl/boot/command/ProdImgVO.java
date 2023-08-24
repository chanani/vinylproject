package com.vinyl.boot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdImgVO {

    private int img_no; // pk
    private String img_name; // 실제 파일명
    private String img_path; // 폴더명
    private String img_uuid; // 난수값
    private LocalDateTime img_regdate;
    private String img_type;
    private String prod_num;

}
