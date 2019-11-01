package com.xiang.airTicket.entity;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 航班安排
@Entity
public class FlightManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startTime; // 起始时间

    private Date arrivalTime; // 到达时间

    @ManyToOne
    @JsonView(StartingPlaceJsonView.class)
    private City startingPlace; // 起始地

    @ManyToOne
    @JsonView(DestinationJsonView.class)
    private City destination; // 目的地

    @ManyToOne
    @JsonView(StartingAirPortJsonView.class)
    private AirPort startingAirPort;  // 起始机场

    @ManyToOne
    @JsonView(DestinationAirPortJsonView.class)
    private AirPort destinationAirPort; // 到达机场

    @ManyToOne
    @JsonView(PlaneJsonView.class)
    private Plane plane; // 飞机

    @OneToMany(cascade = CascadeType.ALL)
    @JsonView(TicketPricesJsonView.class)
    List<TicketPrice> ticketPrices; // 订单价钱

    @OneToMany
    @JsonView(TicketOrdersJsonView.class)
    private List<TicketOrder> ticketOrders = new ArrayList<>(); // 订单集合

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

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public List<TicketOrder> getOrders() {
        return ticketOrders;
    }

    public void setOrders(List<TicketOrder> orders) {
        this.ticketOrders = orders;
    }

    public AirPort getDestinationAirPort() {
        return destinationAirPort;
    }

    public void setDestinationAirPort(AirPort destinationAirPort) {
        this.destinationAirPort = destinationAirPort;
    }

    public List<TicketPrice> getTicketPrices() {
        return ticketPrices;
    }

    public void setTicketPrices(List<TicketPrice> ticketPrices) {
        this.ticketPrices = ticketPrices;
    }

    public List<TicketOrder> getTicketOrders() {
        return ticketOrders;
    }

    public void setTicketOrders(List<TicketOrder> ticketOrders) {
        this.ticketOrders = ticketOrders;
    }

    public interface StartingPlaceJsonView {
    }

    public interface DestinationJsonView{}

    public interface StartingAirPortJsonView{}

    public interface DestinationAirPortJsonView{}

    public interface PlaneJsonView{}

    public interface TicketPricesJsonView{}

    public interface TicketOrdersJsonView{}
}
