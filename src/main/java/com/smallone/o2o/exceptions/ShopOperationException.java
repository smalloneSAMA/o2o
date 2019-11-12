package com.smallone.o2o.exceptions;

import jdk.internal.org.objectweb.asm.commons.SerialVersionUIDAdder;

/**
 * 商铺操作异常
 *
 * @author smallone
 * @created 2019--11--12--17:30
 */
public class ShopOperationException extends RuntimeException {

    private static final long serialVersionUID = -1440764912500202616L;

    public ShopOperationException(String msg){
        super(msg);
    }




}
