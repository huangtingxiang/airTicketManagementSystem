package com.xiang.airTicket.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private String name; // 名字

    private String idCard; // 身份证号码

    private String phoneNumber; // 手机号

    @OneToMany
    List<TicketOrder> ticketOrders = new ArrayList<>(); // 订单号

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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<TicketOrder> getOrders() {
        return ticketOrders;
    }

    public void setOrders(List<TicketOrder> orders) {
        this.ticketOrders = orders;
    }
}
