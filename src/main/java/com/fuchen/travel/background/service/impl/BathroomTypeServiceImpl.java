package com.fuchen.travel.background.service.impl;

import com.fuchen.travel.background.entity.BathroomType;
import com.fuchen.travel.background.mapper.BathroomTypeMapper;
import com.fuchen.travel.background.service.BathroomTypeService;
import com.fuchen.travel.background.util.QCloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author rtq
 * @Date 2023/5/7
 **/
@Service
public class BathroomTypeServiceImpl implements BathroomTypeService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BathroomTypeMapper bathroomTypeMapper;


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
    private QCloudUtil qCloudUtil;
    @Override
    public Integer getScenicCount() {

        //从redis中取出浴室总数
        Integer scenicCount = (Integer) redisTemplate.opsForValue().get("scenicCount");
        //如果浴室数量不为空，则返回浴室数量,否则重新进入数据库查询
        if (scenicCount != null) {
            return scenicCount;
        }
        //获取浴室数量
        scenicCount = bathroomTypeMapper.selectScenicCount();
        //将景点数量放入redis中
        redisTemplate.opsForValue().set("bathroomTypeCount", scenicCount);
        return scenicCount;
    }



    /**
     * 分页查询全部浴室
     * @param offset 检索起始行
     * @param limit 简述条数
     * @return
     */
    @Override
    public List<BathroomType> getBathroomTypes(Integer offset, Integer limit) {
        return bathroomTypeMapper.selectBathroomType(offset, limit);
    }

    @Override
    public void addBathRoomType(BathroomType bathroomType, MultipartFile BathroomTypeImg, String filename, String suffix) {
        //上传腾讯云
        qCloudUtil.uploadFile(bucketName, filename, BathroomTypeImg , cosRegion, secretId, secretKey);

        //更新景点图片路径
        String bathroomTypeUrl = qCloudUrl + "/" +  filename ;
        bathroomType.setUrl(bathroomTypeUrl);

        //如果景点id为空说明已经不存在该景点，应该添加当前景点，否则为修改景点
        if (bathroomType.getId() == null) {
            //添加景点信息
            bathroomTypeMapper.insertBathroomType(bathroomType);
        } else {
            //修改景点信息
            bathroomTypeMapper.updateBathroomType(bathroomType);
        }
        //清除redis中景点数量
        redisTemplate.delete("bathroomTypeCount");
    }

    @Override
    public void removeBathRoomType(List<String> list) {
        //修改景点状态
        bathroomTypeMapper.updateBathroomTypeById(list);
        //清除redis中景点数量
        redisTemplate.delete("bathroomTypeCount");
    }

    /**
     * 查询指定名称景点数量
     * @param keyword
     * @return
     */
    @Override
    public Integer getBathroomTypeCountSearch(String keyword) {
        return bathroomTypeMapper.selectCountByKeyword(keyword);
    }

    @Override
    public List<BathroomType> getBathroomTypeSearch(String keyword, Integer offset, Integer limit) {
        return bathroomTypeMapper.selectBathroomTypeByKeyword(keyword, offset, limit);
    }


}
