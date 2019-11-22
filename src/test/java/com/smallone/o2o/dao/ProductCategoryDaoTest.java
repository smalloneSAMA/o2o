package com.smallone.o2o.dao;

import com.smallone.o2o.BaseTest;
import com.smallone.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author smallone
 * @created 2019--11--22--19:35
 */
public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    ProductCategoryDao productCategoryDao;

    @Test
    public void queryProductCategoryList() {
        Long shopId = 1L;
        List<ProductCategory> productCategoryList =  productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("产品类别数量为： " + productCategoryList.size());
    }
}