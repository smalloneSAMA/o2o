package com.smallone.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallone.o2o.dto.ProductExecution;
import com.smallone.o2o.entity.Product;
import com.smallone.o2o.entity.ProductCategory;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.enums.OperationStatusEnum;
import com.smallone.o2o.enums.ProductStateEnum;
import com.smallone.o2o.exceptions.ProductOperationException;
import com.smallone.o2o.service.ProductCategoryService;
import com.smallone.o2o.service.ProductService;
import com.smallone.o2o.util.CodeUtil;
import com.smallone.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理
 *
 * @author smallone
 * @created 2019--11--24--16:57
 */
@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManagementController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductCategoryService productCategoryService;
    //支持上传商品详情图的最大数量
    private static final int IMAGE_MAX_COUNT = 6;

    @RequestMapping(value = "/getproductlistbyshop" , method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getProductListByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 获取页码和每页数量
        //int pageIndex = 1;
        //int pageSize =999;
        Integer pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        Integer pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        // 获取当前session中的店铺信息
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        // 空值判断
        if (pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
            // 设值查询条件
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            // 查询商品列表和总数
            ProductExecution productExecution = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", productExecution.getProductList());
            modelMap.put("count", productExecution.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "缺少分页参数");
        }
        return modelMap;

    }


    /**
     * 修改店铺信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // Step1:检验验证码，在点击商品下架时不需要输入验证码，编辑时需要输入验证码
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        // 状态改变且验证码错误时，返回
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", OperationStatusEnum.VERIFYCODE_ERROR.getStateInfo());
            return modelMap;
        }

        // Step2：参数第一个参数：商品信息
        String productStr = null;
        Product product = null;
        // 使用jackson-databind-->https://github.com/FasterXML/jackson-databind将json转换为pojo
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse（创建一次，可重用）
        try {
            productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        // Step3: 商品缩略图 和 商品详情图 构造调用service层的第二个参数和第三个参数
        MultipartFile productImg = null; // 图片缩略图
        List<MultipartFile> productDetailImgList = new ArrayList<>();// 商品详情图
        try {
            // 创建一个通用的多部分解析器
            MultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                productImg = handleImage(request, productDetailImgList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        // Step4：调用service层店铺修改方法
        if (product != null) {
            try {
                // 从session中获取shop信息，不依赖前端的传递更加安全
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                // 调用modifyProduct
                ProductExecution pe = productService.modifyProduct(product, productImg, productDetailImgList);
                if (pe.getState() == OperationStatusEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", ProductStateEnum.PRODUCT_EMPTY.getStateInfo());
        }
        return modelMap;
    }

    /**
     * @param productId
     * @return
     */
    @RequestMapping(value = "/getproductbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductById(@RequestParam long productId){
        Map<String,Object > modelMap = new HashMap<>();
        //非空判断
        if(productId > -1){
            //获取商品信息
            Product product = productService.getProductById(productId);
            //获取该店铺下的商品类别列表
            List<ProductCategory> productCategoryList = productCategoryService.
                    getProductCategoryList(product.getShop().getShopId());
            modelMap.put("success",true);
            modelMap.put("product",product);
            modelMap.put("productCategoryList",productCategoryList);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty productId");
        }
        return modelMap;
    }

    /**
     * 新增产品
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // Step1:校验验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", OperationStatusEnum.VERIFYCODE_ERROR.getStateInfo());
            return modelMap;
        }

        // Step2: 使用FastJson提供的api,实例化Product 构造调用service层的第一个参数
        String productStr = null;
        Product product = null;
        // 使用jackson-databind-->https://github.com/FasterXML/jackson-databind将json转换为pojo
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse（创建一次，可重用）
        try {
            productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        // Step3: 商品缩略图 和 商品详情图 构造调用service层的第二个参数和第三个参数
        MultipartFile productImg = null; // 图片缩略图
        List<MultipartFile> productDetailImgList = new ArrayList<>();// 商品详情图
        try {
            // 创建一个通用的多部分解析器
            MultipartResolver multipartResolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                productImg = handleImage(request, productDetailImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", OperationStatusEnum.PIC_EMPTY.getStateInfo());
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        // Step4 调用Service层
        if (product != null && productImg != null && productDetailImgList.size() > 0) {
            try {
                // 从session中获取shop信息，不依赖前端的传递更加安全
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                // 调用addProduct
                ProductExecution pe = productService.addProduct(product, productImg, productDetailImgList);
                if (pe.getState() == OperationStatusEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", ProductStateEnum.PRODUCT_EMPTY.getStateInfo());
        }
        return modelMap;
    }

    /**
     * 处理图片私有方法
     *
     * @param request
     * @param productDetailImgList
     * @return
     */
    private MultipartFile handleImage(HttpServletRequest request, List<MultipartFile> productDetailImgList) {
        MultipartHttpServletRequest multipartRequest;
        MultipartFile productImg;
        // 与前端约定使用productImg，得到商品缩略图
        multipartRequest = (MultipartHttpServletRequest) request;
        productImg = (MultipartFile) multipartRequest.getFile("productImg");

        // 得到商品详情的列表，和前端约定使用productDetailImg + i 传递
        for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
            MultipartFile productDetailImg = (MultipartFile) multipartRequest.getFile("productDetailImg" + i);
            if (productDetailImg != null) {
                productDetailImgList.add(productDetailImg);
            } else {
                // 如果从请求中获取的到file为空，终止循环
                break;
            }
        }
        return productImg;
    }

    /**
     * 组合商品查询条件
     *
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        // 查询类别
        if (productCategoryId != -1) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        // 查询商品名
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }
}
