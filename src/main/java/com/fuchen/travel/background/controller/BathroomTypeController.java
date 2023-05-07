package com.fuchen.travel.background.controller;

import com.fuchen.travel.background.entity.BathroomType;
import com.fuchen.travel.background.entity.Page;
import com.fuchen.travel.background.service.BathroomTypeService;
import com.fuchen.travel.background.service.UserService;
import com.fuchen.travel.background.util.TravelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rtq
 * @Date 2023/5/7
 **/
@Controller
@Slf4j
public class BathroomTypeController {

    @Autowired
    private BathroomTypeService bathroomTypeService;
    @Autowired
    private UserService userService;
    /**
     * 浴室类型管理
     * @param model
     * @param page
     * @return
     */

    @GetMapping("/bathroomType-control")
    public String scenicControl(Model model, Page page, HttpServletRequest request) {
        //获取浴室数量
        Integer bathroomTypeCount = bathroomTypeService.getScenicCount();
        //设置分页数据
        page.setLimit(5);
        page.setPath("/bathroomType-control");
        page.setRows(bathroomTypeCount);
        //获取浴室集合
        List<BathroomType> bathroomTypes = bathroomTypeService.getBathroomTypes(page.getOffset(), page.getLimit());
        //用户存放浴室数据
        List<Map<String, BathroomType>> bathroomTypeList = new ArrayList<>(bathroomTypeCount);
        //遍历景点集合，将其通过map放入list中
        for (int i = 0; i < bathroomTypes.size(); i++) {
            Map<String, BathroomType> map = new HashMap<>();
            map.put("bathroomType", bathroomTypes.get(i));
            bathroomTypeList.add(map);
        }

        model.addAttribute("bathroomTypeList", bathroomTypeList);
        model.addAttribute("loginUser", userService.getLoginUser(request));
        return "/pages/bathroomType";
    }
    /**
     * 添加浴室信息
     * @param BathroomTypeName 浴室名称
     * @param BathroomTypeImg 浴室图片
     * @param model 模板
     * @return
     */
    @PostMapping("/addBathroomType")
    public String addBathroom(String BathroomTypeName, MultipartFile BathroomTypeImg, Model model){

        BathroomType bathroomType = new BathroomType();
        bathroomType.setName(BathroomTypeName);

        //判断文件后缀
        String filename = BathroomTypeImg.getOriginalFilename();
        //获得文件后缀
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("headerImgMsg","图片格式错误！");
            return "/site/setting";
        }
        //添加景点
        bathroomTypeService.addBathRoomType(bathroomType, BathroomTypeImg, filename, suffix);
        return "redirect:/bathroomType-control";
    }

    /**
     * 删除景点信息
     * @param list 需要删除的景点id集合
     * @return
     */
    @ResponseBody
    @PostMapping("/removeBathroomType")
    public String removeScenic(@RequestParam("list[]")List<String> list) {
        //判断集合是否为空
        if (list.size() == 0) {
            TravelUtil.getJsonString(1,"未选择景点！");
        }
        //删除景点信息
        bathroomTypeService.removeBathRoomType(list);
        return TravelUtil.getJsonString(0,"景点删除成功！");
    }

    @GetMapping("/bathroomType/search")
    public String searchUser(String keyword, Model model, Page page,HttpServletRequest request){
        //判断关键字是否为空
        if (keyword.isEmpty()) {
            model.addAttribute("searchMsg", "请输入搜素内容！");
            return "/pages/bathroomType-control";
        }

        //获取用户数量
        Integer bathroomTypeCount = bathroomTypeService.getBathroomTypeCountSearch(keyword);

        //设置分页数据
        page.setLimit(5);
        page.setPath("/bathroomType/search?keyword=" + keyword);
        page.setRows(bathroomTypeCount);
        //分页查询用户集合
        List<BathroomType> bathroomTypes = bathroomTypeService.getBathroomTypeSearch(keyword, page.getOffset(), page.getLimit());
        //创建景点集合存放景点数据
        List<Map<String, BathroomType>> bathroomTypeList = new ArrayList<>(bathroomTypeCount);
        //遍历景点集合，将其通过map放入list中
        for (int i = 0; i < bathroomTypes.size(); i++) {
            Map<String, BathroomType> map = new HashMap<>();
            map.put("bathroomType", bathroomTypes.get(i));
            bathroomTypeList.add(map);
        }
        model.addAttribute("loginUser", userService.getLoginUser(request));
        model.addAttribute("bathroomTypeList", bathroomTypeList);
        return "/pages/bathroomType";
    }

}
