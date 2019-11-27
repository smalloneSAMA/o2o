package com.smallone.o2o.dao;

import com.smallone.o2o.BaseTest;
import com.smallone.o2o.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author smallone
 * @created 2019--11--26--19:06
 */
public class HeadLineDaoTest extends BaseTest {

    @Autowired
    HeadLineDao headLineDao;

    @Test
    public void insertHeadLine() {
    }

    @Test
    public void selectHeadLineList() {
        List<HeadLine> headLineList = headLineDao.selectHeadLineList(new HeadLine());
        System.out.println("查询个数为：" + headLineList.size());
    }

    @Test
    public void selectHeadLineByIds() {
    }

    @Test
    public void selectHeadLineById() {
    }

    @Test
    public void updateHeadLine() {
    }

    @Test
    public void deleteHeadLine() {
    }

    @Test
    public void batchDeleteHeadLine() {
    }
}