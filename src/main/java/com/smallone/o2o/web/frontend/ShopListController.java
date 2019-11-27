package com.smallone.o2o.web.frontend;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.smallone.o2o.dto.ShopExecution;
import com.smallone.o2o.entity.Area;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.entity.ShopCategory;
import com.smallone.o2o.enums.OperationStatusEnum;
import com.smallone.o2o.service.AreaService;
import com.smallone.o2o.service.ShopCategoryService;
import com.smallone.o2o.service.ShopService;
import com.smallone.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回首页商店列表
 *
 * @author smallone
 * @created 2019--11--27--15:14
 */
@Controller
@RequestMapping("/frontend")
public class ShopListController {

    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    /**
     * 返回商品列表页里的ShopCategory列表（二级或者一级），以及区域信息列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShopsPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //试着从前端请求中获取parentId
        Long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        if (parentId != -1) {
            //如果parentId存在，则取出该一级ShopCategory下的二级ShopCategory列表
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            try {
                //如果parentId不存在，则取出所有一级ShopCategory(用户在首页选择的是全部商店列表)
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        try {
            //获取区域列表信息
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }

    /**
     * 获取指定查询条件下的店铺列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 获取页码
        Integer pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        // 获取每页显示条数
        Integer pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        // 页码非空判断
        if (pageIndex != -1 && pageSize != -1) {
            // 获取一级类别Id
            Long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            // 获取二级类别Id
            Long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            // 获取模糊查询的店铺名称
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            // 获取区域id
            Integer areaId = HttpServletRequestUtil.getInt(request, "areaId");
            // 获取组合后的查询条件
            Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
            // 根据查询条件和分页信息获取店铺列表
            ShopExecution shopExecution = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("success", true);
            modelMap.put("shopList", shopExecution.getShopList());
            modelMap.put("count", shopExecution.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", OperationStatusEnum.PAGIN_EMPTY.getStateInfo());
        }

        return modelMap;
    }

    /**
     * 根据查询条件组合
     *
     * @param parentId       一级类别Id
     * @param shopCategoryId 二级类别Id
     * @param areaId         区域id
     * @param shopName       模糊查询的店铺名称
     * @return
     */
    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        // 查询某个一级shopCategory下的所有二级shopCateg的店铺列表
        if (parentId != -1) {
            ShopCategory childShopCategory = new ShopCategory();
            ShopCategory parentShopCategory = new ShopCategory();
            parentShopCategory.setShopCategoryId(parentId);
            childShopCategory.setParent(parentShopCategory);
            shopCondition.setShopCategory(childShopCategory);
        }
        // 查询某个二级shopCategory下的店铺列表
        if (shopCategoryId != -1) {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        // 查询某个区域下的店铺列表
        if (areaId != -1) {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        // 模糊查询店铺名称的列表
        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}
