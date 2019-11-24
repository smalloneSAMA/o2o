package com.smallone.o2o.service;

import com.smallone.o2o.dto.ProductExecution;
import com.smallone.o2o.entity.Product;
import com.smallone.o2o.exceptions.ProductOperationException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 商品
 *
 * @author smallone
 * @created 2019--11--24--10:23
 */
public interface ProductService {
    /**
     * 添加商品信息以及图片处理
     *
     * @param product        商品信息
     * @param productImg     商品缩略图
     * @param productImgList 商品图片列表
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, MultipartFile productImg, List<MultipartFile> productImgList)
            throws ProductOperationException;
}
