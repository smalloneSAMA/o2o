package com.smallone.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 商铺管理员操作
 *
 * @author smallone
 * @created 2019--11--12--23:27
 */
@Controller
@RequestMapping(value = "/shopadmin",method = RequestMethod.GET)
public class ShopAdminController {

    @RequestMapping(value = "/shopoperation",method = RequestMethod.GET)
    public String shopOpration(){
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist",method = RequestMethod.GET)
    public String shopList(){
        return "shop/shoplist";
    }

    /**
     * 店铺管理页面
     *
     * @return
     */
    @RequestMapping(value = "/shopmanagement", method = RequestMethod.GET)
    public String shopManagement() {
        return "shop/shopmanagement";
    }


}
