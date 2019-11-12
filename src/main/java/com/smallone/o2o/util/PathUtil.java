package com.smallone.o2o.util;

/**
 * 提供类路径，提供不同执行环境根路径
 *
 * @author smallone
 * @created 2019--11--12--10:05
 */
public class PathUtil {
    private static String seperator = System.getProperty("file.separator");

    /**
     * @return 项目图片根路径
     */
    public static String getImgBasePath(){


        String os = System.getProperty("os.name");
        String basePath="";
        if(os.toLowerCase().startsWith("win")){
            basePath = "D:/projectdev/image";
        }else{
            basePath = "/home/xiangze/image/";
        }
        basePath = basePath.replace("/",seperator);
        return basePath;
    }

    /**
     * @param shopId 商店id
     * @return 根据业务不同 返回项目图片子路径
     */
    public static String getShopImagePath(long shopId){
        String imagePath = "upload/item/shop" + shopId + "/";
        return imagePath.replace("/",seperator);
    }


}
