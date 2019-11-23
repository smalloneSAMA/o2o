package com.smallone.o2o.exceptions;

import com.smallone.o2o.entity.ProductCategory;
import com.smallone.o2o.enums.ProductCategoryStateEnum;

import java.util.List;

/**
 * @author smallone
 * @created 2019--11--23--16:44
 */
public class ProductCategoryOperationException extends RuntimeException{

    private static final long serialVersionUID = 5880193977266759324L;

    public ProductCategoryOperationException(String msg){
        super(msg);
    }

}
