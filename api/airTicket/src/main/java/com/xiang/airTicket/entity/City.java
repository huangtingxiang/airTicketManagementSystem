package com.xiang.airTicket.entity;

import com.fasterxml.jackson.annotation.JsonView;

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

    private Boolean primaried; // 是否热门城市

    @OneToMany
    @JsonView(AirPortsJsonView.class)
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

    public Boolean getPrimaried() {
        return primaried;
    }

    public void setPrimaried(Boolean primaried) {
        this.primaried = primaried;
    }

    public List<AirPort> getAirPorts() {
        return airPorts;
    }

    public void setAirPorts(List<AirPort> airPorts) {
        this.airPorts = airPorts;
    }

    public interface AirPortsJsonView {
    }
}
