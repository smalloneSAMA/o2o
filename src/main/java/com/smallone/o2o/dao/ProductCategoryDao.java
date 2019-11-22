package com.smallone.o2o.dao;

import com.smallone.o2o.entity.Product;
import com.smallone.o2o.entity.ProductCategory;

import java.util.List;

/**
 * 商品DAO
 *
 * @author smallone
 * @created 2019--11--22--19:16
 */
public interface ProductCategoryDao {


    /**
     * 按照店铺Id查询该店铺商品类别
     * @param shopId  店铺Id
     * @return
     */
    List<ProductCategory> queryProductCategoryList(Long shopId);

}
