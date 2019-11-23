package com.smallone.o2o.service;


import com.smallone.o2o.dto.ProductCategoryExecution;
import com.smallone.o2o.entity.ProductCategory;
import com.smallone.o2o.exceptions.ProductCategoryOperationException;

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

    /**
     * 批量增加商品类别
     * @param productCategoryList
     * @return
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
    throws ProductCategoryOperationException;

    /**
     * 将此类别下的商品里的类别id置为空，在删除掉该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(Long productCategoryId, Long shopId)
        throws ProductCategoryOperationException;
}
