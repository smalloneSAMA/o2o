package com.smallone.o2o.dao;

import com.smallone.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品
 *
 * @author smallone
 * @created 2019--11--24--9:29
 */
public interface ProductDao {

    /**
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> selectProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
                                    @Param("pageSize") int pageSize);

    /**
     * @param productCondition
     * @return
     */
    Integer selectProductCount(@Param("productCondition") Product productCondition);

    /**
     * 插入商品
     * @param product
     * @return
     */
    Integer insertProduct(Product product);

    /**
     * 根据商品Id获取商品信息
     * @param productId
     * @return
     */
    Product queryProductByProductId(Long productId);

    /**
     * 更新商品信息
     *
     * @param product
     * @return
     */
    Integer updateProduct(Product product);

    /**
     * 根据商品Id删除商品
     * @param productId
     * @return
     */
    Integer deleteProductImgByProductId(Long productId);

    /**
     * 删除商品类别时将商品记录中的类别项置空
     *
     * @param productCategoryId
     * @return
     */
    Integer updateProductCategoryToNull(Long productCategoryId);


}
