package com.xiang.airTicket.repository;

import com.xiang.airTicket.entity.TicketOrder;
import com.xiang.airTicket.entity.Visitor;
import com.xiang.airTicket.enumeration.OrderStatus;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TicketOrderRepository extends PagingAndSortingRepository<TicketOrder, Long> {
    List<TicketOrder> findAllByOrderStatus(OrderStatus orderStatus);

    List<TicketOrder> findAllByVisitorOrderByIdDesc(Visitor visitor);
}
