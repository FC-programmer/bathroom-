package com.fuchen.travel.background.service;

import com.fuchen.travel.background.entity.BathroomType;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author rtq
 * @Date 2023/5/7
 * 浴室类型管理
 **/
public interface BathroomTypeService {

    /**
     * 获取预约浴室总数
     * @return 浴室总数
     */
    Integer getScenicCount ();

    /**
     * 分页查询全部浴室
     * @param offset 检索起始行
     * @param limit 简述条数
     * @return 浴室的list集合
     */
    List<BathroomType> getBathroomTypes(Integer offset, Integer limit);
    /**
     * 添加浴室
     * @param
     */
    void addBathRoomType(BathroomType bathroomType, MultipartFile BathroomTypeImg, String filename, String suffix);

    /**
     * 移出浴室
     * @param list
     */
    void removeBathRoomType(List<String> list);
    /**
     * 查询指定名称浴室数量
     * @param keyword
     * @return
     */
    Integer getBathroomTypeCountSearch(String keyword);
    /**
     * 通过指定名称获取景点信息
     * @param keyword
     * @param offset
     * @param limit
     * @return
     */
    List<BathroomType> getBathroomTypeSearch(String keyword, Integer offset, Integer limit);
}
