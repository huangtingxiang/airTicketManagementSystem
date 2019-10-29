package com.xiang.airTicket.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 舱位表
@Entity
public class ShipSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String described; // 描述

    private Boolean primaried; // 是否为主舱位

    @OneToMany(cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>(); // 座位集合

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
    }

    public Boolean getPrimaried() {
        return primaried;
    }

    public void setPrimaried(Boolean primaried) {
        this.primaried = primaried;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public interface SeatJsonView {
    }
}
