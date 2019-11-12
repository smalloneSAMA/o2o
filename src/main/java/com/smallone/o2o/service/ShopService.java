package com.smallone.o2o.service;

import com.smallone.o2o.dto.ShopExecution;
import com.smallone.o2o.entity.Shop;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 商店Service
 *
 * @author smallone
 * @created 2019--11--12--12:58
 */
public interface ShopService {

    ShopExecution addShop(Shop shop, MultipartFile shopImg);

}
