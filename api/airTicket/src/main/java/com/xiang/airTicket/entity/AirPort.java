package com.xiang.airTicket.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

// 机场表
@Entity
public class AirPort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private String icon; // 图标

    private String name;

    @ManyToOne
    @JsonView(CityJsonView.class)
    private City city; // 所属城市

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public interface CityJsonView {
    }
}
