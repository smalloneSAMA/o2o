package com.smallone.o2o.dao;

import com.smallone.o2o.BaseTest;
import com.smallone.o2o.entity.Product;
import com.smallone.o2o.entity.ProductCategory;
import com.smallone.o2o.entity.ProductImg;
import com.smallone.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author smallone
 * @created 2019--11--24--9:57
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductImgDao productImgDao;

    @Test
    @Ignore
    public void AinsertProduct() {
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        Product product = new Product();
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        product.setProductName("测试商品1");
        product.setProductDescription("测试商品描述1");
        product.setImgAddress("111");
        product.setNormalPrice("1111");
        product.setPromotionPrice("11111");
        product.setPriority(1);
        product.setProductCategory(productCategory);
        product.setShop(shop);
        int effectNum = productDao.insertProduct(product);
        System.out.println("effectNum:" + effectNum);
    }

    @Test
    @Ignore
    public void BqueryProductByProductId() {
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
        System.out.println("插入数量effectNum:" + effectNum);
        //查询productId为1的商品信息
        Long productId = 1L;
        Product product = productDao.queryProductByProductId(productId);
        System.out.println("产品图片数量productImgSize：" + product.getProductImgList().size());
        int effectedNum = productDao.deleteProductImgByProductId(productId);
        System.out.println("删除数量为：" + effectedNum);
    }

    @Test
    @Ignore
    public void CdeleteProductImgByProductId() {
        Long productId = 1L;
        int effectedNum = productDao.deleteProductImgByProductId(productId);
        System.out.println("删除数量为：" + effectedNum);
    }

    @Test
    public void updateProduct() {
        Product product = new Product();
        product.setProductId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        Shop shop = new Shop();
        shop.setShopId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试修改商品");
        int effectNum = productDao.updateProduct(product);
        System.out.println("effectNum:" + effectNum);
    }

    @Test
    public void selectProductList() {
        //List<Product> selectProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
        //@Param("pageSize") int pageSize);
        Product product = new Product();
        Shop shop = new Shop();
        ProductCategory productCategory = new ProductCategory();
        shop.setShopId(1L);
        productCategory.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        //product.setProductId(1L);

        List<Product> productList = productDao.selectProductList(product,0,2);
        System.out.println("查出来的大小为：" + productList.size());
    }

    @Test
    public void selectProductCount() {
        Product product = new Product();
        Shop shop = new Shop();
        ProductCategory productCategory = new ProductCategory();
        shop.setShopId(1L);
        //productCategory.setProductCategoryId(1L);
        product.setShop(shop);
        //product.setProductCategory(productCategory);
        Integer num = productDao.selectProductCount(product);
        System.out.println("查出来的大小为：" + num);
    }
}