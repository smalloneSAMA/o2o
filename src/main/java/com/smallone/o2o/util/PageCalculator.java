package com.smallone.o2o.util;

/**
 * 页码行号计算器
 *
 * @author smallone
 * @created 2019--11--14--18:23
 */
public class PageCalculator {

    public  static  int calculateRowIndex(int pageIndex, int pageSize){
        return  (pageIndex > 0)? (pageIndex - 1)*pageSize : 0;
    }
}
