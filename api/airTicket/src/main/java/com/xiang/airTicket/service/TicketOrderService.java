package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.TicketOrder;

import javax.servlet.http.HttpServletRequest;

public interface TicketOrderService {

    /**
     * 创建预定订单
     * @param flightManagementId
     * @param ticketPriceId
     * @return
     */
    TicketOrder subscribeOrder(Long flightManagementId, Long ticketPriceId, HttpServletRequest httpServletRequest);

}
