package com.smallone.o2o.enums;

/**
 * 商品类别状态
 *
 * @author smallone
 * @created 2019--11--22--22:42
 */
public enum ProductCategoryStateEnum {
    SUCCESS(-2000,"创建成功"),
    NULL_SHOP(-2001, "Shop信息为空"),
    EMPETY_LIST(-2002, "请输入商品目录信息"),
    DELETE_ERROR(-2003, "商品类别删除失败"),
    EDIT_ERROR(-2004, "商品类别编辑失败");

    private int state;
    private String stateInfo;

    /**
     * @Description:构造函数
     *
     * @param state
     * @param stateInfo
     */
    private ProductCategoryStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * @Description: 通过state获取productCategoryStateEnum,从而可以调用ProductCategoryStateEnum
     *               #getStateInfo()获取stateInfo
     *
     * @param index
     */
    public static ProductCategoryStateEnum stateOf(int index) {
        for (ProductCategoryStateEnum productCategoryStateEnum : values()) {
            if (productCategoryStateEnum.getState() == index) {
                return productCategoryStateEnum;
            }
        }
        return null;
    }



}
