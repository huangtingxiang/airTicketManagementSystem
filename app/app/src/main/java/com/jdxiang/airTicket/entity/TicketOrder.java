package com.jdxiang.airTicket.entity;

import com.jdxiang.airTicket.enumeration.OrderStatus;

import java.util.Date;

public class TicketOrder {
    private Long id; // id

    private int orderStatus = 0; // 订单状态

    private Date createTime; // 创建时间

    FlightManagement flightManagement; // 对应航班

    private Visitor visitor; // 对应乘客


    private TicketPrice ticketPrice; // 对应舱位价钱

    private Seat seat; // 订单座位


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
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

    public TicketPrice getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(TicketPrice ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public static String orderStatusToString(int orderStatus) {
        String result = "";
        switch (orderStatus) {
            case 1:
                result = "已支付";
                break;
            case 3:
                result = "已取消";
                break;
            case 2:
                result = "已完成";
                break;
            case 0:
                result = "预约中";
                break;
            default:
                break;
        }
        return result;
    }

    public static boolean isActive(int code) {
        return code == getActive();
    }

    public static boolean isSubscribe(int code) {
        return code == getSubscribe();
    }

    public static int getActive() {
        return 1;
    }

    public static int getSubscribe() {
        return 0;
    }

    public static int getCancel() {
        return  3;
    }

    public static int getFinish() {
        return 2;
    }
}
