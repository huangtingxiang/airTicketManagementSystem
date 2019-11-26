package com.xiang.airTicket.service;

import com.xiang.airTicket.entity.TicketOrder;
import com.xiang.airTicket.entity.Visitor;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TicketOrderService {

    /**
     * 创建预定订单
     *
     * @param flightManagementId
     * @param ticketPriceId
     * @return
     */
    TicketOrder subscribeOrder(Long flightManagementId, Long ticketPriceId, HttpServletRequest httpServletRequest);

    /**
     * 支付订单
     *
     * @param id
     */
    void payForOrder(Long id, Visitor visitor);

    /**
     * 获取旅客所有订单
     *
     * @param visitor
     * @return
     */
    List<TicketOrder> getAll(Visitor visitor);

    /**
     * 取消订单
     * @param id
     */
    void cancelSubscribe(Long id);

    /**
     * 退票
     * @param id
     */
    void cancelPayFor(Long id, Visitor visitor);

    /**
     * 完成订单
     * @param id
     */
    void finishOrder(Long id);

}
