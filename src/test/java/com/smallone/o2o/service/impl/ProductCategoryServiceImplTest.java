package com.smallone.o2o.service.impl;

import com.smallone.o2o.BaseTest;
import com.smallone.o2o.service.ProductCategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author smallone
 * @created 2019--11--22--19:47
 */
public class ProductCategoryServiceImplTest extends BaseTest {
    @Autowired
    ProductCategoryService productCategoryService;

    @Test
    public void getProductCategoryList() {
        Long shopId = 1L;
        System.out.println("商品信息数量为： "+ productCategoryService.getProductCategoryList(shopId).size());
    }
}