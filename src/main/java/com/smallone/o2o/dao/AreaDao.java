package com.smallone.o2o.dao;

import com.smallone.o2o.entity.Area;

import java.util.List;

/**
 * 区域类的接口DAO
 *
 * @author smallone
 * @created 2019--11--11--13:26
 */
public interface AreaDao {
    /**
     * 列出区域列表
     * @return areaList
     */
    List<Area> queryArea();



}
