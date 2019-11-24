package com.smallone.o2o.enums;

/**
 * 状态列表
 *
 * @author smallone
 * @created 2019--11--24--11:01
 */
public enum EnableStatusEnum {
    UNAVAILABLE(0, "不可用"), AVAILABLE(1, "可用"), CHECK(2, "审核中");
    private int state;
    private String stateInfo;

    private EnableStatusEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    // 根据传入的state值返回相应的状态值
    public static EnableStatusEnum stateOf(int index) {
        for (EnableStatusEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
