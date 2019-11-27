package com.smallone.o2o.dao;

import com.smallone.o2o.BaseTest;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author smallone
 * @created 2019--11--13--11:09
 */
public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    ShopCategoryDao shopCategoryDao;

    @Test
    public void queryShopCategory() {
        //List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        //assertEquals(2,shopCategoryList.size());
        //ShopCategory shopCategory = new ShopCategory();
        //ShopCategory parent = new ShopCategory();
        //parent.setShopCategoryId(2L);
        //shopCategory.setParent(parent);
        //shopCategoryList = shopCategoryDao.queryShopCategory(shopCategory);
        //System.out.println(shopCategoryList.get(0).getShopCategoryDescription());
        //assertEquals(1,shopCategoryList.size());

        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        System.out.println("查询数量为：" + shopCategoryList.size());



    }
}