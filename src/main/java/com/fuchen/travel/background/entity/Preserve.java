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
public class Preserve {
    private Integer id;
    private Integer number;
    private String bathroomtype;
    private String title;
    private String date;
    private String phone;
    private Integer content;
}
