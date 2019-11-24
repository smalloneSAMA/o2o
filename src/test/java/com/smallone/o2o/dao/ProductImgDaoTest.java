package com.smallone.o2o.dao;

import com.smallone.o2o.BaseTest;
import com.smallone.o2o.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author smallone
 * @created 2019--11--24--9:52
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void AbatchInsertProductImg() {
        // 在productId为1的商品中添加两个图片记录
        ProductImg productImg1 = new ProductImg();
        productImg1.setCreateTime(new Date());
        productImg1.setImgAddress("图片1");
        productImg1.setImgDescription("测试图片1");
        productImg1.setPriority(1);
        productImg1.setProductId(1L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setCreateTime(new Date());
        productImg2.setImgAddress("图片2");
        productImg2.setImgDescription("测试图片2");
        productImg2.setPriority(2);
        productImg2.setProductId(1L);
        List<ProductImg> productImgs = new ArrayList<>();
        productImgs.add(productImg1);
        productImgs.add(productImg2);
        int effectNum = productImgDao.batchInsertProductImg(productImgs);
        System.out.println("effectNum:" + effectNum);
    }
}