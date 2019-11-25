package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.FlightManagement;
import com.xiang.airTicket.entity.TicketOrder;
import com.xiang.airTicket.entity.TicketPrice;
import com.xiang.airTicket.entity.Visitor;
import com.xiang.airTicket.repository.TicketOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiang.airTicket.schedule.TicketQueue;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Service
public class TicketOrderServiceImpl implements TicketOrderService {

    public static Logger logger = LoggerFactory.getLogger(TicketOrderServiceImpl.class);

    @Autowired
    VisitorService visitorService;

    @Autowired
    FlightManagementService flightManagementService;

    @Autowired
    TicketOrderRepository ticketOrderRepository;

    @Autowired
    TicketQueue ticketQueue;

    @Override
    public TicketOrder subscribeOrder(Long flightManagementId, Long tickerPriceId, HttpServletRequest httpServletRequest) {
        // 创建订单 设置创建日期
        TicketOrder ticketOrder = new TicketOrder();
        // 获取当前登陆游客
        Visitor visitor = visitorService.getCurrentLoginVisitor(httpServletRequest);
        // 获取所预定航班
        FlightManagement flightManagement = flightManagementService.getById(flightManagementId);
        for (TicketPrice ticketPrice : flightManagement.getTicketPrices()) {
            if (ticketPrice.getId().equals(tickerPriceId)) {
                ticketOrder.setTicketPrice(ticketPrice);
                break;
            }
        }
        ticketOrder.setVisitor(visitor);
        ticketOrder.setFlightManagement(flightManagement);
        ticketOrder.setCreateTime(Calendar.getInstance().getTime());
        // 添加到订单队列
        ticketQueue.add(ticketOrder);
        return ticketOrderRepository.save(ticketOrder);
    }
}
