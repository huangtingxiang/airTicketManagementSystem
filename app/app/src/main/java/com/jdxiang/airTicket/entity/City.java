package com.jdxiang.airTicket.entity;

import java.util.ArrayList;

public class City {
    private Long id; // id

    private String name; // 名称

    private String pinyin; // 拼音

    private Boolean primaried; // 是否热门城市

//    private List<AirPort> airPorts = new ArrayList<>(); // 机场集合


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Boolean getPrimaried() {
        return primaried;
    }

    public void setPrimaried(Boolean primaried) {
        this.primaried = primaried;
    }
}
