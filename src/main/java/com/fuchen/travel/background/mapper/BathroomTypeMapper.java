package com.fuchen.travel.background.mapper;

import com.fuchen.travel.background.entity.BathroomType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rtq
 * @Date 2023/5/7
 **/
@Mapper
public interface BathroomTypeMapper {

    /**
     * 查询浴室总数
     * @return 返回景点总数
     */
    Integer selectScenicCount ();
    /**
     * 分页查询全部浴室
     * @param offset 检索起始行
     * @param limit 简述条数
     * @return 返回list集合
     */

    List<BathroomType> selectBathroomType(@Param("offset") Integer offset, @Param("limit") Integer limit);
    /**
     * 插入浴室信息
     * @param bathroomType
     */
    void insertBathroomType(@Param("bathroomType") BathroomType bathroomType);

    /**
     * 修改浴室信息
     * @param bathroomType
     */
    void updateBathroomType(@Param("bathroomType") BathroomType bathroomType);
    /**
     * 修改景点的状态，将其设置为删除
     * @param list
     */
    void updateBathroomTypeById(@Param("ids") List<String> list);
    /**
     * 查询指定关键字的景点数量
     * @param keyword
     * @return
     */
    Integer selectCountByKeyword(@Param("keyword") String keyword);
    /**
     * 查询指定关键字景点
     * @param keyword
     * @param offset
     * @param limit
     * @return
     */
    List<BathroomType> selectBathroomTypeByKeyword(@Param("keyword") String keyword,
                                       @Param("offset") Integer offset,
                                       @Param("limit") Integer limit);

}
