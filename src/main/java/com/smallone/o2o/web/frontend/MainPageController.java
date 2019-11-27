package com.smallone.o2o.web.frontend;

import com.smallone.o2o.dao.HeadLineDao;
import com.smallone.o2o.entity.HeadLine;
import com.smallone.o2o.entity.ShopCategory;
import com.smallone.o2o.service.HeadLineService;
import com.smallone.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页请求响应Controller
 *
 * @author smallone
 * @created 2019--11--26--19:25
 */
@Controller
@RequestMapping(value = "/frontend")
public class MainPageController {

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private HeadLineService headLineService;

    @RequestMapping(value = "/listmainpageinfo")
    @ResponseBody
    private Map<String , Object> listMainPageInfo(){
        Map<String,Object> modelMap = new HashMap<>();
        try {
            //获取一级店铺类别列表  parentId为空的shopCategory
            List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(null);
            modelMap.put("shopCategoryList",shopCategoryList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        try {
            //获取状态为可用的头条列表
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            List<HeadLine> headLineList = headLineService.getHeadLineList(headLineCondition);
            modelMap.put("headLineList",headLineList);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return  modelMap;
    }
}
