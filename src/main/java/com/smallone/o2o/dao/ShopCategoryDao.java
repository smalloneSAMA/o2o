package com.smallone.o2o.dao;

import com.smallone.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商铺类别dao
 *
 * @author smallone
 * @created 2019--11--13--11:01
 */
public interface ShopCategoryDao {
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);

}
