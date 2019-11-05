package com.xiang.airTicket.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    SUPER_ROOT, ROOT, VISITOR; // 超级管理员 ，管理员 ， 旅客

    @JsonValue
    public String getValue() {
        return String.valueOf(this.ordinal());
    }
}
