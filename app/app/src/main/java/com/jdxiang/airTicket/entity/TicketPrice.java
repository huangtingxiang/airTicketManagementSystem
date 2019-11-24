package com.jdxiang.airTicket.entity;

public class TicketPrice {

    private Long id; // id

    private Double price; // 价钱

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
