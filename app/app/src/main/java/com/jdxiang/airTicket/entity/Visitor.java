package com.jdxiang.airTicket.entity;

import java.util.ArrayList;

public class Visitor {

    private Long id; // id

    private String name; // 名字

    private String idCard; // 身份证号码

    private String phoneNumber; // 手机号

    private Double balance = 0.0;

    User user;

//    List<TicketOrder> ticketOrders = new ArrayList<>(); // 订单号

    public Visitor(String name, String idCard, String phoneNumber) {
        this.name = name;
        this.idCard = idCard;
        this.phoneNumber = phoneNumber;
    }


    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
