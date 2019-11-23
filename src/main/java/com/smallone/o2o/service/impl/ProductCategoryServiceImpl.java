package com.smallone.o2o.service.impl;

import com.smallone.o2o.dao.ProductCategoryDao;
import com.smallone.o2o.dto.ProductCategoryExecution;
import com.smallone.o2o.entity.ProductCategory;
import com.smallone.o2o.enums.ProductCategoryStateEnum;
import com.smallone.o2o.exceptions.ProductCategoryOperationException;
import com.smallone.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author smallone
 * @created 2019--11--22--19:42
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> getProductCategoryList(Long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {

        if(productCategoryList !=null && productCategoryList.size()>0){
            try {
                int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if(effectedNum <= 0){
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                }else {
                    return  new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS,productCategoryList);
                }
            }catch (Exception e){
                throw new ProductCategoryOperationException("batchAddProductCategory error:" + e.getMessage());
            }
        }else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPETY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(Long productCategoryId, Long shopId) throws ProductCategoryOperationException {
        // TODO 将此商品类别下的商品的类别id置为空

        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if(effectedNum<=0){
                throw new ProductCategoryOperationException("商品类别删除失败");
            }else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }

        }catch (Exception e){
            throw  new ProductCategoryOperationException("deleteProductCategory error: "+e.getMessage());
        }
    }
}
