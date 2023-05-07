package com.fuchen.travel.background.service.impl;

import com.fuchen.travel.background.entity.Goods;
import com.fuchen.travel.background.entity.GoodsOrder;
import com.fuchen.travel.background.mapper.GoodsMapper;
import com.fuchen.travel.background.service.GoodsService;
import com.fuchen.travel.background.util.QCloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 孑然
 * @Date 2023 05/07 22:30
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    /**
     * 腾讯云存储地区
     */
    @Value("${qcloud.cosRegion}")
    private String cosRegion;

    /**
     * 腾讯云密钥id
     */
    @Value("${qcloud.key.secretId}")
    private String secretId;

    /**
     * 腾讯云密钥key
     */
    @Value("${qcloud.key.secretKey}")
    private String secretKey;

    /**
     * 腾讯云对象存储桶名
     */
    @Value("${qcloud.bucket.scenic.name}")
    private String bucketName;

    /**
     * 腾讯云对象存储访问路径
     */
    @Value("${qcloud.bucket.scenic.url}")
    private String qCloudUrl;

    /**
     * 腾讯云工具类对象
     */
    @Autowired
    private QCloudUtil qCloudUtil;;

    @Resource
    private GoodsMapper goodsMapper;


    @Override
    public Integer getGoodsCount() {
        Integer count = goodsMapper.selectGoodsCount();
        return count;
    }

    @Override
    public List<Goods> getGoods(Integer offset, Integer limit) {
        return goodsMapper.selectGoods(offset, limit);
    }

    @Override
    public void addGoods(Goods good, MultipartFile goodsImg, String filename, String suffix) {
        //上传腾讯云
        qCloudUtil.uploadFile(bucketName, filename, goodsImg , cosRegion, secretId, secretKey);

        //更新景点图片路径
        String cover = qCloudUrl + "/" +  filename ;
        good.setCover(cover);
        goodsMapper.insertGoods(good);
    }

    @Override
    public void removeGoods(List<String> list) {
        //修改景点状态
        goodsMapper.deleteGoodsById(list);
    }

    @Override
    public Integer getGoodsCountSearch(String keyword) {
        Integer count = goodsMapper.selectGoodsCountSearch(keyword);
        return count;
    }

    @Override
    public List<Goods> getScenicSearch(String keyword, Integer offset, Integer limit) {
        return goodsMapper.getScenicSearch(keyword, offset, limit);
    }

    @Override
    public Integer getOrderCount() {
        Integer count = goodsMapper.getOrderCount();
        return count;
    }

    @Override
    public List<GoodsOrder> getGoodsOrder(Integer offset, Integer limit) {
        return goodsMapper.getGoodsOrder(offset, limit);
    }

}
