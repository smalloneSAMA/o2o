package com.smallone.o2o.service.impl;

import com.smallone.o2o.dao.ProductDao;
import com.smallone.o2o.dao.ProductImgDao;
import com.smallone.o2o.dto.ProductExecution;
import com.smallone.o2o.entity.Product;
import com.smallone.o2o.entity.ProductImg;
import com.smallone.o2o.enums.EnableStatusEnum;
import com.smallone.o2o.enums.OperationStatusEnum;
import com.smallone.o2o.enums.ProductStateEnum;
import com.smallone.o2o.exceptions.ProductOperationException;
import com.smallone.o2o.service.ProductService;
import com.smallone.o2o.util.ImageUtil;
import com.smallone.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author smallone
 * @created 2019--11--24--10:23
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    @Transactional
    public ProductExecution addProduct(Product product, MultipartFile productImg, List<MultipartFile> productImgList) throws ProductOperationException {
        //1.处理缩略图，获取缩略图相对路径并赋值给product
        //2.往tb_product写入商品信息，获取productId
        //3.结合productId批量处理商品详情图
        //4.将商品详情图列表批量插入tb_product_img中

        // 空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            // 给商品设置默认属性
            product.setCreateTime(new Date());
            // 默认上架状态
            product.setEnableStatus(EnableStatusEnum.AVAILABLE.getState());
            // 若商品缩略图不为空则添加
            if (productImg != null) {
                addProductImg(product, productImg);
            }
            // 创建商品信息
            try {
                int effectNum = productDao.insertProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException(ProductStateEnum.EDIT_ERROR.getStateInfo());
                }
            } catch (Exception e) {
                throw new ProductOperationException(ProductStateEnum.EDIT_ERROR.getStateInfo() + e.toString());
            }
            // 若商品详情图列表不为空则添加
            if (productImgList != null && !productImgList.isEmpty()) {
                addProductImgList(product, productImgList);
            }
            return new ProductExecution(OperationStatusEnum.SUCCESS, product);
        } else {
            // 参数为空则返回空值错误信息
            return new ProductExecution(ProductStateEnum.EMPTY);
        }

    }

    /**
     * 添加商品缩略图
     *
     * @param product    商品
     * @param productImg 商品缩略图
     */
    private void addProductImg(Product product, MultipartFile productImg) {
        // 获取图片存储路径，将图片放在相应店铺的文件夹下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String productImgAddr = ImageUtil.generateThumbnail(productImg, dest);
        product.setImgAddress(productImgAddr);
    }

    /**
     * 添加商品详情图
     *
     * @param product        商品
     * @param productImgList 商品详情图列表
     */
    private void addProductImgList(Product product, List<MultipartFile> productImgList) {
        // 获取图片存储路径，将图片放在相应店铺的文件夹下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgs = new ArrayList<>();
        // 遍历商品详情图，并添加到productImg中
        for (MultipartFile multipartFile : productImgList) {
            String imgAddr = ImageUtil.generateProductImg(multipartFile, dest);
            ProductImg productImg = new ProductImg();
            productImg.setProductId(product.getProductId());
            productImg.setImgAddress(imgAddr);
            productImg.setCreateTime(new Date());
            productImgs.add(productImg);
        }
        // 存入有图片，就执行批量添加操作
        if (productImgs.size() > 0) {
            try {
                int effectNum = productImgDao.batchInsertProductImg(productImgs);
                if (effectNum <= 0) {
                    throw new ProductOperationException(OperationStatusEnum.PIC_UPLOAD_ERROR.getStateInfo());
                }
            } catch (Exception e) {
                throw new ProductOperationException(OperationStatusEnum.PIC_UPLOAD_ERROR.getStateInfo() + e.toString());
            }
        }
    }
}
