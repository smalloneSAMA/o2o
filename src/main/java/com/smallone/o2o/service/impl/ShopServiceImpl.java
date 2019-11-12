package com.smallone.o2o.service.impl;

import com.smallone.o2o.dao.ShopDao;
import com.smallone.o2o.dto.ShopExecution;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.enums.ShopStateEnum;
import com.smallone.o2o.exceptions.ShopOperationException;
import com.smallone.o2o.service.ShopService;
import com.smallone.o2o.util.ImageUtiil;
import com.smallone.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;

/**
 * @author smallone
 * @created 2019--11--12--13:01
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, MultipartFile shopImg) {
        //空值判断
        if(shop == null)
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        try{
            //给店铺信息复制初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if(effectedNum<=0){
                throw new ShopOperationException("店铺创建失败");
            }else{
                if(shopImg!=null){
                    try {
                        //存储图片
                        addShopImg(shop,shopImg);
                    }catch (Exception e){
                        throw new ShopOperationException("addShopImg error" + e.getMessage());
                    }
                    //更新店铺信息
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum<=0){
                        throw new ShopOperationException("跟新图片地址失败");
                    }
                }

            }
        }catch (Exception e){
            throw new ShopOperationException("addShop error"+ e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, MultipartFile shopImg) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        //获取
        String shopImgAddr = ImageUtiil.generateThumbnail(shopImg,dest);
        shop.setShopImg(shopImgAddr);
    }
}
