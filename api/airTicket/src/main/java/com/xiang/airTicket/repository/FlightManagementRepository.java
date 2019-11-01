package com.xiang.airTicket.repository;

import com.xiang.airTicket.entity.City;
import com.xiang.airTicket.entity.FlightManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface FlightManagementRepository extends PagingAndSortingRepository<FlightManagement, Long> {

    // 通过起始地 目的地 startDate< < endDate
    Page<FlightManagement> findAllByStartingPlaceAndDestinationAndStartTimeBetween(City startPlace, City destination, Date startDate, Date endDate, Pageable pageable);

}
