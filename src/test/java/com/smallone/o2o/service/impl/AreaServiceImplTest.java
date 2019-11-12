package com.smallone.o2o.service.impl;

import com.smallone.o2o.BaseTest;
import com.smallone.o2o.entity.Area;
import com.smallone.o2o.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author smallone
 * @created 2019--11--11--14:30
 */
public class AreaServiceImplTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void getAreaList() {
        List<Area> areaList =areaService.getAreaList();
        assertEquals("南苑",areaList.get(0).getAreaName());
    }
}