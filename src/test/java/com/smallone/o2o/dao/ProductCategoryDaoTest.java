package com.smallone.o2o.dao;

import com.smallone.o2o.BaseTest;
import com.smallone.o2o.entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author smallone
 * @created 2019--11--22--19:35
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    ProductCategoryDao productCategoryDao;

    @Test
    @Ignore
    public void queryProductCategoryList() {
        Long shopId = 1L;
        List<ProductCategory> productCategoryList =  productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("产品类别数量为： " + productCategoryList.size());
    }

    @Test
    @Ignore
    public void batchInsertProductCategory() {
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryName("卤蛋1号");
        productCategory1.setPriority(1);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(1L);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("卤蛋2号");
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);

        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);
        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2,effectedNum);
    }

    @Test
    public void deleteProductCategory() {
        productCategoryDao.deleteProductCategory(9L,1L);
        productCategoryDao.deleteProductCategory(10L,1L);

    }
}