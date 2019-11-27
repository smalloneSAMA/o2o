package com.smallone.o2o.service;

import com.smallone.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

/**
 * 头条
 *
 * @author smallone
 * @created 2019--11--26--19:21
 */
public interface HeadLineService {

    /**
     * genuine掺入的条件返回指定的头条列表
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;

}
