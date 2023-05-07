package com.fuchen.travel.background.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author 伏辰
 * @date 2023/1/5
 * 景点-实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Goods {

    private Integer id;
    private Integer goodsId;
    private String cover;
    private String name;
    private String price;
    private String content;
    private String goodsType;
    private String img1;
    private String img2;
    private String img3;

}
