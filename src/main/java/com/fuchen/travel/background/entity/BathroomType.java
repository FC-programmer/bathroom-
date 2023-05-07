package com.fuchen.travel.background.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author rtq
 * @Date 2023/5/7
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BathroomType {
    /**
     * 浴室id
     */
    private Integer id;
    private String name;
    private String url;
    private Integer content;

}
