package com.vinyl.boot.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdVO {

    private String prod_name;
    private String prod_singer;
    private String prod_price;
    private String prod_content;
    private Integer prod_stock;
    private Integer prod_hits;
    private String prod_category;



}
