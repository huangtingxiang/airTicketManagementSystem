package com.xiang.airTicket.repository;

import com.xiang.airTicket.entity.Seat;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SeatRepository extends PagingAndSortingRepository<Seat, Long> {
}
