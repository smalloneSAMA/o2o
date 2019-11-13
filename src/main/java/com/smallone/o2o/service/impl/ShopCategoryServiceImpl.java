package com.smallone.o2o.service.impl;

import com.smallone.o2o.dao.ShopCategoryDao;
import com.smallone.o2o.entity.ShopCategory;
import com.smallone.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author smallone
 * @created 2019--11--13--11:32
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {

        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }
}
