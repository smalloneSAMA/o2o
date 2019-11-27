package com.smallone.o2o.service;

import com.smallone.o2o.entity.ShopCategory;

import java.util.List;

/**
 * 店铺类别Service
 *
 * @author smallone
 * @created 2019--11--13--11:31
 */
public interface ShopCategoryService {
    /**
     * 根据查询条件查询商品类型
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
