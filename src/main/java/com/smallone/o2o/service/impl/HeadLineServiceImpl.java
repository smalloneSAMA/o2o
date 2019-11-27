package com.smallone.o2o.service.impl;

import com.smallone.o2o.dao.HeadLineDao;
import com.smallone.o2o.entity.HeadLine;
import com.smallone.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 实现headlineservice
 *
 * @author smallone
 * @created 2019--11--26--19:22
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    HeadLineDao headLineDao;

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        return  headLineDao.selectHeadLineList(headLineCondition);
    }
}
