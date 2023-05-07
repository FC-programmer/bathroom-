package com.fuchen.travel.background.mapper;

import com.fuchen.travel.background.entity.Goods;
import com.fuchen.travel.background.entity.GoodsOrder;
import com.fuchen.travel.background.entity.Scenic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 伏辰
 * @date 2023/1/5
 * 景点-mapper层
 */
@Mapper
public interface GoodsMapper {

    Integer selectGoodsCount ();

    List<Goods> selectGoods(Integer offset, Integer limit);

    void insertGoods(Goods good);

    void deleteGoodsById(@Param("ids") List<String> list);

    Integer selectGoodsCountSearch(String keyword);

    List<Goods> getScenicSearch(String keyword, Integer offset, Integer limit);

    Integer getOrderCount();

    List<GoodsOrder> getGoodsOrder(Integer offset, Integer limit);

}
