package com.smallone.o2o.dao;

import com.smallone.o2o.BaseTest;
import com.smallone.o2o.entity.Area;
import com.smallone.o2o.entity.PersonInfo;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author smallone
 * @created 2019--11--11--21:21
 */
public class ShopDaoTest extends BaseTest {
    @Autowired
    ShopDao shopDao;

    @Test
    public void QueryShopListAndCount(){
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(2L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);
        List<Shop> shopList =  shopDao.queryShopList(shopCondition,0,6);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺列表大小： " + shopList.size());
        System.out.println("店铺总数： " + count);
    }

    @Test
    @Ignore
    public void insertShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺");
        shop.setShopDescription("test");
        shop.setShopAddress("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1,effectedNum);
    }

    @Test
    @Ignore
    public void updateShop() {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("测试的店铺");
        shop.setShopDescription("测试描述");
        shop.setShopAddress("测试地址");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1,effectedNum);



    }

    @Test
    @Ignore
    public void queryByShopId() {
        long shopId = 1;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("area_id: " + shop.getArea().getAreaId());
        System.out.println("area_name: " + shop.getArea().getAreaName());
    }

    @Test
    @Ignore
    public void queryShopList() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        List<Shop> shopList =  shopDao.queryShopList(shopCondition,0,5);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺类型数目： " + shopList.size());
        System.out.println("店铺总数： " + count);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        shopCondition.setShopCategory(sc);
        shopList = shopDao.queryShopList(shopCondition,0,2);
        count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺类型数目： " + shopList.size());
        System.out.println("店铺总数： " + count);
    }

    @Test
    @Ignore
    public void queryShopCount() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("查询商铺总数为： " + count);
    }
}