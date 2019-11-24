package com.smallone.o2o.dao;

import com.smallone.o2o.entity.ProductImg;

import java.util.List;

/**
 * 商品图片
 *
 * @author smallone
 * @created 2019--11--24--9:35
 */
public interface ProductImgDao {

    /**
     * 批量添加商品详情图
     * @param productImgs
     * @return
     */
    Integer batchInsertProductImg(List<ProductImg> productImgs);

}
