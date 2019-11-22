package com.smallone.o2o.web.shopadmin;

import com.smallone.o2o.dto.Result;
import com.smallone.o2o.entity.ProductCategory;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.enums.ProductCategoryStateEnum;
import com.smallone.o2o.service.ProductCategoryService;
import com.smallone.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author smallone
 * @created 2019--11--22--19:51
 */
@Controller
@RequestMapping(value = "/shopadmin")
public class ProductCategoryManagementController {

    @Autowired
    ProductCategoryService productCategoryService;

/*    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductCategoryList(HttpServletRequest request){
        List<ProductCategory> productCategoryList;
        Map<String,Object> modelMap = new HashMap<>();
        Shop currentShop =  new Shop();
        currentShop.setShopId(1L);
        request.getSession().setAttribute("currentShop",currentShop);
        currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if(currentShop != null && currentShop.getShopId() != null){
            try {
                productCategoryList = productCategoryService.getProductCategoryList(currentShop.getShopId());
                modelMap.put("success", true);
                modelMap.put("productcategorylist", productCategoryList);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","NULL_SHOP");
        }
        return  modelMap;
    }*/

    /**
     * 产品类别列表获取
     * @param request
     * @return
     */
    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList2(HttpServletRequest request){
        // To be removed
        //Shop shop = new Shop();
        //shop.setShopId(1L);
        //request.getSession().setAttribute("currentShop",shop);

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if(currentShop != null && currentShop.getShopId() > 0){
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return  new Result<List<ProductCategory>>(true,list);
        }else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.NULL_SHOP;
            return new Result<List<ProductCategory>>(false,ps.getState(),ps.getStateInfo());
        }
    }
}
