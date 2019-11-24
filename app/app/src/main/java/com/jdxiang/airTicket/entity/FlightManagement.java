package com.jdxiang.airTicket.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightManagement {
    private Long id;

    private Date startTime; // 起始时间

    private Date arrivalTime; // 到达时间

    private City startingPlace; // 起始地

    private City destination; // 目的地

    private AirPort startingAirPort;  // 起始机场

    private AirPort destinationAirPort; // 到达机场

    List<TicketPrice> ticketPrices; // 订单价钱


    private Plane plane; // 飞机

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public City getStartingPlace() {
        return startingPlace;
    }

    public void setStartingPlace(City startingPlace) {
        this.startingPlace = startingPlace;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }


    public AirPort getStartingAirPort() {
        return startingAirPort;
    }

    public void setStartingAirPort(AirPort startingAirPort) {
        this.startingAirPort = startingAirPort;
    }

    public AirPort getDestinationAirPort() {
        return destinationAirPort;
    }

    public void setDestinationAirPort(AirPort destinationAirPort) {
        this.destinationAirPort = destinationAirPort;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public List<TicketPrice> getTicketPrices() {
        return ticketPrices;
    }

    public void setTicketPrices(List<TicketPrice> ticketPrices) {
        this.ticketPrices = ticketPrices;
    }
//    @OneToMany
//    @JsonView(TicketOrdersJsonView.class)
//    private List<TicketOrder> ticketOrders = new ArrayList<>(); // 订单集合

}
