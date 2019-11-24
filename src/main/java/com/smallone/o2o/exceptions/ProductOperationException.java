package com.smallone.o2o.exceptions;

/**
 * 商品操作异常类
 *
 * @author smallone
 * @created 2019--11--24--10:25
 */
public class ProductOperationException extends RuntimeException {
    private static final long serialVersionUID = 678748836349643066L;

    public ProductOperationException(String msg) {
        super(msg);
    }
}
