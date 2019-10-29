package com.xiang.airTicket.entity;

import javax.persistence.*;

// 订单价钱
@Entity
public class TicketPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private Double price; // 价钱

    @OneToOne
    ShipSpace shipSpace; // 对应舱位

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ShipSpace getShipSpace() {
        return shipSpace;
    }

    public void setShipSpace(ShipSpace shipSpace) {
        this.shipSpace = shipSpace;
    }
}
