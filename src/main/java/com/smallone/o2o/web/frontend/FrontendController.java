package com.smallone.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 路由定义
 *
 * @author smallone
 * @created 2019--11--26--19:54
 */
@RequestMapping(value = "/frontend")
@Controller
public class FrontendController {

    /**
     * 前端首页路由
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    private String index(){
        return "frontend/index";
    }

    /**
     * 店铺列表页路由
     *
     * @return
     */
    @RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    public String shopList() {
        return "frontend/shoplist";
    }

    /**
     * 店铺详情页路由
     *
     * @return
     */
    @RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
    public String shopDetail() {
        return "frontend/shopdetail";
    }

    /**
     * 商品详情页路由
     *
     * @return
     */
    @RequestMapping(value = "/productdetail", method = RequestMethod.GET)
    public String productDetail() {
        return "frontend/productdetail";
    }

}
