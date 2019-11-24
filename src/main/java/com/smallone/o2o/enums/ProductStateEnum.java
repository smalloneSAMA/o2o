package com.smallone.o2o.enums;

/**
 * 商品状态
 *
 * @author smallone
 * @created 2019--11--24--10:31
 */
public enum ProductStateEnum {
    PRODUCT_EMPTY(-2001, "请输入商品信息"),
    EDIT_ERROR(-2002, "商品编辑失败"),
    EMPTY(-2003, "商品为空"),
    PRODUCT_ID_EMPTY(-2004, "商品ID为空");

    private int state;
    private String stateInfo;

    private ProductStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    /**
     *
     * @Description: 定义换成pulic static 暴漏给外部,通过state获取ShopStateEnum
     *               values()获取全部的enum常量
     *
     * @param state
     * @return: ShopStateEnum
     */
    public static ProductStateEnum stateOf(int state) {
        for (ProductStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
    }
}
