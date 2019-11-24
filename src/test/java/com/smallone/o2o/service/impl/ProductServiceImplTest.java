package com.smallone.o2o.service.impl;

import com.smallone.o2o.BaseTest;
import com.smallone.o2o.dto.ProductExecution;
import com.smallone.o2o.entity.Product;
import com.smallone.o2o.entity.ProductCategory;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.enums.EnableStatusEnum;
import com.smallone.o2o.service.ProductService;
import com.smallone.o2o.util.ImageUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author smallone
 * @created 2019--11--24--15:31
 */
public class ProductServiceImplTest extends BaseTest {

    @Autowired
    ProductService productService;

    @Test
    public void AaddProduct() throws IOException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        product.setShop(shop);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品1");
        product.setProductDescription("测试商品1描述");
        product.setPriority(11);
        product.setEnableStatus(EnableStatusEnum.AVAILABLE.getState());
        product.setLastEditTime(new Date());
        product.setCreateTime(new Date());
        String filePath0 = "E:\\学习\\java\\项目学习\\o2o\\src\\main\\resources\\watermark.jpg";
        List<MultipartFile> productImgList = new ArrayList<>();
        String filePath1 = "E:\\学习\\java\\项目学习\\o2o\\src\\main\\resources\\image\\product\\Video_Girl_03_001.png";
        MultipartFile productImg1 = ImageUtil.path2MultipartFile(filePath1);
        productImgList.add(productImg1);
        String filePath2 = "E:\\学习\\java\\项目学习\\o2o\\src\\main\\resources\\image\\product\\Video_Girl_03_002.png";
        MultipartFile productImg2 = ImageUtil.path2MultipartFile(filePath2);
        productImgList.add(productImg2);
        ProductExecution se = productService.addProduct(product, ImageUtil.path2MultipartFile(filePath0),
                productImgList);
        System.out.println("ProductExecution.state" + se.getState());
        System.out.println("ProductExecution.stateInfo" + se.getStateInfo());
    }
}