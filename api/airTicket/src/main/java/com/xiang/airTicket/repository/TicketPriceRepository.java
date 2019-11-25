package com.xiang.airTicket.repository;

import com.xiang.airTicket.entity.TicketPrice;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TicketPriceRepository extends PagingAndSortingRepository<TicketPrice, Long> {
}
