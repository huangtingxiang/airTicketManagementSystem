package com.xiang.airTicket.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

// 飞机机型
public enum PlaneType {

    LARGE, MEDIUM, SMALL; // 大 中 小

    @JsonValue
    public String getValue() {
        return String.valueOf(this.ordinal());
    }

}
