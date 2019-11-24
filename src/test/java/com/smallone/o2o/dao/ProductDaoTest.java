package com.smallone.o2o.dao;

import com.smallone.o2o.BaseTest;
import com.smallone.o2o.entity.Product;
import com.smallone.o2o.entity.ProductCategory;
import com.smallone.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author smallone
 * @created 2019--11--24--9:57
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

    @Autowired
    ProductDao productDao;

    @Test
    public void AinsertProduct() {
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        Product product = new Product();
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        product.setProductName("测试商品1");
        product.setProductDescription("测试商品描述1");
        product.setImgAddress("111");
        product.setNormalPrice("1111");
        product.setPromotionPrice("11111");
        product.setPriority(1);
        product.setProductCategory(productCategory);
        product.setShop(shop);
        int effectNum = productDao.insertProduct(product);
        System.out.println("effectNum:" + effectNum);
    }
}