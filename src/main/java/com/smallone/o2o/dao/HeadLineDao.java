package com.smallone.o2o.dao;

import com.smallone.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 头条
 *
 * @author smallone
 * @created 2019--11--26--18:30
 */
public interface HeadLineDao {

    /**
     * 添加首页头条
     *
     * @param headLine
     * @return
     */
    //Integer insertHeadLine(HeadLine headLine);

    /**
     * 根据传入的查询条件查询头条信息列表
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine> selectHeadLineList(@Param("headLineConditon") HeadLine headLineCondition);

    /**
     * 根据Id列表查询头条信息列表
     *
     * @param lineIdList
     * @return
     */
    //List<HeadLine> selectHeadLineByIds(List<Long> lineIdList);

    /**
     * 根据头条Id查询头条信息
     *
     * @param lineId
     * @return
     */
    //HeadLine selectHeadLineById(Long lineId);

    /**
     * 更新头条信息
     *
     * @param headLine
     * @return
     */
    //Integer updateHeadLine(HeadLine headLine);

    /**
     * 根据Id删除头条
     *
     * @param headLineId
     * @return
     */
    //Integer deleteHeadLine(Long headLineId);

    /**
     * 批量删除头条记录
     *
     * @param lineIdList
     * @return
     */
    //Integer batchDeleteHeadLine(List<Long> lineIdList);

}
