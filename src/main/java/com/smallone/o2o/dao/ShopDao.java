package com.smallone.o2o.dao;

import com.smallone.o2o.entity.Shop;

/**
 * 店铺接口类
 *
 * @author smallone
 * @created 2019--11--11--21:07
 */
public interface ShopDao {

    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

}
