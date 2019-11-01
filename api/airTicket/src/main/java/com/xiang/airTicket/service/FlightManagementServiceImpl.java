package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.City;
import com.xiang.airTicket.entity.FlightManagement;
import com.xiang.airTicket.repository.FlightManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class FlightManagementServiceImpl implements FlightManagementService {

    @Autowired
    FlightManagementRepository flightManagementRepository;


    @Override
    public FlightManagement save(FlightManagement flightManagement) {
        return flightManagementRepository.save(flightManagement);
    }

    private  Date getStartTime(Date date) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        return todayStart.getTime();
    }

    private  Date getEndTime(Date date) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        return todayEnd.getTime();
    }

    @Override
    public Page<FlightManagement> pageByStartingPlaceAndDestinationStartTime(Long  startingPlaceId, Long destinationId, Date startTime, Pageable pageable) {
        City startingPlace = new City();
        startingPlace.setId(startingPlaceId);
        City destination = new City();
        destination.setId(destinationId);
        return flightManagementRepository.findAllByStartingPlaceAndDestinationAndStartTimeBetween(
                startingPlace,
                destination,
                getStartTime(startTime),
                getEndTime(startTime),
                pageable
        );
    }

    @Override
    public FlightManagement update(Long id, FlightManagement flightManagement) {
        FlightManagement oldFlightManagement = flightManagementRepository.findById(id).get();
        oldFlightManagement.setArrivalTime(flightManagement.getArrivalTime()); // 到达时间
        oldFlightManagement.setStartTime(flightManagement.getStartTime()); // 起始时间
        oldFlightManagement.setStartingPlace(flightManagement.getStartingPlace()); // 起始地
        oldFlightManagement.setDestination(flightManagement.getDestination()); // 目的地
        oldFlightManagement.setDestinationAirPort(flightManagement.getDestinationAirPort()); // 目的机场
        oldFlightManagement.setStartingAirPort(flightManagement.getStartingAirPort()); // 起始机场
        oldFlightManagement.setPlane(flightManagement.getPlane()); // 飞机
        oldFlightManagement.setTicketPrices(flightManagement.getTicketPrices()); // 订单 价钱
        return flightManagementRepository.save(oldFlightManagement);
    }

    @Override
    public FlightManagement getById(Long id) {
        return flightManagementRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        flightManagementRepository.deleteById(id);
    }
}
