package com.xiang.airTicket.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.config.NoneJsonView;

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

    private Double balance = 0.0; // 余额

    private String imageUrl;

    @OneToOne(mappedBy = "visitor")
    @JsonView({NoneJsonView.class, UserJsonView.class})
    User user;

    @OneToMany(mappedBy = "visitor")
    @JsonView({Visitor.TicketOrderJsonView.class, NoneJsonView.class})
    List<TicketOrder> ticketOrders = new ArrayList<>(); // 订单号

    @OneToMany(mappedBy = "visitor")
    @JsonView(TransactionRecordsJsonView.class)
    List<TransactionRecord> transactionRecords = new ArrayList<>(); // 交易记录

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<TicketOrder> getTicketOrders() {
        return ticketOrders;
    }

    public void setTicketOrders(List<TicketOrder> ticketOrders) {
        this.ticketOrders = ticketOrders;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public interface TicketOrderJsonView {
    }

    public interface TransactionRecordsJsonView {
    }

    public List<TransactionRecord> getTransactionRecords() {
        return transactionRecords;
    }

    public void setTransactionRecords(List<TransactionRecord> transactionRecords) {
        this.transactionRecords = transactionRecords;
    }

    public interface UserJsonView {
    }
}
