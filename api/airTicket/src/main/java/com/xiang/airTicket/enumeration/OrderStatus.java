package com.xiang.airTicket.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    SUBSCRIBE, ACTIVE, FINISH, CANCEL; // 预定，使用中，完成，取消

    @JsonValue
    public String getValue() {
        return String.valueOf(this.ordinal());
    }
}
