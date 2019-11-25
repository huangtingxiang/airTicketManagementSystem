package com.xiang.airTicket.dataInit;

import com.xiang.airTicket.entity.TicketOrder;
import com.xiang.airTicket.enumeration.OrderStatus;
import com.xiang.airTicket.repository.TicketOrderRepository;
import com.xiang.airTicket.schedule.TicketQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketInit extends DataInit {

    public static Logger logger = LoggerFactory.getLogger(TicketQueue.class);

    @Autowired
    TicketOrderRepository ticketOrderRepository;

    @Autowired TicketQueue ticketQueue;

    @Override
    public void init() {
        logger.info("获取所有预约的订单");
        List<TicketOrder> tickets = ticketOrderRepository.findAllByOrderStatus(OrderStatus.SUBSCRIBE);
        logger.info("将所有未支付的订单放入队列中");
        for (TicketOrder ticketOrder :
                tickets) {
            ticketQueue.add(ticketOrder);
        }
    }
}
