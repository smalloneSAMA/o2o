package com.smallone.o2o.service.impl;

import com.smallone.o2o.dao.AreaDao;
import com.smallone.o2o.entity.Area;
import com.smallone.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author smallone
 * @created 2019--11--11--14:26
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
