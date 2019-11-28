package com.jdxiang.airTicket.entity;

import java.util.Date;

public class TransactionRecord {


    private Long id; // id

    private Date createTime; // 创建时间

    String recordMessage; // 描述信息

    Double price; // 交易金钱


    Visitor visitor; // 对应旅客

    Boolean payFor = false; // 为支付


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRecordMessage() {
        return recordMessage;
    }

    public void setRecordMessage(String recordMessage) {
        this.recordMessage = recordMessage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Boolean getPayFor() {
        return payFor;
    }

    public void setPayFor(Boolean payFor) {
        this.payFor = payFor;
    }
}
