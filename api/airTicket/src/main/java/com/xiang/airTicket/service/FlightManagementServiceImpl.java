package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.City;
import com.xiang.airTicket.entity.FlightManagement;
import com.xiang.airTicket.repository.FlightManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    private Date getStartTime(Date date) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        return todayStart.getTime();
    }

    private Date getEndTime(Date date) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        return todayEnd.getTime();
    }


    @Override
    public Page<FlightManagement> pageByStartingPlaceAndDestinationStartTime(Long startingPlaceId, Long destinationId, Date startTime, Pageable pageable) {
        return flightManagementRepository.findAll(new Specification() {
            Predicate predicate;
            CriteriaBuilder criteriaBuilder;

            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 开始地方
                this.criteriaBuilder = criteriaBuilder;
                if (startingPlaceId != null) {
                    this.andPredicate(criteriaBuilder.equal(root.join("startingPlace").get("id"), startingPlaceId));
                }
                // 目的地
                if (destinationId != null) {
                    this.andPredicate(criteriaBuilder.equal(root.join("destination").get("id"), destinationId));
                }
                // 开始时间和结束时间
                if (startTime != null) {
                    this.andPredicate(criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), getStartTime(startTime)));
                    this.andPredicate(criteriaBuilder.lessThanOrEqualTo(root.get("startTime"), getEndTime(startTime)));
                }
                if (predicate != null) {
                    criteriaQuery.where(predicate);
                }
                return criteriaQuery.getRestriction();
            }

            private void andPredicate(Predicate predicate) {
                if (null != predicate) {
                    if (null == this.predicate) {
                        this.predicate = predicate;
                    } else {
                        this.predicate = this.criteriaBuilder.and(this.predicate, predicate);
                    }
                }
            }
        }, pageable);
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
