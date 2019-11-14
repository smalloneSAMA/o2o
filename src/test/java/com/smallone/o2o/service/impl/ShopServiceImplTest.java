package com.smallone.o2o.service.impl;

import com.smallone.o2o.dto.ShopExecution;
import com.smallone.o2o.entity.Area;
import com.smallone.o2o.entity.PersonInfo;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.entity.ShopCategory;
import com.smallone.o2o.enums.ShopStateEnum;
import com.smallone.o2o.service.ShopService;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author smallone
 * @created 2019--11--14--18:32
 */
public class ShopServiceImplTest {

    @Autowired
    private ShopService shopService;
    /*static {
        (new DefaultVFS()).addImplClass(SmalloneVFS.class);
    }*/


    @Test
    public void getShopList() {
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        //PersonInfo owner = new PersonInfo();
        //owner.setUserId(1L);
        //shopCondition.setOwner(owner);
        shopCondition.setShopCategory(sc);
        ShopExecution se =  shopService.getShopList(shopCondition,1,3);
        System.out.println("店铺列表数： " + se.getShopList().size());
        System.out.println("店铺综述： " + se.getCount());
    }

    @Test
    @Ignore
    public void addShop() throws IOException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺1");
        shop.setShopDescription("test1");
        shop.setShopAddress("test1");
        shop.setPhone("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        String filePath = "E:\\学习\\java\\项目学习\\o2o\\src\\main\\resources\\image\\gakki1.jpg";
        //MultipartFile shopImg = new File(filePath);
        //File shopImg = new File("E:/学习/java/项目学习/o2o/src/main/resources/image");
        ShopExecution se = shopService.addShop(shop, path2MultipartFile(filePath));
        assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
        System.out.println("ShopExecution.stateInfo" + se.getStateInfo());
    }

    @Test
    @Ignore
    public void modifyShop() throws IOException {
        Shop shop = new Shop();
        shop.setShopId(1l);
        shop.setShopName("修改后的店铺名称");
        String filePath = "E:\\学习\\java\\项目学习\\o2o\\src\\main\\resources\\image\\gakki2.jpg";
        ShopExecution shopExecution = shopService.modifyShop(shop,path2MultipartFile(filePath));
        System.out.println("新图片地址：" + shopExecution.getShop().getShopImg());
    }

    /**
     * filePath to MultipartFile
     *
     * @param filePath
     * @throws IOException
     */
    private MultipartFile path2MultipartFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
                IOUtils.toByteArray(input));
        return multipartFile;
    }
}