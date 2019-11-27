package com.smallone.o2o.web.frontend;

import com.smallone.o2o.dto.ProductExecution;
import com.smallone.o2o.entity.Product;
import com.smallone.o2o.entity.ProductCategory;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.service.ProductCategoryService;
import com.smallone.o2o.service.ProductService;
import com.smallone.o2o.service.ShopService;
import com.smallone.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商店详情
 *
 * @author smallone
 * @created 2019--11--27--19:15
 */
@Controller
@RequestMapping("/frontend")
public class ShopDetailController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺信息以及该店铺下面的商品类别列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopdetailpageinfo")
    @ResponseBody
    private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取前台传来的shopId
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if (shopId != -1) {
            //获取店铺Id为shopId的店铺信息
            shop = shopService.getByShopId(shopId);
            //获取店铺下面的商品类别列表
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "listproductbyshop")
    @ResponseBody
    private Map<String,Object> listProductByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //页码
        Integer pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        //一页条数
        Integer pageSize =  HttpServletRequestUtil.getInt(request,"pageSize");
        //获取店铺Id
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        //空值判断
        if((pageIndex > -1) && (pageSize > -1) && (shopId > -1)){
            //尝试获取商品类别Id
            Long productCategoryId = HttpServletRequestUtil.getLong(request,"productCategoryId");
            //尝试获取模糊查找的商品名
            String productName = HttpServletRequestUtil.getString(request,"productName");
            //组合查询条件
            Product productCondition = compactProductCondition4Search(shopId,productCategoryId,productName);
            //按照传入的查询条件以及分页信息返回相应商品列表以及总数
            ProductExecution pe = productService.getProductList(productCondition,pageIndex,pageSize);
            modelMap.put("productList",pe.getProductList());
            modelMap.put("count",pe.getCount());
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    private Product compactProductCondition4Search(long shopId,
                                                   long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        productCondition.setEnableStatus(1);
        return productCondition;
    }


}

