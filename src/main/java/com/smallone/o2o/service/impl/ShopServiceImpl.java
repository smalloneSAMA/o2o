package com.smallone.o2o.service.impl;

import com.smallone.o2o.dao.ShopDao;
import com.smallone.o2o.dto.ShopExecution;
import com.smallone.o2o.dto.ShopOperationExecution;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.enums.ShopStateEnum;
import com.smallone.o2o.exceptions.ShopOperationException;
import com.smallone.o2o.service.ShopService;
import com.smallone.o2o.util.ImageUtiil;
import com.smallone.o2o.util.PageCalculator;
import com.smallone.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author smallone
 * @created 2019--11--12--13:01
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;


    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if(shopList != null){
            se.setShopList(shopList);
            se.setCount(count);
        }else{
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, MultipartFile shopImg) throws ShopOperationException {
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

    @Override
    public Shop getByShopId(Long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    @Transactional
    public ShopExecution modifyShop(Shop shop, MultipartFile shopImg) throws ShopOperationException {
        // 判断店铺是否存在
        if(shop == null  ||  shop.getShopId() == null){
            return  new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            //判断是否要处理图片
            try {
                if(shopImg != null){
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if(tempShop.getShopImg() != null){
                        //删除原先图片
                        ImageUtiil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    //添加新照片
                    addShopImg(shop,shopImg);
                    //2.更新店铺信息
                    shop.setLastEditTime(new Date());
                    int effectedNum = shopDao.updateShop(shop);
                    if(effectedNum <= 0){
                        return  new ShopExecution(ShopStateEnum.INNER_ERROR);
                    }else {
                        shop = shopDao.queryByShopId(shop.getShopId());
                        return  new ShopExecution(ShopStateEnum.SUCCESS,shop);
                    }
                }
            }catch (Exception e){
                throw  new ShopOperationException("modifyShop error: " + e.getMessage());
            }
        }
        return new ShopExecution(ShopStateEnum.SUCCESS,shop);
    }


}
