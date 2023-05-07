package com.fuchen.travel.background.controller;

import com.fuchen.travel.background.entity.Goods;
import com.fuchen.travel.background.entity.GoodsOrder;
import com.fuchen.travel.background.entity.Page;
import com.fuchen.travel.background.entity.Scenic;
import com.fuchen.travel.background.service.GoodsService;
import com.fuchen.travel.background.service.ScenicService;
import com.fuchen.travel.background.service.UserService;
import com.fuchen.travel.background.util.TravelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 孑然
 * @Date 2023 05/07 22:18
 */
@Controller
@Slf4j
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private UserService userService;

    @Value("${scenic.path.image}")
    private String goodsImage;

    @GetMapping("/goods")
    public String goodsControl(Model model, Page page, HttpServletRequest request) {

        Integer goodsCount = goodsService.getGoodsCount();
        //设置分页数据
        page.setLimit(5);
        page.setPath("/goods");
        page.setRows(goodsCount);

        List<Goods> goods = goodsService.getGoods(page.getOffset(), page.getLimit());
        //用户存放景点数据
        List<Map<String, Goods>> goodsList = new ArrayList<>(goodsCount);
        //遍历景点集合，将其通过map放入list中
        for (int i = 0; i < goods.size(); i++) {
            Map<String, Goods> map = new HashMap<>();
            map.put("good", goods.get(i));
            goodsList.add(map);
        }
        model.addAttribute("loginUser", userService.getLoginUser(request));
        model.addAttribute("goodsList", goodsList);

        return "/pages/goods";
    }

    @PostMapping("/addGoods")
    public String addScenic(String goodName,String goodPrice, Integer goodType, MultipartFile goodImg, Model model){

        Goods goods = new Goods();
        goods.setName(goodName);
        goods.setGoodsId(goodType);
        goods.setPrice(goodPrice);

        //判断文件后缀
        String filename = goodImg.getOriginalFilename();
        //获得文件后缀
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("headerImgMsg","图片格式错误！");
            return "/site/setting";
        }
        //添加景点
        goodsService.addGoods(goods, goodImg, filename, suffix);

        return "redirect:/goods";
    }


    @ResponseBody
    @PostMapping("/removeGoods")
    public String removeScenic(@RequestParam("list[]")List<String> list) {
        //判断集合是否为空
        if (list.size() == 0) {
            TravelUtil.getJsonString(1,"未选择商品！");
        }
        //删除景点信息
        goodsService.removeGoods(list);
        return TravelUtil.getJsonString(0,"景点删除成功！");
    }

    @GetMapping("/goods/search")
    public String searchUser(String keyword, Model model, Page page, HttpServletRequest request){
        //判断关键字是否为空
        if (keyword.isEmpty()) {
            model.addAttribute("searchMsg", "请输入搜素内容！");
            return "/pages/scenic-control";
        }

        //获取用户数量
        Integer goodsCount = goodsService.getGoodsCountSearch(keyword);

        //设置分页数据
        page.setLimit(5);
        page.setPath("/goods/search?keyword=" + keyword);
        page.setRows(goodsCount);
        //分页查询用户集合
        List<Goods> good = goodsService.getScenicSearch(keyword, page.getOffset(), page.getLimit());
        //创建景点集合存放景点数据
        List<Map<String, Goods>> goodsList = new ArrayList<>(goodsCount);
        //遍历景点集合，将其通过map放入list中
        for (int i = 0; i < good.size(); i++) {
            Map<String, Goods> map = new HashMap<>();
            map.put("good", good.get(i));
            goodsList.add(map);
        }

        model.addAttribute("loginUser", userService.getLoginUser(request));

        model.addAttribute("goodsList", goodsList);

        return "/pages/goods";
    }

    @GetMapping("/goods/order")
    public String searchUser(Model model, Page page, HttpServletRequest request){
        Integer orderCount = goodsService.getOrderCount();
        //设置分页数据
        page.setLimit(5);
        page.setPath("/goods/order");
        page.setRows(orderCount);

        List<GoodsOrder> goodsOrder = goodsService.getGoodsOrder(page.getOffset(), page.getLimit());
        //用户存放景点数据
        List<Map<String, GoodsOrder>> goodsOrderList = new ArrayList<>(orderCount);
        //遍历景点集合，将其通过map放入list中
        for (int i = 0; i < goodsOrder.size(); i++) {
            Map<String, GoodsOrder> map = new HashMap<>();
            map.put("goodOrder", goodsOrder.get(i));
            goodsOrderList.add(map);
        }
        model.addAttribute("loginUser", userService.getLoginUser(request));
        model.addAttribute("goodsOrderList", goodsOrderList);

        return "/pages/goodsOrder";
    }
}
