package com.smallone.o2o.web.shopadmin;

import com.smallone.o2o.dto.ProductCategoryExecution;
import com.smallone.o2o.dto.Result;
import com.smallone.o2o.entity.ProductCategory;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.enums.ProductCategoryStateEnum;
import com.smallone.o2o.service.ProductCategoryService;
import com.smallone.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request){
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

    @RequestMapping(value = "/addproductcategories",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProductCategories(@RequestBody List<ProductCategory> productCategoryList,
                                                    HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for(ProductCategory pc : productCategoryList){
            pc.setShopId(currentShop.getShopId());
        }
        if(productCategoryList != null && productCategoryList.size()>0){
            try {
                ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
                if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请至少输入一个商品类别");
        }
        return  modelMap;
    }

    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
        Map<String ,Object> modelMap = new HashMap<>();
        if(productCategoryId != null && productCategoryId>0){
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg","请至少选择一个商品类别");
        }
        return modelMap;
    }

}
