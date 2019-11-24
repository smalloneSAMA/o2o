package com.smallone.o2o.dto;

import com.smallone.o2o.entity.Product;
import com.smallone.o2o.enums.OperationStatusEnum;
import com.smallone.o2o.enums.ProductStateEnum;

import java.util.List;

/**
 * 商品包装类
 *
 * @author smallone
 * @created 2019--11--24--10:23
 */
public class ProductExecution {
    //结果状态
    private Integer state;
    //状态信息
    private String stateInfo;
    //商品数量
    private int count;
    //操作的商品
    private Product product;
    //获取的product列表
    private List<Product> productList;

    public ProductExecution(){

    }
    //操作失败的构造器
    public ProductExecution(ProductStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //操作成功的构造器
    public ProductExecution(OperationStatusEnum stateEnum,Product product){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.product = product;
    }

    //操作成功的构造器
    public ProductExecution(OperationStatusEnum stateEnum, List<Product> productList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productList = productList;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
