package com.smallone.o2o.service;

import com.smallone.o2o.dto.ShopExecution;
import com.smallone.o2o.dto.ShopOperationExecution;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.exceptions.ShopOperationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * 商店Service
 *
 * @author smallone
 * @created 2019--11--12--12:58
 */
public interface ShopService {

    /**
     * 注册店铺信息，包括图片处理
     * @param shop
     * @param shopImg
     * @return
     */
    ShopExecution addShop(Shop shop, MultipartFile shopImg) throws ShopOperationException;

    /**
     * 通过店铺Id获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(Long shopId);


    /**
     * 更新店铺信息，包括对图片处理
     * @param shop
     * @param  shopImg
     * @return
     */
    ShopExecution modifyShop(Shop shop, MultipartFile shopImg)throws ShopOperationException;

}
