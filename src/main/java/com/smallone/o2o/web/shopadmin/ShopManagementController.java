package com.smallone.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallone.o2o.dto.ShopExecution;
import com.smallone.o2o.entity.PersonInfo;
import com.smallone.o2o.entity.Shop;
import com.smallone.o2o.enums.ShopStateEnum;
import com.smallone.o2o.service.ShopService;
import com.smallone.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.management.modelmbean.ModelMBean;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 商铺管理
 *
 * @author smallone
 * @created 2019--11--12--21:06
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;

    @RequestMapping(value="/registershop",method = POST)
    private Map<String,Object> registerShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        //1.接收并且转化相应参数，包括店铺信息及图片
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            //使用jackson-databind
            shop = mapper.readValue(shopStr,Shop.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        // 获取图片文件流
        MultipartHttpServletRequest multipartRequest = null;
        MultipartFile shopImg = null;
        MultipartResolver multipartResolver =  new CommonsMultipartResolver(
                request.getSession().getServletContext());

        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = multipartHttpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }
        //2.注册店铺
        if(shop != null && shopImg != null){
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwner(owner);
            ShopExecution se = shopService.addShop(shop,shopImg);
            if(se.getState() != ShopStateEnum.CHECK.getState()){
                modelMap.put("success",true);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","请输入店铺信息");
            }
            return modelMap;


        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }

    }

    /**
     * 私有方法，用于转换CommonsMultipartFile为File
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file){
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = ins.read(buffer)) != -1) {
                os.write(buffer,0,bytesRead);
            }
        }catch (Exception e){
            throw new RuntimeException("调用inputStreamToFile产生异常" + e.getMessage());
        }finally {
            try {
                if(os != null){
                    os.close();
                }
                if(ins != null){
                    ins.close();
                }
            }catch (IOException e){
                throw new RuntimeException("inputStreamToFile关闭io产生异常" + e.getMessage());
            }
        }

    }

}
