package com.fuchen.travel.background.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author 孑然
 * @Date 2023 05/08 00:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoodsOrder {

    private  int id ;

    private String phone;

    private String data ;

    private String everyone;

    private int orderId;

    private String price ;

    private String content;
}
