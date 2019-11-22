package com.smallone.o2o.service;


import com.smallone.o2o.entity.ProductCategory;

import java.util.List;

/**
 * 店铺类别Service
 *
 * @author smallone
 * @created 2019--11--13--11:31
 */
public interface ProductCategoryService {

    /**
     * 查询某个商店下所有的商品列表信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList (Long shopId);

}
