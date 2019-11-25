package com.xiang.airTicket.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.xiang.airTicket.enumeration.OrderStatus;

import javax.persistence.*;
import java.util.Date;

// 订单实体
@Entity
public class TicketOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private OrderStatus orderStatus = OrderStatus.SUBSCRIBE; // 订单状态

    private Date createTime; // 创建时间

    @ManyToOne
    @JsonView(TicketOrder.FlightManagementJsonView.class)
    FlightManagement flightManagement; // 对应航班

    @ManyToOne
    @JsonView(TicketOrder.VisitorJsonView.class)
    private Visitor visitor; // 对应乘客

    @ManyToOne
    @JsonView(TicketOrder.TicketPriceJsonView.class)
    private TicketPrice ticketPrice; // 对应舱位价钱

    @ManyToOne
    @JsonView(TicketOrder.SeatJsonView.class)
    private Seat seat; // 订单座位

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

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public TicketPrice getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(TicketPrice ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public interface FlightManagementJsonView {
    }

    public interface VisitorJsonView {
    }

    public interface SeatJsonView {
    }

    public interface TicketPriceJsonView {
    }

}
