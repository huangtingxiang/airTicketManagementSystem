package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.Seat;

import java.util.List;

public interface SeatService {

    List<Seat> findByShipSpaceAndPlane(Long ship);

}
