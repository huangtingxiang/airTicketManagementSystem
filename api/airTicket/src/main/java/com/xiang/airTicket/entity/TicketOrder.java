package com.xiang.airTicket.entity;

import com.xiang.airTicket.enumeration.OrderStatus;

import javax.persistence.*;
import java.util.Date;

// 订单实体
@Entity
public class TicketOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private OrderStatus orderStatus = OrderStatus.ACTIVE; // 订单状态

    private Date createTime; // 创建时间

    @ManyToOne
    FlightManagement flightManagement; // 对应航班

    @ManyToOne
    private Visitor visitor; // 对应乘客

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public FlightManagement getFlightManagement() {
        return flightManagement;
    }

    public void setFlightManagement(FlightManagement flightManagement) {
        this.flightManagement = flightManagement;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
}
