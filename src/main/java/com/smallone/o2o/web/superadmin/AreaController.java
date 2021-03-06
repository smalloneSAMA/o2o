package com.smallone.o2o.web.superadmin;

import com.smallone.o2o.entity.Area;
import com.smallone.o2o.service.AreaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 区域Controller
 *
 * @author smallone
 * @created 2019--11--11--14:37
 */

@Controller
@RequestMapping("/superadmin")
public class AreaController {
    Logger logger = LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private AreaService areaService;

    @RequestMapping("/listarea")
    @ResponseBody
    private Map<String,Object> listArea(){
        logger.info("====start====");
        long startTime = System.currentTimeMillis();
        Map<String,Object> modelMap = new HashMap<>();
        List<Area> list;
        try {
            list = areaService.getAreaList();
            modelMap.put("rows",list);
            modelMap.put("total",list.size());
        }catch (Exception e){
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
        }
        logger.error("test error");
        long endTime = System.currentTimeMillis();
        logger.debug("costTime:[{}ms]",endTime-startTime);
        logger.info("====end====");
        return modelMap;
    }


}
