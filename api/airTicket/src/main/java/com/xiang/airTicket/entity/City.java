package com.xiang.airTicket.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//城市
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private String name; // 名称

    private String pinyin; // 拼音

    private Boolean primary; // 是否热门城市

    @OneToMany
    private List<AirPort> airPorts = new ArrayList<>(); // 机场集合

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

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public List<AirPort> getAirPorts() {
        return airPorts;
    }

    public void setAirPorts(List<AirPort> airPorts) {
        this.airPorts = airPorts;
    }
}
