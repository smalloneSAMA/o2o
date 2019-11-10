package com.smallone.o2o.entity;

import java.util.Date;

/**
 * 商品图片实体类
 *
 * @author smallone
 * @created 2019--11--11--0:47
 */
public class ProductImg {

    private Long productImgId;
    private String imgAddress;
    private String imgDescription;
    private Date createTime;
    private Integer priority;
    private Long productId;

    public Long getProductImgId() {
        return productImgId;
    }

    public void setProductImgId(Long productImgId) {
        this.productImgId = productImgId;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }

    public String getImgDescription() {
        return imgDescription;
    }

    public void setImgDescription(String imgDescription) {
        this.imgDescription = imgDescription;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
