package com.smallone.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理HttpServlet 参数
 *
 * @author smallone
 * @created 2019--11--12--21:11
 */
public class HttpServletRequestUtil {

    /**
     * 根据key获取request中的整数，并转化为数值
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request,String key){
        try {
            return Integer.decode(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * 根据key获取request中的长整型，并转化为数值
     * @param request
     * @param key
     * @return
     */
    public static long getLong(HttpServletRequest request,String key){
        try {
            return Long.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1;
        }
    }
    /**
     * 根据key获取request中的浮点型，并转化为数值
     * @param request
     * @param key
     * @return
     */
    public static double getDouble(HttpServletRequest request,String key){
        try {
            return Double.valueOf(request.getParameter(key));
        }catch (Exception e){
            return -1d;
        }
    }
    /**
     * 根据key获取request中的布尔型，并转化为数值
     * @param request
     * @param key
     * @return
     */
    public static boolean getBoolean(HttpServletRequest request,String key){
        try {
            return Boolean.valueOf(request.getParameter(key));
        }catch (Exception e){
            return false;
        }
    }
    /**
     * 根据key获取request中的字符串
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request,String key){
        try {
            String result = request.getParameter(key);
            if(request != null){
                //取出首位空格
                result = result.trim();
            }
            if("".equals(result)){
                return null;
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }
}
