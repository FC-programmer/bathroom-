package com.fuchen.travel.background.service;

import com.fuchen.travel.background.entity.Goods;
import com.fuchen.travel.background.entity.GoodsOrder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 伏辰
 * @date 2023/1/5
 * 景点-service层
 */
public interface GoodsService {

    /**
     * 获取景点总数
     * @return 景点总数
     */
    Integer getGoodsCount ();

    List<Goods> getGoods(Integer offset, Integer limit);

    void addGoods(Goods goods, MultipartFile goodsImg, String filename, String suffix);

    void removeGoods(List<String> list);

    Integer getGoodsCountSearch(String keyword);

    List<Goods> getScenicSearch(String keyword, Integer offset, Integer limit);

    Integer getOrderCount();

    List<GoodsOrder> getGoodsOrder(Integer offset, Integer limit);
}
